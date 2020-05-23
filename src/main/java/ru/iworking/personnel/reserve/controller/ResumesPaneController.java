package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.BigDecimalFormatter;
import ru.iworking.personnel.reserve.model.EducationCellFactory;
import ru.iworking.personnel.reserve.model.WorkTypeCellFactory;
import ru.iworking.personnel.reserve.props.ResumeRequestParam;
import ru.iworking.personnel.reserve.service.*;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.utils.TimeUtil;
import ru.iworking.personnel.reserve.utils.docs.exel.ExelResumeListWriter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class ResumesPaneController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumesPaneController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private ResumeService resumeService = ResumeService.INSTANCE;
    private ProfFieldService profFieldService = ProfFieldService.INSTANCE;
    private CurrencyService currencyService = CurrencyService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;

    @FXML private TableView<Resume> table;
    @FXML private TableColumn<Resume, String> lastNameColumn;
    @FXML private TableColumn<Resume, String> firstNameColumn;
    @FXML private TableColumn<Resume, String> middleNameColumn;
    @FXML private TableColumn<Resume, String> professionColumn;
    @FXML private TableColumn<Resume, String> workTypeColumn;
    @FXML private TableColumn<Resume, String> wageColumn;
    @FXML private TableColumn<Resume, String> currencyColumn;
    @FXML private TableColumn<Resume, String> educationColumn;
    @FXML private TableColumn<Resume, String> experienceColumn;

    @FXML private VBox profFieldVBox;

    @FXML private StackPane backgroundResumeImagePane;

    @FXML private VBox searchPane;
    @FXML private StackPane tablePane;
    @FXML private GridPane buttonsPane;

    public VBox getSearchPane() {
        return searchPane;
    }
    public StackPane getTablePane() {
        return tablePane;
    }
    public GridPane getButtonsPane() {
        return buttonsPane;
    }

    @FXML private TextField lastNameTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private ComboBox<Education> educationComboBox;
    @FXML private TextField professionTextField;
    @FXML private TextField wageTextField;
    @FXML private ComboBox<WorkType> workTypeComboBox;

    private ObservableList<Resume> resumeObservableList;
    private ProfField currentProfField = null;
    private EducationCellFactory educationCellFactory = new EducationCellFactory();
    private WorkTypeCellFactory workTypeCellFactory = new WorkTypeCellFactory();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wageTextField.setTextFormatter(bigDecimalFormatter);

        workTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        workTypeComboBox.setCellFactory(workTypeCellFactory);
        workTypeComboBox.setItems(FXCollections.observableList(workTypeService.findAll()));

        educationComboBox.setButtonCell(educationCellFactory.call(null));
        educationComboBox.setCellFactory(educationCellFactory);
        educationComboBox.setItems(FXCollections.observableList(educationService.findAll()));

        lastNameColumn.setCellValueFactory(cellData -> {
            Profile profile = cellData.getValue().getProfile();
            return new ReadOnlyStringWrapper(profile.getLastName());
        });
        firstNameColumn.setCellValueFactory(cellData -> {
            Profile profile = cellData.getValue().getProfile();
            return new ReadOnlyStringWrapper(profile.getFirstName());
        });
        middleNameColumn.setCellValueFactory(cellData -> {
            Profile profile = cellData.getValue().getProfile();
            return new ReadOnlyStringWrapper(profile.getMiddleName());
        });
        professionColumn.setCellValueFactory(new PropertyValueFactory<>("profession"));
        workTypeColumn.setCellValueFactory(cellData -> {
            WorkType workType = null;
            if (cellData.getValue().getWorkTypeId() != null)  workType = workTypeService.findById(cellData.getValue().getWorkTypeId());
            String textColumn = workType != null ? workType.getNameView().getName() : "не указан";
            return new ReadOnlyStringWrapper(textColumn);
        });
        wageColumn.setCellValueFactory(cellData -> {
            BigDecimal wage = null;
            if (cellData.getValue().getWage() != null) wage = cellData.getValue().getWage().getCountBigDecimal();
            String textColumn = wage != null ? decimalFormat.format(wage) : "договорная";
            return new ReadOnlyStringWrapper(textColumn);
        });
        currencyColumn.setCellValueFactory(cellData -> {
            Wage wage = cellData.getValue().getWage();
            Currency currency = wage != null ? currencyService.findById(wage.getCurrencyId()) : null;
            String textColumn = currency != null ? currency.getNameView().getName() : "не указана";
            return new ReadOnlyStringWrapper(textColumn);
        });
        educationColumn.setCellValueFactory(cellData -> {
            Education education = null;
            if (cellData.getValue().getLearningHistoryList()!= null && !cellData.getValue().getLearningHistoryList().isEmpty()) {
                LearningHistory learningHistory = cellData.getValue().getLearningHistoryList().stream()
                        .max(Comparator.comparing(LearningHistory::getId))
                        .orElseThrow(NoSuchElementException::new);
                education = learningHistory.getEducation();
            }
            String textColumn = education != null ? education.getNameView().getName() : "не указано";
            return new ReadOnlyStringWrapper(textColumn);
        });
        experienceColumn.setCellValueFactory(cellData -> {
            LocalDate minLocalDate = null;
            LocalDate maxLocalDate = null;
            if (cellData.getValue().getExperienceHistoryList() != null && !cellData.getValue().getExperienceHistoryList().isEmpty()) {
                minLocalDate = cellData.getValue().getExperienceHistoryList().stream()
                        .map(ExperienceHistory::getDateStart)
                        .filter(Objects::nonNull)
                        .min(LocalDate::compareTo)
                        .get();
                maxLocalDate = cellData.getValue().getExperienceHistoryList().stream()
                        .map(ExperienceHistory::getDateEnd)
                        .filter(Objects::nonNull)
                        .max(LocalDate::compareTo)
                        .get();
            }

            Integer age = TimeUtil.calAge(minLocalDate, maxLocalDate);

            String textColumn = age == null || age <= 0 ? "без опыта" : age + " " + TextUtil.nameForNumbers(age);

            return new ReadOnlyStringWrapper(textColumn);
        });

        Button buttonFindAll = new Button();
        buttonFindAll.setText("все категории");
        buttonFindAll.setOnAction(event -> {
            selectCategory(event, null);
            actionButtonClear(event);
        });
        profFieldVBox.getChildren().add(buttonFindAll);

        profFieldService.findAll().stream().forEach(profField -> {
            Button button = new Button();
            button.setText(profField.getNameView().getName());
            button.setOnAction(event -> {
                selectCategory(event, profField);
                actionButtonClear(event);
            });
            profFieldVBox.getChildren().add(button);
        });

        this.resumeObservableList = this.createResumeObservableList(resumeService.findAll());
        table.setItems(resumeObservableList);
    }

    public void selectCategory(ActionEvent event, ProfField profField) {
        this.currentProfField = profField;
        List<Resume> list = profField != null ?
                resumeService.findAllByProfFieldId(profField.getId()) :
                resumeService.findAll();
        this.resumeObservableList = this.createResumeObservableList(list);
        table.setItems(resumeObservableList);
        Long profFieldId = profField != null ? profField.getId() : 0;
        String style = "-fx-background-image: url('images/fone/fone"+profFieldId+".png');";
        backgroundResumeImagePane.setStyle(style);
    }

    public void show(Parent root) {
        Stage stage = MainApp.PARENT_STAGE;
        stage.setTitle("Personnel reserve");
        AppUtil.setIcon(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<Resume> createResumeObservableList(List<Resume> list) {
        ObservableList<Resume> resumeObservableList = FXCollections.observableList(list);
        return resumeObservableList;
    }

    @FXML
    private void actionButtonAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalAddResume.fxml"));
        Parent parent = fxmlLoader.load();

        ModalAddResumeController dialogController = fxmlLoader.getController();
        dialogController.setCurrentProfField(currentProfField);
        dialogController.showAndWait(parent);
        selectCategory(event, currentProfField);
    }

    @FXML
    private void actionButtonEdit(ActionEvent event) throws IOException {
        Resume resume = table.getSelectionModel().getSelectedItem();

        if (resume != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalEditResume.fxml"));
            Parent parent = fxmlLoader.load();

            ModalEditResumeController dialogController = fxmlLoader.getController();
            dialogController.setResume(resume);
            dialogController.setCurrentProfField(currentProfField);
            dialogController.showAndWait(parent);
            selectCategory(event, currentProfField);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalMessage.fxml"));
            Parent parent = fxmlLoader.load();

            ModalMessageController dialogController = fxmlLoader.getController();
            dialogController.setTitle("Ошибка");
            dialogController.setMessage("Выберите объект");
            dialogController.setNameButton("Хорошо");
            dialogController.showAndWait(parent);
        }
    }

    @FXML
    private void actionButtonOpen(ActionEvent event) throws IOException {
        Resume resume = table.getSelectionModel().getSelectedItem();

        if (resume != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalOpenResume.fxml"));
            Parent parent = fxmlLoader.load();

            ModalOpenResumeController dialogController = fxmlLoader.getController();
            dialogController.setResume(resume);
            dialogController.showAndWait(parent);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalMessage.fxml"));
            Parent parent = fxmlLoader.load();

            ModalMessageController dialogController = fxmlLoader.getController();
            dialogController.setTitle("Ошибка");
            dialogController.setMessage("Выберите объект");
            dialogController.setNameButton("Хорошо");
            dialogController.showAndWait(parent);
        }
    }

    @FXML
    private void actionButtonFind(ActionEvent event) {
        String lastName = lastNameTextField.getText();
        String firstName = firstNameTextField.getText();
        String middleName = middleNameTextField.getText();
        Education education = educationComboBox.getValue();
        String profession = professionTextField.getText();
        String wage = wageTextField.getText();
        WorkType workType = workTypeComboBox.getValue();

        Map<String, Object> params = new HashMap();
        if (lastName != null && lastName.length() > 0) params.put(ResumeRequestParam.LAST_NAME, lastName);
        if (firstName != null && firstName.length() > 0) params.put(ResumeRequestParam.FIRST_NAME, firstName);
        if (middleName != null && middleName.length() > 0) params.put(ResumeRequestParam.MIDDLE_NAME, middleName);
        if (education != null) params.put(ResumeRequestParam.EDUCATION_ID, education.getId());
        if (profession != null && profession.length() > 0) params.put(ResumeRequestParam.PROFESSION, profession);
        if (workType != null) params.put(ResumeRequestParam.WORK_TYPE_ID, workType.getId());
        try {
            if (wage != null) params.put(ResumeRequestParam.WAGE, new BigDecimal(wage.replaceAll(",", ".")));
        } catch (Exception e) {
            logger.error(e);
        }

        if (currentProfField != null) params.put(ResumeRequestParam.PROF_FIELD_ID, currentProfField.getId());

        this.resumeObservableList = this.createResumeObservableList(resumeService.findAll(params));
        table.setItems(resumeObservableList);
    }

    @FXML
    public void actionButtonClear(ActionEvent event) {
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        educationComboBox.setValue(null);
        workTypeComboBox.setValue(null);
        professionTextField.setText("");
        wageTextField.setText("");
    }

    @FXML
    private void actionButtonExportToExel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("resume.xls");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("XLS", "*.xls")
        );

        File file = fileChooser.showSaveDialog(MainApp.PARENT_STAGE);
        if (file != null) {
            String path = file.getAbsoluteFile().getAbsolutePath();
            Map props = new HashMap<>();
            props.put(ExelResumeListWriter.props.PATH, path);
            props.put(ExelResumeListWriter.props.LIST_RESUME, resumeObservableList);
            ExelResumeListWriter.getInstance().write(props);
        }
    }

    @FXML
    private void actionButtonDelete(ActionEvent event) throws IOException {
        Resume resume = table.getSelectionModel().getSelectedItem();

        if (resume != null) {
            resumeObservableList.remove(resume);
            resumeService.delete(resume.getId());
            table.refresh();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalMessage.fxml"));
            Parent parent = fxmlLoader.load();

            ModalMessageController dialogController = fxmlLoader.getController();
            dialogController.setTitle("Ошибка");
            dialogController.setMessage("Выберите объект");
            dialogController.setNameButton("Хорошо");
            dialogController.showAndWait(parent);
        }

    }

    public void reload(ActionEvent event) {
        selectCategory(event, null);
        actionButtonClear(event);
    }

}
