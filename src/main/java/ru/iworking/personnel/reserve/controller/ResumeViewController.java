package ru.iworking.personnel.reserve.controller;

import com.google.common.base.Strings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.component.Messager;
import ru.iworking.personnel.reserve.component.VacancyListViewPane;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.interfaces.AppFunctionalInterface;
import ru.iworking.personnel.reserve.service.*;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.utils.TimeUtil;
import ru.iworking.personnel.reserve.utils.docs.pdf.PdfResumeWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ResumeViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumeViewController.class);

    @Autowired private PhotoService photoService;
    @Autowired private ProfFieldService profFieldService;
    @Autowired private CurrencyService currencyService;
    @Autowired private WorkTypeService workTypeService;
    @Autowired private EducationService educationService;
    @Autowired private ResumeService resumeService;
    @Autowired private ClickService clickService;
    @Autowired private ResumeStateService resumeStateService;
    @Autowired private VacancyService vacancyService;
    @Autowired private PdfResumeWriter pdfResumeWriter;

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

    @FXML private ScrollPane learningHistoryPane;
    @FXML private ScrollPane experienceHistoryPane;

    @FXML private TabPane resumeViewTabPane;

    @FXML private ImageView photoImageView;

    @FXML private Button buttonCancel;
    @FXML private Button clickButton;

    private Resume currentResume = null;
    private boolean isShow = false;

    public Button getButtonCancel() {
        return buttonCancel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        isShow = true;
        resumePaneView.setVisible(true);
        resumePaneView.setManaged(true);
    }

    public boolean isShow() {
        return isShow;
    }

    public void show(AppFunctionalInterface function) {
        function.execute();
        this.show();
    }

    public void hide() {
        isShow = false;
        resumePaneView.setVisible(false);
        resumePaneView.setManaged(false);
    }

    public void hide(AppFunctionalInterface function) {
        function.execute();
        this.hide();
    }

    public void isDisableClickButton(boolean isDisable) {
        clickButton.setDisable(isDisable);
    }

    public void setData(Resume resume) {
        clear();
        if (resume != null) {
            this.currentResume = resume;
            String prefixNumberPhone = "Номер тел.: ";
            String prefixEmail = "Эл. почта: ";
            String prefixProfession = "Профессия: ";
            String prefixProfField = "Проф. область: ";
            String prefixWage = "Зарплата: ";
            String prefixWorkType = "График: ";
            String prefixEducation = "Образование: ";
            String prefixExperience = "Опыт паботы: ";
            String prefixAddress = "Адрес: ";

            Profile profile = resume.getProfile();
            if (profile != null) {
                lastNameLabel.setText(profile.getLastName());
                firstNameLabel.setText(profile.getFirstName());
                middleNameLabel.setText(profile.getMiddleName());
            }
            if (resume.getNumberPhone() != null) numberPhone.setText(prefixNumberPhone + resume.getNumberPhone().getNumber());
            email.setText(prefixEmail + resume.getEmail());
            if (!Strings.isNullOrEmpty(resume.getProfession())) {
                profession.setText(prefixProfession + resume.getProfession());
            } else {
                profession.setText(prefixProfession + "не указана");
            }

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
            address.setText(prefixAddress + resume.getAddress().getHouse());
            if (resume.getPhotoId() != null) {
                setPhotoImageById(resume.getPhotoId());
            } else {
                setDefaultImage();
            }

            setDataLearningHistory(resume.getLearningHistoryList());
            setDataExperienceHistory(resume.getExperienceHistoryList());
        } else {
            logger.debug("Resume is null. We can't view resume...");
        }
    }

    public void setPhotoImageById(Long id) {
        Photo photo = photoService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(photo.getImage());
        Image img = new Image(targetStream);
        photoImageView.setImage(img);
    }

    public void setDefaultImage() {
        Image defaultImage = new Image(
                getClass().getClassLoader().getResourceAsStream("images/default-resume.jpg"),
                150,
                150,
                false,
                false);
        photoImageView.setImage(defaultImage);
    }

    private void setDataLearningHistory(List<LearningHistory> list) {
        VBox pane = new VBox();
        list.stream().forEach(learningHistory -> {
            VBox wrap = new VBox();
            VBox.setMargin(wrap, new Insets(10.0, 0.0, 10.0, 0.0));
            wrap.getChildren().add(new Label(learningHistory.getEducation().getNameView().getName()));
            wrap.getChildren().add(new Label(learningHistory.getDescription()));
            pane.getChildren().add(wrap);
        });
        learningHistoryPane.setContent(pane);
    }

    private void setDataExperienceHistory(List<ExperienceHistory> list) {
        VBox pane = new VBox();
        list.stream().forEach(experienceHistory -> {
            VBox wrap = new VBox();
            VBox.setMargin(wrap, new Insets(10.0, 0.0, 10.0, 0.0));

            Integer age = TimeUtil.calAge(experienceHistory.getDateStart(), experienceHistory.getDateEnd());
            String experience = age == null || age <= 0 ? "без опыта" : age + " " + TextUtil.nameForNumbers(age);

            wrap.getChildren().add(new Label("Опыт работы: " + experience));
            wrap.getChildren().add(new Label(experienceHistory.getDescription()));
            pane.getChildren().add(wrap);
        });
        experienceHistoryPane.setContent(pane);
    }

    private Long getResumeId() {
        return currentResume.getId();
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        getResumeListViewController().clearSelection();
        getClickListViewController().clearSelection();
        hide();
    }

    @FXML
    public void actionSavePdf(ActionEvent event) {
        Resume resume = resumeService.findById(getResumeId());
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
            pdfResumeWriter.write(props);
        }
    }

    @FXML
    public void actionEdit(ActionEvent event) throws Exception {
        Resume resume = resumeService.findById(getResumeId());
        getResumeEditController().setData(resume);
        getResumeEditController().show();
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        resumeService.deleteById(currentResume.getId());
        getResumeListViewController().actionUpdate(event);
        if (getVacancyListViewPane() != null) getVacancyListViewPane().actionUpdate(event);
        hide();
        clear();
    }

    @FXML
    public void actionClick(ActionEvent event) {
        ResumeState resumeState = resumeStateService.findById(1L);
        Vacancy vacancy = getVacancyViewController().getCurrentVacancy();

        Set<Click> clicks = vacancy.getClicks().stream()
                .filter(click1 -> click1.getResume().getId() == this.currentResume.getId())
                .collect(Collectors.toSet());

        if (vacancy != null && clicks.isEmpty()) {
            Click click = Click.builder()
                    .resume(this.currentResume)
                    .vacancy(vacancy)
                    .resumeState(resumeState)
                    .build();
            clickService.persist(click);
            getVacancyListViewPane().actionUpdate(event);
        } else {
            Messager.getInstance().sendMessage("Резюме уже прикреплено к данной вакансии...");
        }
    }

    public void clear() {
        this.currentResume = null;
        resumeViewTabPane.getSelectionModel().select(0);
        setDefaultImage();
    }

    public ResumeEditController getResumeEditController() {
        return (ResumeEditController) getControllerProvider().get(ResumeEditController.class.getName());
    }

    public VacancyViewController getVacancyViewController() {
        return (VacancyViewController) getControllerProvider().get(VacancyViewController.class.getName());
    }

    public VacancyListViewPane getVacancyListViewPane() {
        return (VacancyListViewPane) getControllerProvider().get(VacancyListViewPane.class.getName());
    }

    public ResumeListViewController getResumeListViewController() {
        return (ResumeListViewController) getControllerProvider().get(ResumeListViewController.class.getName());
    }

    public ClickListViewController getClickListViewController() {
        return (ClickListViewController) getControllerProvider().get(ClickListViewController.class.getName());
    }

}
