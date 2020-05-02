package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.interfaces.AppFunctionalInterface;
import ru.iworking.personnel.reserve.service.CurrencyService;
import ru.iworking.personnel.reserve.service.EducationService;
import ru.iworking.personnel.reserve.service.ProfFieldService;
import ru.iworking.personnel.reserve.service.WorkTypeService;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.utils.TimeUtil;
import ru.iworking.personnel.reserve.utils.docs.pdf.PdfResumeWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResumeViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumeViewController.class);

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @FXML private Pane resumePaneView;

    @FXML private TextField resumeIdTextField;

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
    private ProfFieldService profFieldService = ProfFieldService.INSTANCE;
    private CurrencyService currencyService = CurrencyService.INSTANCE;
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private ResumeDao resumeDao = ResumeDao.getInstance();

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

            resumeIdTextField.setText(resume.getId().toString());

            Profile profile = resume.getProfile();
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
                profField.setText(prefixProfField + profFieldService.findById(profFieldId).getNameView().getName());
            } else {
                profField.setText(prefixProfField + "не указана");
            }
            if (resume.getWage() != null) {
                String wageString = prefixWage;
                if(resume.getWage().getCurrencyId() != null) {
                    Currency currency = currencyService.findById(resume.getWage().getCurrencyId());
                    wageString += decimalFormat.format(resume.getWage().getCountBigDecimal()) + " " + currency.getNameView().getName();
                } else {
                    wageString += decimalFormat.format(resume.getWage().getCountBigDecimal());
                }
                if (wageString.length() <= prefixWage.length()) wageString += "не указана";
                wage.setText(wageString);
            } else {
                wage.setText(prefixWage + "не указана");
            }

            if (resume.getWorkTypeId() != null) {
                WorkType workType1 = workTypeService.findById(resume.getWorkTypeId());
                workType.setText(prefixWorkType + workType1.getNameView().getName());
            } else {
                workType.setText(prefixWorkType + "не указан");
            }
            if (resume.getEducationId() != null) {
                Education education1 = educationService.findById(resume.getEducationId());
                education.setText(prefixEducation + education1.getNameView().getName());
            } else {
                education.setText(prefixEducation + "не указано");
            }

            Integer age = TimeUtil.calAge(resume.getExperience().getDateStart(), resume.getExperience().getDateEnd());

            experience.setText(age == null || age <= 0 ? prefixExperience + "без опыта" : prefixExperience + age + " " + TextUtil.nameForNumbers(age));
            address.setText(prefixAddress + resume.getAddress().getHouse());
            if (resume.getPhotoId() != null) {
                Photo photo = photoDao.find(resume.getPhotoId());
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

    private Long getResumeId() {
        String resumeId = resumeIdTextField.getText();
        if (resumeId.length() > 0 ) {
            return Long.valueOf(resumeId);
        } else {
            return null;
        }
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        VacanciesPaneController vacanciesPaneController = (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class.getName());
        hide();
        vacanciesPaneController.showWrapperClient();
    }

    @FXML
    public void actionSavePdf(ActionEvent event) {
        Resume resume = resumeDao.find(getResumeId());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Resume"+resume.getId()+".pdf");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );

        File file = fileChooser.showSaveDialog(MainApp.PARENT_STAGE);
        if (file != null) {
            String path = file.getAbsoluteFile().getAbsolutePath();
            Map props = new HashMap<>();
            props.put(PdfResumeWriter.props.PATH, path);
            props.put(PdfResumeWriter.props.RESUME, resume);
            PdfResumeWriter.getInstance().write(props);
        }
    }

    @FXML
    public void actionEdit(ActionEvent event) throws Exception {
        Resume resume = resumeDao.find(getResumeId());
        getResumeEditController().setData(resume);
        getResumeEditController().show();
        getVacanciesPaneController().hideWrapperClient();
    }

    public ResumeEditController getResumeEditController() {
        return (ResumeEditController) getControllerProvider().get(ResumeEditController.class.getName());
    }

    public VacanciesPaneController getVacanciesPaneController() {
        return (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class.getName());
    }

}
