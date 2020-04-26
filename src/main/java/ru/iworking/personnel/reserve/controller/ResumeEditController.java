package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ResumeEditController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumeEditController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    @FXML private Pane resumePaneEdit;

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
    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
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

        photoImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg")));
    }

    public void setData(Resume resume) {
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
        if (resume.getWorkTypeId() != null) workTypeComboBox.setValue(workTypeDao.findFromCash(resume.getWorkTypeId()));
        if (resume.getState() != null) resumeStateComboBox.setValue(resume.getState());
        if (resume.getEducationId() != null) educationComboBox.setValue(educationDao.findFromCash(resume.getEducationId()));
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
    public void actionCancel(ActionEvent event) {
        hide();
        clear();
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

}
