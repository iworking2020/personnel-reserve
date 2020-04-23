package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CurrencyDao;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.service.api.model.IWage;
import ru.iworking.service.api.utils.LocaleUtil;
import ru.iworking.vacancy.api.model.IVacancy;

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
    @FXML private Label addressLabel;

    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();

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

    public void setData(IVacancy vacancy) {
        if (vacancy != null) {
            String prefixProfession = "Профессия: ";
            String prefixProfField = "Проф. область: ";
            String prefixWorkType = "График работы: ";
            String prefixEducation = "Образование: ";
            String prefixWage = "Зарплата: ";
            String prefixAddress = "Адрес: ";

            professionLabel.setText(prefixProfession + vacancy.getProfession());

            Long profFieldId = vacancy.getProfFieldId();
            profFieldLabel.setText(
                    profFieldId != null ?
                            prefixProfField + profFieldDao.findFromCash(profFieldId).getNameToView(LocaleUtil.getDefault()) :
                            prefixProfField + "не указана");

            Long workTypeId = vacancy.getWorkTypeId();
            workTypeLabel.setText(
                    workTypeId != null ?
                            prefixWorkType + workTypeDao.findFromCash(workTypeId).getNameToView(LocaleUtil.getDefault()) :
                            prefixWorkType + "не указан");

            Long educationId = vacancy.getEducationId();
            educationLabel.setText(
                    educationId != null ?
                            prefixEducation + educationDao.findFromCash(educationId).getNameToView(LocaleUtil.getDefault()) :
                            prefixEducation + "не указано");

            IWage wage = vacancy.getWage();
            if (wage != null) {
                String wageStr = prefixWage + wage.getCount();
                Long currencyId = vacancy.getWage().getCurrencyId();
                if (currencyId != null) {
                    Currency currency = currencyDao.findFromCash(currencyId);
                    wageStr += " " + currency.getNameToView(LocaleUtil.getDefault());
                }
                wageLabel.setText(wageStr);
            }

            addressLabel.setText(
                    vacancy.getAddress() != null ?
                        prefixAddress + vacancy.getAddress().getStreet() :
                        prefixAddress + "не указан");

        } else {
            logger.debug("Vacancy is null. We can't view vacancy...");
        }
    }

}
