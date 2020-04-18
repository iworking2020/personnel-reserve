package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.*;
import ru.iworking.personnel.reserve.utils.AppUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class ModalAddResumeFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ModalAddResumeFxmlController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    @FXML private TextField lastNameTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField numberPhoneTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField professionTextField;
    @FXML private ComboBox<ProfField> profFieldComboBox;
    @FXML private TextField wageTextField;
    @FXML private ComboBox<Currency> currencyComboBox;
    @FXML private ComboBox<WorkType> workTypeComboBox;
    @FXML private ComboBox<Education> educationComboBox;
    @FXML private TextArea addressTextArea;
    @FXML private DatePicker experienceDateStartDatePicker;
    @FXML private DatePicker experienceDateEndDatePicker;
    @FXML private ComboBox<ResumeState> resumeStateComboBox;

    @FXML private ImageView photoImageView;

    @FXML private Button buttonCancel;

    private ProfField currentProfField;

    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private ResumeDao resumeDao = ResumeDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();
    private PhotoDao photoDao = PhotoDao.getInstance();
    private ResumeStateDao resumeStateDao = ResumeStateDao.getInstance();

    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();
    private ResumeStateCellFactory resumeStateCellFactory = new ResumeStateCellFactory();

    public ProfField getCurrentProfField() {
        return currentProfField;
    }

    public void setCurrentProfField(ProfField currentProfField) {
        this.currentProfField = currentProfField;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);
        wageTextField.setTextFormatter(bigDecimalFormatter);

        resumeStateComboBox.setButtonCell(resumeStateCellFactory.call(null));
        resumeStateComboBox.setCellFactory(resumeStateCellFactory);
        resumeStateComboBox.setItems(FXCollections.observableList(resumeStateDao.findAllFromCash()));

        profFieldComboBox.setButtonCell(profFieldCellFactory.call(null));
        profFieldComboBox.setCellFactory(profFieldCellFactory);
        profFieldComboBox.setItems(FXCollections.observableList(profFieldDao.findAllFromCash()));

        workTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        workTypeComboBox.setCellFactory(workTypeCellFactory);
        workTypeComboBox.setItems(FXCollections.observableList(workTypeDao.findAllFromCash()));

        educationComboBox.setButtonCell(educationCellFactory.call(null));
        educationComboBox.setCellFactory(educationCellFactory);
        educationComboBox.setItems(FXCollections.observableList(educationDao.findAllFromCash()));

        currencyComboBox.setButtonCell(currencyCellFactory.call(null));
        currencyComboBox.setCellFactory(currencyCellFactory);
        currencyComboBox.setItems(FXCollections.observableList(currencyDao.findAllFromCash()));
    }

    private void initStartValues() {
        if (currentProfField != null) profFieldComboBox.getSelectionModel().select(currentProfField);
        photoImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg")));
    }

    public void showAndWait(Parent parent) {
        this.initStartValues();

        Stage primaryStage = MainApp.PARENT_STAGE;

        Scene scene = new Scene(parent);
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/button.css");
        scene.getStylesheets().add("/styles/text-field.css");
        scene.getStylesheets().add("/styles/combo-box.css");
        scene.getStylesheets().add("/styles/date-picker.css");
        scene.getStylesheets().add("/styles/text-area.css");
        scene.getStylesheets().add("/styles/modal.css");

        Stage modal = new Stage();
        modal.setTitle("Добавить");
        AppUtil.setIcon(modal);
        modal.setScene(scene);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(primaryStage);
        modal.showAndWait();
    }

    @FXML
    private void actionButtonImageReplace(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG", "*.png"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("GIF", "*.gif")
        );

        File file = fileChooser.showOpenDialog(getStage(event));
        if (file != null) {
            try {
                Image img = new Image(file.toURI().toString());
                photoImageView.setImage(img);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    @FXML
    private void actionButtonCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionButtonInsert(ActionEvent event) {
        Resume resume = new Resume();

        Profile profile = new Profile();
        profile.setLastName(lastNameTextField.getText());
        profile.setFirstName(firstNameTextField.getText());
        profile.setMiddleName(middleNameTextField.getText());
        resume.setProfile(profile);

        NumberPhone numberPhone = new NumberPhone();
        numberPhone.setNumber(numberPhoneTextField.getText());
        resume.setNumberPhone(numberPhone);

        resume.setEmail(emailTextField.getText());
        resume.setProfession(professionTextField.getText());
        ProfField profField = profFieldComboBox.getValue();
        if (profField != null) resume.setProfFieldId(profField.getId());
        if (!wageTextField.getText().isEmpty()) {
            try {
                Wage wage = new Wage();
                wage.setCount(new BigDecimal(wageTextField.getText().replaceAll(",",".")));
                wage.setCurrencyId(currencyComboBox.getValue().getId());
                resume.setWage(wage);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        WorkType workType = workTypeComboBox.getValue();
        if (workType != null) resume.setWorkTypeId(workType.getId());

        Education education = educationComboBox.getValue();
        if (education != null) resume.setEducationId(education.getId());

        if (resumeStateComboBox.getValue() != null) resume.setState(resumeStateComboBox.getValue());
        
        Experience exp = new Experience();
        exp.setDateStart(experienceDateStartDatePicker.getValue());
        exp.setDateEnd(experienceDateEndDatePicker.getValue());
        resume.setExperience(exp);

        Address address = new Address();
        address.setHouse(addressTextArea.getText());
        resume.setAddress(address);

        Photo photo = null;

        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BufferedImage originalImage = SwingFXUtils.fromFXImage(photoImageView.getImage(), null);
            ImageIO.write(originalImage, "png", stream);

            photo = new Photo(stream.toByteArray());
        } catch (IOException e) {
            logger.error(e);
        }

        if (isValidFields(resume)) {
            if (photo != null) {
                Long photoId = photoDao.createAndUpdateInCash(photo).getId();
                resume.setPhotoId(photoId);
            }
            resumeDao.createAndUpdateInCash(resume);
            this.closeStage(event);
        }
    }

    private Boolean isValidFields(Resume resume) {
        Boolean isValid = true;
        if (resume.getProfile().getFirstName() != null && resume.getProfile().getFirstName().length() <= 0) {
            firstNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (resume.getProfile().getLastName() != null && resume.getProfile().getLastName().length() <= 0) {
            lastNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (resume.getProfile().getMiddleName() != null && resume.getProfile().getMiddleName().length() <= 0) {
            middleNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (resume.getProfession() != null && resume.getProfession().length() <= 0) {
            professionTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        return isValid;
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
