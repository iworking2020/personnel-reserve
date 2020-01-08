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
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.PdfUtil;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.service.api.utils.LocaleUtils;
import ru.iworking.service.api.utils.TimeUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ModalOpenResumeFxmlController implements Initializable {

    static final Logger logger = LogManager.getLogger(ModalOpenResumeFxmlController.class);

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

    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
        this.setValues(this.resume);
    }

    public void setValues(Resume resume) {
        lastNameLabel.setText(resume.getLastName());
        firstNameLabel.setText(resume.getFirstName());
        middleNameLabel.setText(resume.getMiddleName());
        numberPhoneLabel.setText(resume.getNumberPhone());
        emailLabel.setText(resume.getEmail());
        professionLabel.setText(resume.getProfession());
        if (resume.getProfField() != null) {
            profFieldLabel.setText(resume.getProfField().getNameToView(LocaleUtils.getDefault()));
        } else {
            profFieldLabel.setText("не указана");
        }
        if (resume.getWage() != null) {
            String wageString = resume.getCurrency() != null ?
                    resume.getWage().toString() + " " + resume.getCurrency().getNameToView(LocaleUtils.getDefault()) :
                    resume.getWage().toString();
            wageString = wageString.length() > 0 ? wageString : "не указана";
            wageLabel.setText(wageString);
        } else {
            wageLabel.setText("не указана");
        }

        if (resume.getWorkType() != null) {
            workTypeLabel.setText(resume.getWorkType().getNameToView(LocaleUtils.getDefault()));
        } else {
            workTypeLabel.setText("не указан");
        }
        if (resume.getEducation() != null) {
            educationLabel.setText(resume.getEducation().getNameToView(LocaleUtils.getDefault()));
        } else {
            educationLabel.setText("не указано");
        }

        Integer age = TimeUtils.calAge(resume.getExperience().getDateStart(), resume.getExperience().getDateEnd());
        
        experienceLabel.setText(age == null || age <= 0 ? "без опыта" : age + " " + TextUtil.nameForNumbers(age));
        addressLabel.setText(resume.getAddress());

        if (resume.getPhoto() != null) {
            InputStream targetStream = new ByteArrayInputStream(resume.getPhoto());
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
            try {
                PdfUtil.createResumePdf(path, this.resume);
            } catch (IOException e) {
                logger.error(e);
            }
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
