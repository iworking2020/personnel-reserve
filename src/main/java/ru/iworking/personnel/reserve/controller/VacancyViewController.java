package ru.iworking.personnel.reserve.controller;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.entity.Wage;
import ru.iworking.personnel.reserve.service.CurrencyService;
import ru.iworking.personnel.reserve.service.EducationService;
import ru.iworking.personnel.reserve.service.ProfFieldService;
import ru.iworking.personnel.reserve.service.WorkTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class VacancyViewController extends FxmlController{

    private static final Logger logger = LogManager.getLogger(VacancyViewController.class);

    @FXML private VBox vacancyView;

    @FXML private Label professionLabel;
    @FXML private Label profFieldLabel;
    @FXML private Label workTypeLabel;
    @FXML private Label educationLabel;
    @FXML private Label wageLabel;

    @Inject private ProfFieldService profFieldService;
    @Inject private WorkTypeService workTypeService;
    @Inject private EducationService educationService;
    @Inject private CurrencyService currencyService;

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

        } else {
            logger.debug("Vacancy is null. We can't view vacancy...");
        }
    }

    @FXML
    public void actionEdit(ActionEvent event) {
        getVacancyEditController().setData(currentVacancy);
        getVacancyEditController().show();
    }

    public Vacancy getCurrentVacancy() {
        return currentVacancy;
    }
    public void setCurrentVacancy(Vacancy currentVacancy) {
        this.currentVacancy = currentVacancy;
    }

    public VacancyEditController getVacancyEditController() {
        return (VacancyEditController) getControllerProvider().get(VacancyEditController.class.getName());
    }
}
