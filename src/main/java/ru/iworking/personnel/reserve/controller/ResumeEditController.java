package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ResumeEditController implements Initializable {

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

    public void initData(Resume resume) {

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
