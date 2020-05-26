package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iworking.personnel.reserve.component.Messager;
import ru.iworking.personnel.reserve.component.VacancyListViewPane;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.CurrencyCellFactory;
import ru.iworking.personnel.reserve.model.EducationCellFactory;
import ru.iworking.personnel.reserve.model.ProfFieldCellFactory;
import ru.iworking.personnel.reserve.model.WorkTypeCellFactory;
import ru.iworking.personnel.reserve.service.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VacancyEditController extends FxmlController {

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

    @Autowired private ProfFieldService profFieldService;
    @Autowired private WorkTypeService workTypeService;
    @Autowired private EducationService educationService;
    @Autowired private CurrencyService currencyService;
    @Autowired private VacancyService vacancyService;

    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();

    private Vacancy currentVacancy = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        vacancyProfFieldComboBox.setButtonCell(profFieldCellFactory.call(null));
        vacancyProfFieldComboBox.setCellFactory(profFieldCellFactory);
        vacancyProfFieldComboBox.setItems(FXCollections.observableList(profFieldService.findAll()));

        vacancyWorkTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        vacancyWorkTypeComboBox.setCellFactory(workTypeCellFactory);
        vacancyWorkTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));

        vacancyEducationComboBox.setButtonCell(educationCellFactory.call(null));
        vacancyEducationComboBox.setCellFactory(educationCellFactory);
        vacancyEducationComboBox.setItems(FXCollections.observableList(educationService.findAll()));

        vacancyCurrencyComboBox.setButtonCell(currencyCellFactory.call(null));
        vacancyCurrencyComboBox.setCellFactory(currencyCellFactory);
        vacancyCurrencyComboBox.setItems(FXCollections.observableList(currencyService.findAll()));
    }

    public void setData(Vacancy vacancy) {
        if (vacancy != null) {
            this.currentVacancy = vacancy;
            vacancyProfessionTextField.setText(vacancy.getProfession());
            if (vacancy.getProfFieldId() != null) vacancyProfFieldComboBox.setValue(profFieldService.findById(vacancy.getProfFieldId()));
            if (vacancy.getWorkTypeId() != null) vacancyWorkTypeComboBox.setValue(workTypeService.findById(vacancy.getWorkTypeId()));
            if (vacancy.getEducationId() != null) vacancyEducationComboBox.setValue(educationService.findById(vacancy.getEducationId()));
            if (vacancy.getWage() != null) vacancyWageTextField.setText(vacancy.getWage().getCount().toString());
            if (vacancy.getWage() != null && vacancy.getWage().getCurrencyId() != null) vacancyCurrencyComboBox.setValue(currencyService.findById(vacancy.getWage().getCurrencyId()));
            if (vacancy.getExperience() != null) {
                vacancyExpDateStartDatePicker.setValue(vacancy.getExperience().getDateStart());
                vacancyExpDateEndDatePicker.setValue(vacancy.getExperience().getDateEnd());
            }
        } else {
            logger.debug("vacancy is null..");
        }
    }

    @FXML
    public void actionSave(ActionEvent event) {
        Long companyId = getCompanyViewController().getCurrentCompany().getId();
        if (isValid()) {
            Vacancy vacancy = save(companyId);
            hide();
            clear();
            getVacancyListViewPane().actionUpdate(event);
            getVacancyListViewPane().select(vacancy);
            //getVacanciesTableController().actionUpdate(event);
        } else {
            Messager.getInstance().sendMessage("Не введены обязательные поля...");
            logger.debug("Fields vacancy edit block is not valid...");
        }

    }

    public Vacancy save(Long companyId) {
        Long vacancyId = null;
        if (currentVacancy != null) vacancyId = currentVacancy.getId();

        String professionStr = vacancyProfessionTextField.getText();
        ProfField profField = vacancyProfFieldComboBox.getValue();
        WorkType workType = vacancyWorkTypeComboBox.getValue();
        Education education = vacancyEducationComboBox.getValue();
        String wageStr = vacancyWageTextField.getText();
        Currency currency = vacancyCurrencyComboBox.getValue();
        LocalDate startDateExperience = vacancyExpDateStartDatePicker.getValue();
        LocalDate endDateExperience = vacancyExpDateEndDatePicker.getValue();

        Vacancy vacancy = vacancyId == null ? new Vacancy() : vacancyService.findById(vacancyId);
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

        if (vacancyId == null) {
            vacancyService.persist(vacancy);
        } else {
            vacancyService.update(vacancy);
        }
        logger.debug("Created new vacancy: " + vacancy.toString());
        return vacancy;
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
        currentVacancy = null;
        vacancyProfessionTextField.setText("");
        vacancyProfessionTextField.getStyleClass().remove("has-error");
        vacancyProfFieldComboBox.setValue(null);
        vacancyWorkTypeComboBox.setValue(null);
        vacancyEducationComboBox.setValue(null);
        vacancyWageTextField.setText("");
        vacancyCurrencyComboBox.setValue(null);
        vacancyExpDateStartDatePicker.setValue(null);
        vacancyExpDateEndDatePicker.setValue(null);
    }

    public void reload(ActionEvent event) {
        actionCancel(event);
        vacancyProfFieldComboBox.setItems(FXCollections.observableList(profFieldService.findAll()));
        vacancyWorkTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));
        vacancyEducationComboBox.setItems(FXCollections.observableList(educationService.findAll()));
        vacancyCurrencyComboBox.setItems(FXCollections.observableList(currencyService.findAll()));
    }

    public CompanyViewController getCompanyViewController() {
        return (CompanyViewController) getControllerProvider().get(CompanyViewController.class.getName());
    }

    public VacancyListViewPane getVacancyListViewPane() {
        return (VacancyListViewPane) getControllerProvider().get(VacancyListViewPane.class.getName());
    }
}
