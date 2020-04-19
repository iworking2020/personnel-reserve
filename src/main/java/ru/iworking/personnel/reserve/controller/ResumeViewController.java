package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.auth.api.model.IProfile;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.AppFunctionalInterface;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.service.api.utils.LocaleUtil;
import ru.iworking.service.api.utils.TimeUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ResumeViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ResumeViewController.class);

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @FXML private Pane resumePaneView;

    @FXML private Label lastNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label middleNameLabel;
    @FXML private Label numberPhone;
    @FXML private Label email;
    @FXML private Label education;
    @FXML private Label experience;
    @FXML private Label address;
    @FXML private Label profession;
    @FXML private Label profField;
    @FXML private Label wage;
    @FXML private Label workType;

    @FXML private ImageView photoImageView;

    @FXML private Button buttonCancel;
    public Button getButtonCancel() {
        return buttonCancel;
    }

    private PhotoDao photoDao = PhotoDao.getInstance();
    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        resumePaneView.setVisible(true);
        resumePaneView.setManaged(true);
    }

    public void show(AppFunctionalInterface function) {
        function.execute();
        this.show();
    }

    public void hide() {
        resumePaneView.setVisible(false);
        resumePaneView.setManaged(false);
    }

    public void hide(AppFunctionalInterface function) {
        function.execute();
        this.hide();
    }

    public void setData(Resume resume) {
        if (resume != null) {
            String prefixNumberPhone = "Номер тел.: ";
            String prefixEmail = "Эл. почта: ";
            String prefixProfession = "Профессия: ";
            String prefixProfField = "Проф. область: ";
            String prefixWage = "Зарплата: ";
            String prefixWorkType = "График: ";
            String prefixEducation = "Образование: ";
            String prefixExperience = "Опыт паботы: ";
            String prefixAddress = "Адрес: ";

            IProfile profile = resume.getProfile();
            if (profile != null) {
                lastNameLabel.setText(profile.getLastName());
                firstNameLabel.setText(profile.getFirstName());
                middleNameLabel.setText(profile.getMiddleName());
            }
            if (resume.getNumberPhone() != null) numberPhone.setText(prefixNumberPhone + resume.getNumberPhone().getNumber());
            email.setText(prefixEmail + resume.getEmail());
            profession.setText(prefixProfession + resume.getProfession());
            if (resume.getProfFieldId() != null) {
                Long profFieldId = resume.getProfFieldId();
                profField.setText(prefixProfField + profFieldDao.findFromCash(profFieldId).getNameToView(LocaleUtil.getDefault()));
            } else {
                profField.setText(prefixProfField + "не указана");
            }
            if (resume.getWage() != null) {
                String wageString = prefixWage;
                if(resume.getWage().getCurrencyId() != null) {
                    Currency currency = currencyDao.findFromCash(resume.getWage().getCurrencyId());
                    wageString += decimalFormat.format(resume.getWage().getCountBigDecimal()) + " " + currency.getNameToView(LocaleUtil.getDefault());
                } else {
                    wageString += decimalFormat.format(resume.getWage().getCountBigDecimal());
                }
                if (wageString.length() <= prefixWage.length()) wageString += "не указана";
                wage.setText(wageString);
            } else {
                wage.setText(prefixWage + "не указана");
            }

            if (resume.getWorkTypeId() != null) {
                WorkType workType1 = workTypeDao.findFromCash(resume.getWorkTypeId());
                workType.setText(prefixWorkType + workType1.getNameToView(LocaleUtil.getDefault()));
            } else {
                workType.setText(prefixWorkType + "не указан");
            }
            if (resume.getEducationId() != null) {
                Education education1 = educationDao.findFromCash(resume.getEducationId());
                education.setText(prefixEducation + education1.getNameToView(LocaleUtil.getDefault()));
            } else {
                education.setText(prefixEducation + "не указано");
            }

            Integer age = TimeUtil.calAge(resume.getExperience().getDateStart(), resume.getExperience().getDateEnd());

            experience.setText(age == null || age <= 0 ? prefixExperience + "без опыта" : prefixExperience + age + " " + TextUtil.nameForNumbers(age));
            address.setText(prefixAddress + resume.getAddress().getHouse());
            if (resume.getPhotoId() != null) {
                Photo photo = photoDao.findFromCash(resume.getPhotoId());
                InputStream targetStream = new ByteArrayInputStream(photo.getImage());
                Image img = new Image(targetStream);
                photoImageView.setImage(img);
            } else {
                Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg"));
                photoImageView.setImage(defaultImage);
            }
        } else {
            logger.debug("Resume is null. We can't view resume...");
        }
    }
}