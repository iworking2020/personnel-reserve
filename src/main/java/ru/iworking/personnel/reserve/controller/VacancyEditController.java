package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CurrencyDao;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.personnel.reserve.model.CurrencyCellFactory;
import ru.iworking.personnel.reserve.model.EducationCellFactory;
import ru.iworking.personnel.reserve.model.ProfFieldCellFactory;
import ru.iworking.personnel.reserve.model.WorkTypeCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VacancyEditController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacancyEditController.class);

    @FXML private VBox vacancyEdit;
    @FXML private TextField vacancyProfessionTextField;
    @FXML private ComboBox<ProfField> vacancyProfFieldComboBox;
    @FXML private ComboBox<WorkType> vacancyWorkTypeComboBox;
    @FXML private ComboBox<Education> vacancyEducationComboBox;
    @FXML private TextField vacancyWageTextField;
    @FXML private ComboBox<Currency> vacancyCurrencyComboBox;
    @FXML private DatePicker vacancyExpDateStartDatePicker;
    @FXML private DatePicker vacancyExpDateEndDatePicker;
    @FXML private TextArea vacancyAddressTextArea;

    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();

    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        vacancyProfFieldComboBox.setButtonCell(profFieldCellFactory.call(null));
        vacancyProfFieldComboBox.setCellFactory(profFieldCellFactory);
        vacancyProfFieldComboBox.setItems(FXCollections.observableList(profFieldDao.findAllFromCash()));

        vacancyWorkTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        vacancyWorkTypeComboBox.setCellFactory(workTypeCellFactory);
        vacancyWorkTypeComboBox.setItems(FXCollections.observableList(workTypeDao.findAllFromCash()));

        vacancyEducationComboBox.setButtonCell(educationCellFactory.call(null));
        vacancyEducationComboBox.setCellFactory(educationCellFactory);
        vacancyEducationComboBox.setItems(FXCollections.observableList(educationDao.findAllFromCash()));

        vacancyCurrencyComboBox.setButtonCell(currencyCellFactory.call(null));
        vacancyCurrencyComboBox.setCellFactory(currencyCellFactory);
        vacancyCurrencyComboBox.setItems(FXCollections.observableList(currencyDao.findAllFromCash()));
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        hide();
        clear();
    }

    public void view() {
        vacancyEdit.setVisible(true);
    }

    public void hide() {
        vacancyEdit.setVisible(false);
    }

    public void clear() {
        vacancyProfessionTextField.setText("");
        vacancyProfFieldComboBox.setValue(null);
        vacancyWorkTypeComboBox.setValue(null);
        vacancyEducationComboBox.setValue(null);
        vacancyWageTextField.setText("");
        vacancyCurrencyComboBox.setValue(null);
        vacancyExpDateStartDatePicker.setValue(null);
        vacancyExpDateEndDatePicker.setValue(null);
        vacancyAddressTextArea.setText("");
    }
}
