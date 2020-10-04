package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.Messager;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.*;
import ru.iworking.personnel.reserve.service.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class VacancyEditController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacancyEditController.class);

    @FXML private VBox vacancyEdit;
    @FXML private TextField vacancyProfessionTextField;
    @FXML private ComboBox<ProfField> vacancyProfFieldComboBox;
    @FXML private ComboBox<WorkType> vacancyWorkTypeComboBox;
    @FXML private ComboBox<Education> vacancyEducationComboBox;
    @FXML private TextField vacancyWageTextField;
    @FXML private ComboBox<Currency> vacancyCurrencyComboBox;
    @FXML private TextField minExperienceTextField;
    @FXML private TextField maxExperienceTextField;
    @FXML private ComboBox<PeriodExperience> periodMinExperienceComboBox;
    @FXML private ComboBox<PeriodExperience> periodMaxExperienceComboBox;

    private final ProfFieldService profFieldService;
    private final WorkTypeService workTypeService;
    private final EducationService educationService;
    private final CurrencyService currencyService;
    private final VacancyService vacancyService;
    private final PeriodExperienceService periodExperienceService;

    @Autowired @Lazy private CompanyViewController companyViewController;
    @Autowired @Lazy private VacancyListViewController vacancyListViewController;

    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();
    private PeriodExperienceCellFactory periodExperienceCellFactory = new PeriodExperienceCellFactory();

    private Vacancy currentVacancy = null;

    private PeriodExperience defalutPeriodExperience;

    @PostConstruct
    public void init() {
        defalutPeriodExperience = periodExperienceService.findAll().parallelStream()
                .filter(periodExperience -> periodExperience.getIsDefault())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("element with isDefault=true in PeriodExperience not found..."));
    }

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

        minExperienceTextField.setTextFormatter(new IntegerFormatter());
        maxExperienceTextField.setTextFormatter(new IntegerFormatter());

        periodMinExperienceComboBox.setButtonCell(periodExperienceCellFactory.call(null));
        periodMinExperienceComboBox.setCellFactory(periodExperienceCellFactory);
        periodMinExperienceComboBox.setItems(FXCollections.observableList(periodExperienceService.findAll()));

        periodMaxExperienceComboBox.setButtonCell(periodExperienceCellFactory.call(null));
        periodMaxExperienceComboBox.setCellFactory(periodExperienceCellFactory);
        periodMaxExperienceComboBox.setItems(FXCollections.observableList(periodExperienceService.findAll()));
    }

    public void setData(Vacancy vacancy) {
        if (vacancy != null) {
            this.currentVacancy = vacancy;
            vacancyProfessionTextField.setText(vacancy.getProfession());
            if (vacancy.getProfFieldId() != null) vacancyProfFieldComboBox.setValue(profFieldService.findById(vacancy.getProfFieldId()));
            if (vacancy.getWorkTypeId() != null) vacancyWorkTypeComboBox.setValue(workTypeService.findById(vacancy.getWorkTypeId()));
            if (vacancy.getEducationId() != null) vacancyEducationComboBox.setValue(educationService.findById(vacancy.getEducationId()));
            if (vacancy.getWage() != null) vacancyWageTextField.setText(vacancy.getWage().getCount().toString());
            if (vacancy.getWage() != null && vacancy.getWage().getCurrency() != null) vacancyCurrencyComboBox.setValue(vacancy.getWage().getCurrency());
            if (Objects.nonNull(vacancy.getMinExperience())) {
                minExperienceTextField.setText(vacancy.getMinExperience().toString());
                if (Objects.nonNull(vacancy.getPeriodMinExperience())) {
                    periodMinExperienceComboBox.setValue(vacancy.getPeriodMinExperience());
                } else {
                    periodMinExperienceComboBox.setValue(defalutPeriodExperience);
                }
            }
            if (Objects.nonNull(vacancy.getMaxExperience())) {
                maxExperienceTextField.setText(vacancy.getMaxExperience().toString());
                if (Objects.nonNull(vacancy.getPeriodMaxExperience())) {
                    periodMaxExperienceComboBox.setValue(vacancy.getPeriodMaxExperience());
                } else {
                    periodMaxExperienceComboBox.setValue(defalutPeriodExperience);
                }
            }
        } else {
            logger.debug("vacancy is null..");
        }
    }

    @FXML
    public void actionSave(ActionEvent event) {
        Long companyId = companyViewController.getCurrentCompany().getId();
        if (isValid()) {
            Vacancy vacancy = save(companyId);
            hide();
            clear();
            vacancyListViewController.actionUpdate(event);
            vacancyListViewController.select(vacancy);
            //getVacanciesTableController().actionUpdate(event);
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

        String minExpStr = minExperienceTextField.getText();
        String maxExpStr = maxExperienceTextField.getText();

        PeriodExperience minPeriodExperience = periodMinExperienceComboBox.getValue();
        PeriodExperience maxPeriodExperience = periodMaxExperienceComboBox.getValue();

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
                if (currency != null) wage.setCurrency(currency);
                vacancy.setWage(wage);
            } catch (Exception e) {
                logger.error(e);
            }
        }

        if (Strings.isNotBlank(minExpStr)) {
            vacancy.setMinExperience(Integer.valueOf(minExpStr));
            if (Objects.nonNull(minPeriodExperience)) {
                vacancy.setPeriodMinExperience(minPeriodExperience);
            } else {
                vacancy.setPeriodMinExperience(defalutPeriodExperience);
            }
        } else {
            vacancy.setMinExperience(null);
        }

        if (Strings.isNotBlank(maxExpStr)) {
            vacancy.setMaxExperience(Integer.valueOf(maxExpStr));
            if (Objects.nonNull(maxPeriodExperience)) {
                vacancy.setPeriodMaxExperience(maxPeriodExperience);
            } else {
                vacancy.setPeriodMaxExperience(defalutPeriodExperience);
            }
        } else {
            vacancy.setMaxExperience(null);
        }

        if (vacancyId == null) {
            vacancyService.create(vacancy);
        } else {
            vacancyService.update(vacancy);
        }
        logger.debug("Created new vacancy: " + vacancy.toString());
        return vacancy;
    }

    private Boolean isValid() {
        if (vacancyProfessionTextField.getText() == null || vacancyProfessionTextField.getText().length() <= 0) {
            vacancyProfessionTextField.getStyleClass().add("has-error");
            Messager.getInstance().sendMessage("Не введены обязательные поля...");
            logger.warn("Fields vacancy edit block is not valid...");
            return false;
        }
        if (Strings.isNotBlank(minExperienceTextField.getText()) && Objects.isNull(periodMinExperienceComboBox.getValue())) {
            Messager.getInstance().sendMessage("Укажите период минимального опыта работы");
            logger.warn("pls, set period for min experience");
            return false;
        }
        if (Strings.isNotBlank(maxExperienceTextField.getText()) && Objects.isNull(periodMaxExperienceComboBox.getValue())) {
            Messager.getInstance().sendMessage("Укажите период максимального опыта работы");
            logger.warn("pls, set period for max experience");
            return false;
        }
        if (Objects.nonNull(periodMinExperienceComboBox.getValue()) && Objects.nonNull(periodMaxExperienceComboBox.getValue())) {
            LocalDate min = LocalDate.now();
            if (Strings.isNotBlank(minExperienceTextField.getText())) {
                Integer minCount = Integer.valueOf(minExperienceTextField.getText());
                switch (periodMinExperienceComboBox.getValue().getNameSystem().getName()) {
                    case "YEAR":
                        min = min.plusYears(minCount);
                        break;
                    case "MONTH":
                        min = min.plusMonths(minCount);
                        break;
                    case "DAY":
                        min = min.plusDays(minCount);
                        break;
                }
            }

            LocalDate max = LocalDate.now();
            if (Strings.isNotBlank(maxExperienceTextField.getText())) {
                Integer maxCount = Integer.valueOf(maxExperienceTextField.getText());
                switch (periodMaxExperienceComboBox.getValue().getNameSystem().getName()) {
                    case "YEAR":
                        max = max.plusYears(maxCount);
                        break;
                    case "MONTH":
                        max = max.plusMonths(maxCount);
                        break;
                    case "DAY":
                        max = max.plusDays(maxCount);
                        break;
                }
            }

            if (min.isAfter(max) || min.isEqual(max)) {
                Messager.getInstance().sendMessage("Мимимальный опыт работы не может быть больше или равен максимальному");
                logger.warn("min Experience bigger that max Experience");
                return false;
            }
        }
        return true;
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
        minExperienceTextField.setText("");
        maxExperienceTextField.setText("");
        periodMinExperienceComboBox.setValue(null);
        periodMaxExperienceComboBox.setValue(null);
    }

    public void reload(ActionEvent event) {
        actionCancel(event);
        vacancyProfFieldComboBox.setItems(FXCollections.observableList(profFieldService.findAll()));
        vacancyWorkTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));
        vacancyEducationComboBox.setItems(FXCollections.observableList(educationService.findAll()));
        vacancyCurrencyComboBox.setItems(FXCollections.observableList(currencyService.findAll()));
    }

}
