package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.component.EducationEditBlock;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.interfaces.AppFunctionalInterface;
import ru.iworking.personnel.reserve.model.*;
import ru.iworking.personnel.reserve.service.EducationService;
import ru.iworking.personnel.reserve.service.WorkTypeService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ResumeEditController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumeEditController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    @FXML private Pane resumePaneEdit;

    @FXML private TextField resumeIdTextField;

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

    @FXML private VBox educationEditList;

    private ProfField currentProfField;

    private PhotoDao photoDao = PhotoDao.getInstance();
    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private ResumeDao resumeDao = ResumeDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();
    private ResumeStateDao resumeStateDao = ResumeStateDao.getInstance();

    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();
    private ResumeStateCellFactory resumeStateCellFactory = new ResumeStateCellFactory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);
        wageTextField.setTextFormatter(bigDecimalFormatter);

        resumeStateComboBox.setButtonCell(resumeStateCellFactory.call(null));
        resumeStateComboBox.setCellFactory(resumeStateCellFactory);
        resumeStateComboBox.setItems(FXCollections.observableList(resumeStateDao.findAllFromCache()));

        profFieldComboBox.setButtonCell(profFieldCellFactory.call(null));
        profFieldComboBox.setCellFactory(profFieldCellFactory);
        profFieldComboBox.setItems(FXCollections.observableList(profFieldDao.findAllFromCache()));

        workTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        workTypeComboBox.setCellFactory(workTypeCellFactory);
        workTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));

        educationComboBox.setButtonCell(educationCellFactory.call(null));
        educationComboBox.setCellFactory(educationCellFactory);
        educationComboBox.setItems(FXCollections.observableList(educationService.findAll()));

        currencyComboBox.setButtonCell(currencyCellFactory.call(null));
        currencyComboBox.setCellFactory(currencyCellFactory);
        currencyComboBox.setItems(FXCollections.observableList(currencyDao.findAllFromCache()));

        photoImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg")));
    }

    public void setData(Resume resume) {
        resumeIdTextField.setText(resume.getId().toString());
        lastNameTextField.setText(resume.getProfile().getLastName());
        firstNameTextField.setText(resume.getProfile().getFirstName());
        middleNameTextField.setText(resume.getProfile().getMiddleName());
        numberPhoneTextField.setText(resume.getNumberPhone().getNumber());
        emailTextField.setText(resume.getEmail());
        professionTextField.setText(resume.getProfession());
        if (resume.getProfFieldId() != null) profFieldComboBox.setValue(profFieldDao.findFromCash(resume.getProfFieldId()));
        if (resume.getWage() != null) {
            wageTextField.setText(decimalFormat.format(resume.getWage().getCountBigDecimal()));
            currencyComboBox.setValue(currencyDao.findFromCash(resume.getWage().getCurrencyId()));
        }
        if (resume.getWorkTypeId() != null) workTypeComboBox.setValue(workTypeService.findById(resume.getWorkTypeId()));
        if (resume.getState() != null) resumeStateComboBox.setValue(resume.getState());
        if (resume.getEducationId() != null) educationComboBox.setValue(educationService.findById(resume.getEducationId()));
        experienceDateStartDatePicker.setValue(resume.getExperience().getDateStart());
        experienceDateEndDatePicker.setValue(resume.getExperience().getDateEnd());

        addressTextArea.setText(resume.getAddress().getHouse());

        if (resume.getPhotoId() != null) {
            Photo photo = photoDao.findFromCash(resume.getPhotoId());
            InputStream targetStream = new ByteArrayInputStream(photo.getImage());
            Image img = new Image(targetStream);
            photoImageView.setImage(img);
        } else {
            Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg"));
            photoImageView.setImage(defaultImage);
        }
    }

    @FXML
    public void actionAddEducation(ActionEvent event) {
        EducationEditBlock educationEditBlock = new EducationEditBlock();
        educationEditList.getChildren().add(educationEditBlock);
    }

    @FXML
    private void actionLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );

        File file = fileChooser.showOpenDialog(MainApp.PARENT_STAGE);
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
    public void actionCancel(ActionEvent event) {
        hide();
        clear();
    }

    @FXML
    public void actionSave(ActionEvent event) {
        Boolean isSaved = save();
        if (isSaved) {
            hide();
            clear();
            getResumesAccordionController().actionUpdate(event);
        }
    }

    public Boolean save() {
        if (isValid()) {
            Long resumeId = null;
            String vacancyIdStr = resumeIdTextField.getText();
            if (vacancyIdStr != null && !vacancyIdStr.isEmpty()) {
                resumeId = Long.valueOf(vacancyIdStr);
            }

            String lastNameStr = lastNameTextField.getText();
            String firstNameStr = firstNameTextField.getText();
            String middleNameStr = middleNameTextField.getText();
            String numberStr = numberPhoneTextField.getText();
            String emailStr = emailTextField.getText();
            String professionStr = professionTextField.getText();
            ProfField profField = profFieldComboBox.getValue();
            String wageStr = wageTextField.getText();
            Currency currency = currencyComboBox.getValue();
            WorkType workType = workTypeComboBox.getValue();
            ResumeState resumeState = resumeStateComboBox.getValue();
            Education education = educationComboBox.getValue();
            LocalDate expStart = experienceDateStartDatePicker.getValue();
            LocalDate expEnd = experienceDateEndDatePicker.getValue();
            String addressStr = addressTextArea.getText();

            Resume resume = resumeId == null ? new Resume() : resumeDao.find(resumeId);
            if (resume.getProfile() == null) resume.setProfile(new Profile());
            resume.getProfile().setLastName(lastNameStr);
            resume.getProfile().setFirstName(firstNameStr);
            resume.getProfile().setMiddleName(middleNameStr);
            if (resume.getNumberPhone() == null) resume.setNumberPhone(new NumberPhone());
            resume.getNumberPhone().setNumber(numberStr);
            resume.setEmail(emailStr);
            resume.setProfession(professionStr);
            if (profField != null) resume.setProfFieldId(profField.getId());
            if (wageStr != null && !wageStr.isEmpty()) {
                if (resume.getWage() == null) resume.setWage(new Wage());
                try {
                    resume.getWage().setCount(new BigDecimal(wageStr.replaceAll(",",".")));
                    if (currency != null) resume.getWage().setCurrencyId(currency.getId());
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            if (workType != null) resume.setWorkTypeId(workType.getId());
            if (resumeState != null) resume.setState(resumeState);
            if (education != null) resume.setEducationId(education.getId());
            if (resume.getExperience() == null) resume.setExperience(new Experience());
            resume.getExperience().setDateStart(expStart);
            resume.getExperience().setDateEnd(expEnd);
            if (resume.getAddress() == null) resume.setAddress(new Address());
            resume.getAddress().setHouse(addressStr);

            Photo photo = null;

            try(ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                BufferedImage originalImage = SwingFXUtils.fromFXImage(photoImageView.getImage(), null);
                ImageIO.write(originalImage, "png", stream);

                photo = new Photo(ImageUtil.scaleToSize(stream.toByteArray(), null,200));
            } catch (IOException e) {
                logger.error(e);
            }

            if (photo != null) {
                try {
                    Long photoId = photoDao.createAndUpdateInCash(photo).getId();
                    resume.setPhotoId(photoId);
                } catch (OutOfMemoryError ex) {
                    logger.error(ex);
                }
            }

            if (resumeId == null) {
                resumeDao.create(resume);
            } else {
                resumeDao.update(resume);
            }
            logger.debug("Created new resume: " + resume.toString());
            return true;
        } else {
            logger.debug("Fields vacancy edit block is not valid...");
            return false;
        }
    }

    private Boolean isValid() {
        Boolean isValid = true;
        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() <= 0) {
            firstNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() <= 0) {
            lastNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (middleNameTextField.getText() == null || middleNameTextField.getText().length() <= 0) {
            middleNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (professionTextField.getText() == null || professionTextField.getText().length() <= 0) {
            professionTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        return isValid;
    }

    public void clear() {
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        numberPhoneTextField.setText("");
        emailTextField.setText("");
        professionTextField.setText("");
        profFieldComboBox.setValue(null);
        wageTextField.setText("");
        currencyComboBox.setValue(null);
        workTypeComboBox.setValue(null);
        educationComboBox.setValue(null);
        addressTextArea.setText("");
        experienceDateStartDatePicker.setValue(null);
        experienceDateEndDatePicker.setValue(null);
        resumeStateComboBox.setValue(null);
    }

    public void show() {
        resumePaneEdit.setVisible(true);
        resumePaneEdit.setManaged(true);
    }

    public void show(AppFunctionalInterface function) {
        function.execute();
        this.show();
    }

    public void hide() {
        resumePaneEdit.setVisible(false);
        resumePaneEdit.setManaged(false);
    }

    public void hide(AppFunctionalInterface function) {
        function.execute();
        this.hide();
    }

    public ResumesAccordionController getResumesAccordionController() {
        return (ResumesAccordionController) getControllerProvider().get(ResumesAccordionController.class.getName());
    }

}
