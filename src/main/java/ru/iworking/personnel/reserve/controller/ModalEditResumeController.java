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
import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.*;
import ru.iworking.personnel.reserve.service.*;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ModalEditResumeController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ModalEditResumeController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
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

    private PhotoDao photoDao = PhotoDao.getInstance();
    private ProfFieldService profFieldService = ProfFieldService.INSTANCE;
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private ResumeDao resumeDao = ResumeDao.getInstance();
    private CurrencyService currencyService = CurrencyService.INSTANCE;
    private ResumeStateService resumeStateService = ResumeStateService.INSTANCE;
    
    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();
    private ResumeStateCellFactory resumeStateCellFactory = new ResumeStateCellFactory();
    
    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
        this.setValues(this.resume);
    }

    public Resume getResume() {
        return resume;
    }

    public ProfField getCurrentProfField() {
        return currentProfField;
    }

    public void setCurrentProfField(ProfField currentProfField) {
        this.currentProfField = currentProfField;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wageTextField.setTextFormatter(bigDecimalFormatter);
        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);

        resumeStateComboBox.setButtonCell(resumeStateCellFactory.call(null));
        resumeStateComboBox.setCellFactory(resumeStateCellFactory);
        resumeStateComboBox.setItems(FXCollections.observableList(resumeStateService.findAll()));

        profFieldComboBox.setButtonCell(profFieldCellFactory.call(null));
        profFieldComboBox.setCellFactory(profFieldCellFactory);
        profFieldComboBox.setItems(FXCollections.observableList(profFieldService.findAll()));

        workTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        workTypeComboBox.setCellFactory(workTypeCellFactory);
        workTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));

        educationComboBox.setButtonCell(educationCellFactory.call(null));
        educationComboBox.setCellFactory(educationCellFactory);
        educationComboBox.setItems(FXCollections.observableList(educationService.findAll()));

        currencyComboBox.setButtonCell(currencyCellFactory.call(null));
        currencyComboBox.setCellFactory(currencyCellFactory);
        currencyComboBox.setItems(FXCollections.observableList(currencyService.findAll()));
    }

    public void setValues(Resume resume) {
        lastNameTextField.setText(resume.getProfile().getLastName());
        firstNameTextField.setText(resume.getProfile().getFirstName());
        middleNameTextField.setText(resume.getProfile().getMiddleName());
        numberPhoneTextField.setText(resume.getNumberPhone().getNumber());
        emailTextField.setText(resume.getEmail());
        professionTextField.setText(resume.getProfession());
        if (resume.getProfFieldId() != null) profFieldComboBox.setValue(profFieldService.findById(resume.getProfFieldId()));
        if (resume.getWage() != null) {
            wageTextField.setText(decimalFormat.format(resume.getWage().getCountBigDecimal()));
            currencyComboBox.setValue(currencyService.findById(resume.getWage().getCurrencyId()));
        }
        if (resume.getWorkTypeId() != null) workTypeComboBox.setValue(workTypeService.findById(resume.getWorkTypeId()));
        if (resume.getState() != null) resumeStateComboBox.setValue(resume.getState());
        if (resume.getEducationId() != null) educationComboBox.setValue(educationService.findById(resume.getEducationId()));
        experienceDateStartDatePicker.setValue(resume.getExperience().getDateStart());
        experienceDateEndDatePicker.setValue(resume.getExperience().getDateEnd());
        
        addressTextArea.setText(resume.getAddress().getHouse());

        if (resume.getPhotoId() != null) {
            Photo photo = photoDao.find(resume.getPhotoId());
            InputStream targetStream = new ByteArrayInputStream(photo.getImage());
            Image img = new Image(targetStream);
            photoImageView.setImage(img);
        } else {
            Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg"));
            photoImageView.setImage(defaultImage);
        }
    }

    public void showAndWait(Parent parent) {
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
        modal.setTitle("Изменить");
        AppUtil.setIcon(modal);
        modal.setScene(scene);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(primaryStage);
        modal.showAndWait();
    }

    @FXML
    public void actionButtonImageReplace(ActionEvent event) {
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
    public void actionButtonCancel(ActionEvent event) {
        this.closeStage(event);
    }

    @FXML
    public void actionButtonEdit(ActionEvent event) throws CloneNotSupportedException {

        Resume newResume = (Resume) resume.clone();

        newResume.getProfile().setLastName(lastNameTextField.getText());
        newResume.getProfile().setFirstName(firstNameTextField.getText());
        newResume.getProfile().setMiddleName(middleNameTextField.getText());

        newResume.getNumberPhone().setNumber(numberPhoneTextField.getText());

        newResume.setEmail(emailTextField.getText());
        newResume.setProfession(professionTextField.getText());

        if (profFieldComboBox.getValue() != null) newResume.setProfFieldId(profFieldComboBox.getValue().getId());

        if (!wageTextField.getText().isEmpty()) {
            try {
                newResume.getWage().setCount(new BigDecimal(wageTextField.getText().replaceAll(",",".")));
                newResume.getWage().setCurrencyId(currencyComboBox.getValue().getId());
            } catch (Exception e) {
                logger.error(e);
            }
        }
        if (workTypeComboBox.getValue() != null) newResume.setWorkTypeId(workTypeComboBox.getValue().getId());
        if (resumeStateComboBox.getValue() != null) newResume.setState(resumeStateComboBox.getValue());
        if (educationComboBox.getValue() != null) newResume.setEducationId(educationComboBox.getValue().getId());

        newResume.getExperience().setDateStart(experienceDateStartDatePicker.getValue());
        newResume.getExperience().setDateEnd(experienceDateEndDatePicker.getValue());

        newResume.getAddress().setHouse(addressTextArea.getText());

        Photo photo = null;

        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BufferedImage originalImage = SwingFXUtils.fromFXImage(photoImageView.getImage(), null);
            ImageIO.write(originalImage, "png", stream);

            photo = new Photo(ImageUtil.scaleToSize(stream.toByteArray(), null,200));
        } catch (IOException e) {
            logger.error(e);
        }

        if (isValidFields(newResume)) {
            if (photo != null) {
                try {
                    Long photoId = photoDao.createAndUpdateInCash(photo).getId();
                    newResume.setPhotoId(photoId);
                } catch (OutOfMemoryError ex) {
                    logger.error(ex);
                }
            }
            resumeDao.update(newResume);
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
