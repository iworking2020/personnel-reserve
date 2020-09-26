package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.entity.PeriodExperience;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.entity.Wage;
import ru.iworking.personnel.reserve.service.*;
import ru.iworking.personnel.reserve.utils.TextUtil;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class VacancyViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacancyViewController.class);

    @FXML private VBox vacancyView;

    @FXML private Label professionLabel;
    @FXML private Label profFieldLabel;
    @FXML private Label workTypeLabel;
    @FXML private Label educationLabel;
    @FXML private Label wageLabel;
    @FXML private Label experienceLabel;

    private final ProfFieldService profFieldService;
    private final WorkTypeService workTypeService;
    private final EducationService educationService;
    private final CurrencyService currencyService;
    private final VacancyService vacancyService;

    @Autowired @Lazy private VacancyEditController vacancyEditController;
    @Autowired @Lazy private VacancyListViewController vacancyListViewController;

    private Vacancy currentVacancy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        vacancyView.setVisible(true);
        vacancyView.setManaged(true);
    }

    public void hide() {
        vacancyView.setVisible(false);
        vacancyView.setManaged(false);
    }

    public void clear() {
        currentVacancy = null;
    }

    public void setData(Vacancy vacancy) {
        if (vacancy != null) {
            this.currentVacancy = vacancy;

            String prefixProfession = "Профессия: ";
            String prefixProfField = "Проф. область: ";
            String prefixWorkType = "График работы: ";
            String prefixEducation = "Образование: ";
            String prefixWage = "Зарплата: ";
            String prefixExperience = "Опыт работы: ";

            professionLabel.setText(prefixProfession + vacancy.getProfession());

            Long profFieldId = vacancy.getProfFieldId();
            profFieldLabel.setText(
                    profFieldId != null ?
                            prefixProfField + profFieldService.findById(profFieldId).getNameView().getName() :
                            prefixProfField + "не указана");

            Long workTypeId = vacancy.getWorkTypeId();
            workTypeLabel.setText(
                    workTypeId != null ?
                            prefixWorkType + workTypeService.findById(workTypeId).getNameView().getName() :
                            prefixWorkType + "не указан");

            Long educationId = vacancy.getEducationId();
            educationLabel.setText(
                    educationId != null ?
                            prefixEducation + educationService.findById(educationId).getNameView().getName() :
                            prefixEducation + "не указано");

            Wage wage = vacancy.getWage();
            if (wage != null) {
                String wageStr = prefixWage + wage.getCount();
                Long currencyId = vacancy.getWage().getCurrencyId();
                if (currencyId != null) {
                    Currency currency = currencyService.findById(currencyId);
                    wageStr += " " + currency.getNameView().getName();
                }
                wageLabel.setText(wageStr);
            } else {
                wageLabel.setText(prefixWage + "не указана");
            }

            String expStr = prefixExperience;
            if (Objects.nonNull(vacancy.getMinExperience())) {
                Integer minExp = vacancy.getMinExperience();
                PeriodExperience periodMinExperience = vacancy.getPeriodMinExperience();
                String nameMinExp;
                switch (periodMinExperience.getNameSystem().getName()) {
                    case "YEAR":
                        nameMinExp = TextUtil.nameForYears(minExp);
                        break;
                    case "MONTH":
                        nameMinExp = TextUtil.nameForMonths(minExp);
                        break;
                    case "DAY":
                        nameMinExp = TextUtil.nameForDays(minExp);
                        break;
                    default:
                        nameMinExp = periodMinExperience.getNameView().getName();
                }
                if (Objects.nonNull(vacancy.getMaxExperience())) {
                    Integer maxExp = vacancy.getMaxExperience();
                    PeriodExperience periodMaxExperience = vacancy.getPeriodMaxExperience();
                    String nameMaxExp;
                    switch (periodMaxExperience.getNameSystem().getName()) {
                        case "YEAR":
                            nameMaxExp = TextUtil.nameForYears(maxExp);
                            break;
                        case "MONTH":
                            nameMaxExp = TextUtil.nameForMonths(maxExp);
                            break;
                        case "DAY":
                            nameMaxExp = TextUtil.nameForDays(maxExp);
                            break;
                        default:
                            nameMaxExp = periodMaxExperience.getNameView().getName();
                    }
                    experienceLabel.setText(expStr += String.format("%s %s - %s %s", minExp, nameMinExp, maxExp, nameMaxExp));
                } else {
                    experienceLabel.setText(expStr += String.format("%s %s", minExp, nameMinExp));
                }
            } else {
                experienceLabel.setText(expStr += "не указан");
            }

        } else {
            logger.debug("Vacancy is null. We can't view vacancy...");
        }
    }

    @FXML
    public void actionEdit(ActionEvent event) {
        vacancyEditController.setData(currentVacancy);
        vacancyEditController.show();
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        vacancyService.deleteById(currentVacancy.getId());
        hide();
        clear();
        vacancyListViewController.actionUpdate(event);
    }

    public Vacancy getCurrentVacancy() {
        return currentVacancy;
    }
    public void setCurrentVacancy(Vacancy currentVacancy) {
        this.currentVacancy = currentVacancy;
    }



}
