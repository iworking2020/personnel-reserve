package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.CurrencyCellFactory;
import ru.iworking.personnel.reserve.model.EducationCellFactory;
import ru.iworking.personnel.reserve.model.ProfFieldCellFactory;
import ru.iworking.personnel.reserve.model.WorkTypeCellFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VacancyEditController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(VacancyEditController.class);

    @FXML private VBox vacancyEdit;
    @FXML private TextField vacancyIdTextField;
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
    private VacancyDao vacancyDao = VacancyDao.getInstance();

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

    public void setData(Vacancy vacancy) {
        if (vacancy != null) {
            if (vacancy.getId() != null) vacancyIdTextField.setText(vacancy.getId().toString());
            vacancyProfessionTextField.setText(vacancy.getProfession());
            if (vacancy.getProfFieldId() != null) vacancyProfFieldComboBox.setValue(profFieldDao.findFromCash(vacancy.getProfFieldId()));
            if (vacancy.getWorkTypeId() != null) vacancyWorkTypeComboBox.setValue(workTypeDao.findFromCash(vacancy.getWorkTypeId()));
            if (vacancy.getEducationId() != null) vacancyEducationComboBox.setValue(educationDao.findFromCash(vacancy.getEducationId()));
            if (vacancy.getWage() != null) vacancyWageTextField.setText(vacancy.getWage().getCount().toString());
            if (vacancy.getWage() != null && vacancy.getWage().getCurrencyId() != null) vacancyCurrencyComboBox.setValue(currencyDao.findFromCash(vacancy.getWage().getCurrencyId()));
            if (vacancy.getExperience() != null) {
                vacancyExpDateStartDatePicker.setValue(vacancy.getExperience().getDateStart());
                vacancyExpDateEndDatePicker.setValue(vacancy.getExperience().getDateEnd());
            }
            if (vacancy.getAddress() != null) vacancyAddressTextArea.setText(vacancy.getAddress().getStreet());
        } else {
            logger.debug("vacancy is null..");
        }
    }

    @FXML
    public void actionSave(ActionEvent event) {
        Long companyId = getCompaniesTableController().getTableCompanies().getSelectionModel().getSelectedItem().getId();
        Boolean isSaved = save(companyId);
        if (isSaved) {
            hide();
            clear();
            getVacanciesTableController().actionUpdate(event);
        }
    }

    public Boolean save(Long companyId) {
        if (isValid()) {
            Long vacancyId = null;
            String vacancyIdStr = vacancyIdTextField.getText();
            if (vacancyIdStr != null && !vacancyIdStr.isEmpty()) {
                vacancyId = Long.valueOf(vacancyIdStr);
            }

            String professionStr = vacancyProfessionTextField.getText();
            ProfField profField = vacancyProfFieldComboBox.getValue();
            WorkType workType = vacancyWorkTypeComboBox.getValue();
            Education education = vacancyEducationComboBox.getValue();
            String wageStr = vacancyWageTextField.getText();
            Currency currency = vacancyCurrencyComboBox.getValue();
            LocalDate startDateExperience = vacancyExpDateStartDatePicker.getValue();
            LocalDate endDateExperience = vacancyExpDateEndDatePicker.getValue();
            String addressStr = vacancyAddressTextArea.getText();

            Vacancy vacancy = vacancyId == null ? new Vacancy() : vacancyDao.find(vacancyId);
            vacancy.setCompanyId(companyId);
            vacancy.setProfession(professionStr);
            if (profField != null) vacancy.setProfFieldId(profField.getId());
            if (workType != null) vacancy.setWorkTypeId(workType.getId());
            if (education != null) vacancy.setEducationId(education.getId());
            if (!wageStr.isEmpty()) {
                try {
                    Wage wage = new Wage();
                    wage.setCount(new BigDecimal(wageStr.replaceAll(",",".")));
                    if (currency != null) wage.setCurrencyId(currency.getId());
                    vacancy.setWage(wage);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            if (startDateExperience != null) {
                Experience experience = new Experience();
                experience.setDateStart(startDateExperience);
                if (endDateExperience != null) experience.setDateEnd(endDateExperience);
            }

            Address address = new Address();
            address.setStreet(addressStr);

            vacancy.setAddress(address);

            if (vacancyId == null) {
                vacancyDao.create(vacancy);
            } else {
                vacancyDao.update(vacancy);
            }
            logger.debug("Created new vacancy: " + vacancy.toString());
            return true;
        } else {
            logger.debug("Fields vacancy edit block is not valid...");
            return false;
        }
    }

    private Boolean isValid() {
        Boolean isValid = true;
        if (vacancyProfessionTextField.getText() == null || vacancyProfessionTextField.getText().length() <= 0) {
            vacancyProfessionTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        return isValid;
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        hide();
        clear();
    }

    public void show() {
        vacancyEdit.setVisible(true);
        vacancyEdit.setManaged(true);
    }

    public void hide() {
        vacancyEdit.setVisible(false);
        vacancyEdit.setManaged(false);
    }

    public void clear() {
        vacancyProfessionTextField.setText("");
        vacancyProfessionTextField.getStyleClass().remove("has-error");
        vacancyProfFieldComboBox.setValue(null);
        vacancyWorkTypeComboBox.setValue(null);
        vacancyEducationComboBox.setValue(null);
        vacancyWageTextField.setText("");
        vacancyCurrencyComboBox.setValue(null);
        vacancyExpDateStartDatePicker.setValue(null);
        vacancyExpDateEndDatePicker.setValue(null);
        vacancyAddressTextArea.setText("");
    }

    public void reload(ActionEvent event) {
        actionCancel(event);

        profFieldDao.clearCash();
        workTypeDao.clearCash();
        educationDao.clearCash();
        currencyDao.clearCash();

        vacancyProfFieldComboBox.setItems(FXCollections.observableList(profFieldDao.findAllFromCash()));
        vacancyWorkTypeComboBox.setItems(FXCollections.observableList(workTypeDao.findAllFromCash()));
        vacancyEducationComboBox.setItems(FXCollections.observableList(educationDao.findAllFromCash()));
        vacancyCurrencyComboBox.setItems(FXCollections.observableList(currencyDao.findAllFromCash()));
    }

    public CompaniesTableController getCompaniesTableController() {
        return (CompaniesTableController) getControllerProvider().get(CompaniesTableController.class.getName());
    }

    public VacanciesTableController getVacanciesTableController() {
        return (VacanciesTableController) getControllerProvider().get(VacanciesTableController.class.getName());
    }
}
