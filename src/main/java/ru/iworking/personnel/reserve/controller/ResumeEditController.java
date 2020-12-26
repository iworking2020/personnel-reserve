package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationJavaFX;
import ru.iworking.personnel.reserve.component.Messager;
import ru.iworking.personnel.reserve.component.layout.ExperienceHistoryEditPane;
import ru.iworking.personnel.reserve.component.layout.LearningHistoryEditPane;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.interfaces.AppFunctionalInterface;
import ru.iworking.personnel.reserve.model.*;
import ru.iworking.personnel.reserve.service.*;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResumeEditController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ResumeEditController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    @FXML private Pane resumePaneEdit;

    @FXML private TabPane resumeEdiTabPane;

    @FXML private TextField resumeIdTextField;

    @FXML private TextField lastNameTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField numberPhoneTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField professionTextField;
    @FXML private ComboBox<ProfField> profFieldComboBox;
    @FXML private TextField wageTextField;
    @FXML private ComboBox<Currency> currencyComboBox;
    @FXML private ComboBox<WorkType> workTypeComboBox;
    @FXML private TextArea addressTextArea;

    @FXML private ImageView photoImageView;

    @FXML private Button buttonCancel;

    @FXML private VBox educationEditList;
    @FXML private VBox experienceHistoryEditList;

    private ProfField currentProfField;

    private final ImageContainerService imageContainerService;
    private final ProfFieldService profFieldService;
    private final WorkTypeService workTypeService;
    private final ResumeService resumeService;
    private final CurrencyService currencyService;
    private final EducationService educationService;

    private final ImageUtil imageUtil;

    @Autowired @Lazy private ResumeViewController resumeViewController;
    @Autowired @Lazy private ResumeListViewController resumeListViewController;
    @Autowired @Lazy private VacancyListViewController vacancyListViewController;

    private ProfFieldCellFactory profFieldCellFactory = new ProfFieldCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();
    private CurrencyCellFactory currencyCellFactory = new CurrencyCellFactory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);
        wageTextField.setTextFormatter(bigDecimalFormatter);

        profFieldComboBox.setButtonCell(profFieldCellFactory.call(null));
        profFieldComboBox.setCellFactory(profFieldCellFactory);
        profFieldComboBox.setItems(FXCollections.observableList(profFieldService.findAll()));

        workTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        workTypeComboBox.setCellFactory(workTypeCellFactory);
        workTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));

        currencyComboBox.setButtonCell(currencyCellFactory.call(null));
        currencyComboBox.setCellFactory(currencyCellFactory);
        currencyComboBox.setItems(FXCollections.observableList(currencyService.findAll()));

        byte[] imageBytes = imageUtil.getDefaultResumeImage();
        if (Objects.nonNull(imageBytes) && imageBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            photoImageView.setImage(new javafx.scene.image.Image(inputStream));
        }
    }

    public void setData(Resume resume) {
        resumeIdTextField.setText(resume.getId().toString());
        lastNameTextField.setText(resume.getProfile().getLastName());
        firstNameTextField.setText(resume.getProfile().getFirstName());
        middleNameTextField.setText(resume.getProfile().getMiddleName());
        numberPhoneTextField.setText(resume.getNumberPhone().getNumber());
        emailTextField.setText(resume.getEmail());
        professionTextField.setText(resume.getProfession());
        if (resume.getProfField() != null) profFieldComboBox.setValue(resume.getProfField());
        if (resume.getWage() != null && Objects.nonNull(resume.getWage().getCount())) {
            wageTextField.setText(decimalFormat.format(resume.getWage().getCountBigDecimal()));
            if (resume.getWage().getCurrency() != null) {
                currencyComboBox.setValue(resume.getWage().getCurrency());
            }
        }
        if (resume.getWorkType() != null) workTypeComboBox.setValue(resume.getWorkType());

        addressTextArea.setText(resume.getAddress().getHouse());

        if (Objects.nonNull(resume.getPhoto())) {
            setPhotoImage(resume.getPhoto().getImage());
        } else {
            setDefaultImage();
        }

        resume.getLearningHistoryList().forEach( learningHistory -> {
            LearningHistoryEditPane learningHistoryEditPane = new LearningHistoryEditPane(educationService);
            learningHistoryEditPane.setLearningHistory(learningHistory);
            educationEditList.getChildren().add(learningHistoryEditPane);
        });

        resume.getExperienceHistoryList().forEach( experienceHistory -> {
            ExperienceHistoryEditPane experienceHistoryEditPane = new ExperienceHistoryEditPane();
            experienceHistoryEditPane.setExperienceHistory(experienceHistory);
            experienceHistoryEditList.getChildren().add(experienceHistoryEditPane);
        });
    }

    @FXML
    public void actionAddEducation(ActionEvent event) {
        LearningHistoryEditPane learningHistoryEditPane = new LearningHistoryEditPane(educationService);
        educationEditList.getChildren().add(learningHistoryEditPane);
    }

    @FXML
    public void actionAddExperienceHistory(ActionEvent event) {
        ExperienceHistoryEditPane experienceHistoryEditPane = new ExperienceHistoryEditPane();
        experienceHistoryEditList.getChildren().add(experienceHistoryEditPane);
    }

    @FXML
    private void actionLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );

        File file = fileChooser.showOpenDialog(ApplicationJavaFX.PARENT_STAGE);
        if (file != null) {
            try {
                javafx.scene.image.Image img = new javafx.scene.image.Image(file.toURI().toString());
                photoImageView.setImage(img);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        hide();
        clear();
    }

    @FXML
    public void actionSave(ActionEvent event) {
        if (isValid()) {
            Resume resume = save();
            hide();
            clear();
            resumeListViewController.actionUpdate(event);
            if (vacancyListViewController != null) vacancyListViewController.actionUpdate(event);
        } else {
            Messager.getInstance().sendMessage("Не введены обязательные поля...");
            logger.debug("resume is not valid");
        }

    }

    public Resume save() {
        Long resumeId = null;
        String vacancyIdStr = resumeIdTextField.getText();
        if (vacancyIdStr != null && !vacancyIdStr.isEmpty()) {
            resumeId = Long.valueOf(vacancyIdStr);
        }

        String lastNameStr = lastNameTextField.getText();
        String firstNameStr = firstNameTextField.getText();
        String middleNameStr = middleNameTextField.getText();
        String numberStr = numberPhoneTextField.getText();
        String emailStr = emailTextField.getText();
        String professionStr = professionTextField.getText();
        ProfField profField = profFieldComboBox.getValue();
        String wageStr = wageTextField.getText();
        Currency currency = currencyComboBox.getValue();
        WorkType workType = workTypeComboBox.getValue();
        String addressStr = addressTextArea.getText();

        Resume resume = resumeId == null ? new Resume() : resumeService.findById(resumeId);
        if (resume.getProfile() == null) resume.setProfile(new Profile());
        resume.getProfile().setLastName(lastNameStr);
        resume.getProfile().setFirstName(firstNameStr);
        resume.getProfile().setMiddleName(middleNameStr);
        if (resume.getNumberPhone() == null) resume.setNumberPhone(new NumberPhone());
        resume.getNumberPhone().setNumber(numberStr);
        resume.setEmail(emailStr);
        resume.setProfession(professionStr);
        if (profField != null) resume.setProfField(profField);
        if (wageStr != null && !wageStr.isEmpty()) {
            if (resume.getWage() == null) resume.setWage(new Wage());
            try {
                resume.getWage().setOriginalCount(new BigDecimal(wageStr.replaceAll(",",".")));
                if (currency != null) resume.getWage().setCurrency(currency);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        if (workType != null) resume.setWorkType(workType);
        if (resume.getAddress() == null) resume.setAddress(new Address());
        resume.getAddress().setHouse(addressStr);

        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BufferedImage originalImage = SwingFXUtils.fromFXImage(photoImageView.getImage(), null);
            ImageIO.write(originalImage, "png", stream);

            ImageContainer imageContainer = new ImageContainer(imageUtil.scaleToSize(stream.toByteArray(), null,300));
            resume.setPhoto(imageContainer);
        } catch (IOException e) {
            logger.error(e);
        }

        List<LearningHistory> learningHistories = educationEditList.getChildren().stream()
                .filter(node -> node instanceof LearningHistoryEditPane)
                .map(node -> (LearningHistoryEditPane) node)
                .filter(node -> node.getLearningHistory() != null)
                .map(node ->  node.getLearningHistory())
                .collect(Collectors.toList());
        resume.setLearningHistoryList(learningHistories);

        List<ExperienceHistory> experienceHistories = experienceHistoryEditList.getChildren().stream()
                .filter(node -> node instanceof ExperienceHistoryEditPane)
                .map(node -> (ExperienceHistoryEditPane) node)
                .filter(node -> node.getExperienceHistory() != null)
                .map(node ->  node.getExperienceHistory())
                .collect(Collectors.toList());
        resume.setExperienceHistoryList(experienceHistories);

        if (resumeId == null) {
            resumeService.create(resume);
        } else {
            resumeService.update(resume);
        }
        logger.debug("Created new resume: " + resume.toString());
        return resume;
    }

    private Boolean isValid() {
        Boolean isValid = true;
        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() <= 0) {
            firstNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() <= 0) {
            lastNameTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        return isValid;
    }

    public void clear() {
        resumeIdTextField.setText("");
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        numberPhoneTextField.setText("");
        emailTextField.setText("");
        professionTextField.setText("");
        profFieldComboBox.setValue(null);
        wageTextField.setText("");
        currencyComboBox.setValue(null);
        workTypeComboBox.setValue(null);
        addressTextArea.setText("");
        List<LearningHistoryEditPane> removeList = educationEditList.getChildren().stream()
                .filter(node -> node instanceof LearningHistoryEditPane)
                .map(node -> (LearningHistoryEditPane) node)
                .collect(Collectors.toList());
        educationEditList.getChildren().removeAll(removeList);
        educationEditList.getChildren().clear();
        List<ExperienceHistoryEditPane> experienceHistoryEditPanes = experienceHistoryEditList.getChildren().stream()
                .filter(node -> node instanceof ExperienceHistoryEditPane)
                .map(node -> (ExperienceHistoryEditPane) node)
                .collect(Collectors.toList());
        experienceHistoryEditList.getChildren().removeAll(experienceHistoryEditPanes);
        experienceHistoryEditList.getChildren().clear();
        resumeEdiTabPane.getSelectionModel().select(0);
        setDefaultImage();
    }

    public void setPhotoImage(byte[] image) {
        InputStream targetStream = new ByteArrayInputStream(image);
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        photoImageView.setImage(img);
    }

    public void setPhotoImageById(Long id) {
        ImageContainer imageContainer = imageContainerService.findById(id);
        this.setPhotoImage(imageContainer.getImage());
    }

    public void setDefaultImage() {
        byte[] imageBytes = imageUtil.getDefaultResumeImage();
        if (Objects.nonNull(imageBytes) && imageBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                    inputStream,
                    300,
                    300,
                    false,
                    false);
            photoImageView.setImage(defaultImage);
        }
    }

    public void show() {
        resumePaneEdit.setVisible(true);
        resumePaneEdit.setManaged(true);
    }

    public void show(AppFunctionalInterface function) {
        function.execute();
        this.show();
    }

    public void hide() {
        resumePaneEdit.setVisible(false);
        resumePaneEdit.setManaged(false);
    }

    public void hide(AppFunctionalInterface function) {
        function.execute();
        this.hide();
    }

}
