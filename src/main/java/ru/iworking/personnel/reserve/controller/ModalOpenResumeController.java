package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.service.*;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.utils.TimeUtil;
import ru.iworking.personnel.reserve.utils.docs.pdf.PdfResumeWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ModalOpenResumeController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ModalOpenResumeController.class);

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @FXML private Label lastNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label middleNameLabel;
    @FXML private Label numberPhoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label professionLabel;
    @FXML private Label profFieldLabel;
    @FXML private Label wageLabel;
    @FXML private Label workTypeLabel;
    @FXML private Label educationLabel;
    @FXML private Label experienceLabel;
    @FXML private Label addressLabel;

    @FXML private ImageView photoImageView;

    private ProfFieldService profFieldService = ProfFieldService.INSTANCE;
    private CurrencyService currencyService = CurrencyService.INSTANCE;
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private PhotoService photoService = PhotoService.INSTANCE;

    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
        this.setValues(this.resume);
    }

    public void setValues(Resume resume) {
        lastNameLabel.setText(resume.getProfile().getLastName());
        firstNameLabel.setText(resume.getProfile().getFirstName());
        middleNameLabel.setText(resume.getProfile().getMiddleName());
        numberPhoneLabel.setText(resume.getNumberPhone().getNumber());
        emailLabel.setText(resume.getEmail());
        professionLabel.setText(resume.getProfession());
        if (resume.getProfFieldId() != null) {
            Long profFieldId = resume.getProfFieldId();
            profFieldLabel.setText(profFieldService.findById(profFieldId).getNameView().getName());
        } else {
            profFieldLabel.setText("не указана");
        }
        if (resume.getWage() != null) {
            String wageString;
            if(resume.getWage().getCurrencyId() != null) {
                Currency currency = currencyService.findById(resume.getWage().getCurrencyId());
                wageString = decimalFormat.format(resume.getWage().getCountBigDecimal()) + " " + currency.getNameView().getName();
            } else {
                wageString = decimalFormat.format(resume.getWage().getCountBigDecimal());
            }
            wageString = wageString.length() > 0 ? wageString : "не указана";
            wageLabel.setText(wageString);
        } else {
            wageLabel.setText("не указана");
        }

        if (resume.getWorkTypeId() != null) {
            WorkType workType = workTypeService.findById(resume.getWorkTypeId());
            workTypeLabel.setText(workType.getNameView().getName());
        } else {
            workTypeLabel.setText("не указан");
        }
        if (resume.getEducationId() != null) {
            Education education = educationService.findById(resume.getEducationId());
            educationLabel.setText(education.getNameView().getName());
        } else {
            educationLabel.setText("не указано");
        }

        Integer age = TimeUtil.calAge(resume.getExperience().getDateStart(), resume.getExperience().getDateEnd());
        
        experienceLabel.setText(age == null || age <= 0 ? "без опыта" : age + " " + TextUtil.nameForNumbers(age));
        addressLabel.setText(resume.getAddress().getHouse());

        if (resume.getPhotoId() != null) {
            Photo photo = photoService.findById(resume.getPhotoId());
            InputStream targetStream = new ByteArrayInputStream(photo.getImage());
            Image img = new Image(targetStream);
            photoImageView.setImage(img);
        } else {
            Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg"));
            photoImageView.setImage(defaultImage);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showAndWait(Parent parent) {
        Stage primaryStage = MainApp.PARENT_STAGE;

        Scene scene = new Scene(parent);
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/button.css");
        scene.getStylesheets().add("/styles/modal.css");

        Stage modal = new Stage();
        modal.setTitle("Открыть");
        AppUtil.setIcon(modal);
        modal.setScene(scene);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(primaryStage);
        modal.showAndWait();
    }

    @FXML
    private void actionButtonSavePdf(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Resume"+this.resume.getId()+".pdf");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.*"),
            new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );

        File file = fileChooser.showSaveDialog(getStage(event));
        if (file != null) {
            String path = file.getAbsoluteFile().getAbsolutePath();
            Map props = new HashMap<>();
            props.put(PdfResumeWriter.props.PATH, path);
            props.put(PdfResumeWriter.props.RESUME, this.resume);
            PdfResumeWriter.getInstance().write(props);
        }
    }

    @FXML
    private void actionButtonCancel(ActionEvent event) {
        this.closeStage(event);
    }

    private Stage getStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        return stage;
    }

    private void closeStage(ActionEvent event) {
        getStage(event).close();
    }
    
    

}
