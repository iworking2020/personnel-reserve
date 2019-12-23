package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.EducationCellFactory;
import ru.iworking.personnel.reserve.model.WorkTypeCellFactory;
import ru.iworking.personnel.reserve.props.ResumeRequestParam;
import ru.iworking.personnel.reserve.utils.ExelUtil;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.service.api.utils.LocaleUtils;
import ru.iworking.service.api.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainMenuFxmlController implements Initializable {

    static final Logger logger = LogManager.getLogger(MainMenuFxmlController.class);

    private ResumeDao resumeDao = new ResumeDao();
    private ProfFieldDao profFieldDao = new ProfFieldDao();
    private EducationDao educationDao = new EducationDao();
    private WorkTypeDao workTypeDao = new WorkTypeDao();

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
    
    @FXML private StackPane backgroundImagePane;

    @FXML private VBox searchPane;
    @FXML private StackPane tablePane;
    @FXML private GridPane buttonsPane;

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
        workTypeComboBox.setButtonCell(workTypeCellFactory.call(null));
        workTypeComboBox.setCellFactory(workTypeCellFactory);
        workTypeComboBox.setItems(FXCollections.observableList(workTypeDao.findAll()));

        educationComboBox.setButtonCell(educationCellFactory.call(null));
        educationComboBox.setCellFactory(educationCellFactory);
        educationComboBox.setItems(FXCollections.observableList(educationDao.findAll()));
        
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        professionColumn.setCellValueFactory(new PropertyValueFactory<>("profession"));
        workTypeColumn.setCellValueFactory(cellData -> {
            WorkType workType = cellData.getValue().getWorkType();
            String textColumn = workType != null ? workType.getNameToView(LocaleUtils.getDefault()) : "не указан";
            return new ReadOnlyStringWrapper(textColumn);
        });
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));
        currencyColumn.setCellValueFactory(cellData -> {
            Currency currency = cellData.getValue().getCurrency();
            String textColumn = currency != null ? currency.getNameToView(LocaleUtils.getDefault()) : "не указана";
            return new ReadOnlyStringWrapper(textColumn);
        });
        educationColumn.setCellValueFactory(cellData -> {
            Education education = cellData.getValue().getEducation();
            String textColumn = education != null ? education.getNameToView(LocaleUtils.getDefault()) : "не указано";
            return new ReadOnlyStringWrapper(textColumn);
        });
        experienceColumn.setCellValueFactory(cellData -> {
            Experience exp = cellData.getValue().getExperience();
            String textColumn = "";
            if (exp != null) {
                Integer age = TimeUtils.calAge(exp.getDateStart(), exp.getDateEnd());
                textColumn = age == null || age <= 0 ? "без опыта" : age + " " + TextUtil.nameForNumbers(age);
            } else {
                textColumn = "без опыта";
            }

            return new ReadOnlyStringWrapper(textColumn);
        });

        Button buttonFindAll = new Button();
        buttonFindAll.setText("все категории");
        buttonFindAll.setOnAction(event -> {
            this.currentProfField = null;
            this.resumeObservableList = this.createResumeObservableList(resumeDao.findAll());
            table.setItems(resumeObservableList);
            String style = "-fx-background-image: url('images/fone/fone0.jpg');";
            backgroundImagePane.setStyle(style);
            actionButtonClear(event);
        });
        profFieldVBox.getChildren().add(buttonFindAll);

        profFieldDao.findAll().stream().forEach(profField -> {
            Button button = new Button();
            button.setText(profField.getNameToView(LocaleUtils.getDefault()));
            button.setOnAction(event -> {
                this.currentProfField = profField;
                this.resumeObservableList = this.createResumeObservableList(resumeDao.findAllByProfField(profField));
                table.setItems(resumeObservableList);
                String style = "-fx-background-image: url('images/fone/fone"+profField.getId()+".png');";
                backgroundImagePane.setStyle(style);
                actionButtonClear(event);
            });
            profFieldVBox.getChildren().add(button);
        });

        CheckMenuItem checkMenuItem = new CheckMenuItem("Поиск");
        checkMenuItem.setSelected(false);
        checkMenuItem.setOnAction(event -> {
            if (checkMenuItem.isSelected()) {
                searchPane.setVisible(true);
                GridPane.setColumnSpan(tablePane, 3);
                GridPane.setColumnSpan(buttonsPane, 3);
            } else {
                searchPane.setVisible(false);
                GridPane.setColumnSpan(tablePane, 4);
                GridPane.setColumnSpan(buttonsPane, 4);
            }
        });

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(checkMenuItem);

        table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(table, event.getScreenX(), event.getScreenY());
            } else if (event.getButton() == MouseButton.PRIMARY) {
                if (contextMenu.isShowing()) contextMenu.hide();
            }
        });

        this.resumeObservableList = this.createResumeObservableList(resumeDao.findAll());
        table.setItems(resumeObservableList);
    }

    public void show(Parent root) {
        Stage stage = MainApp.PARENT_STAGE;

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/window.css");
        scene.getStylesheets().add("/styles/button.css");
        scene.getStylesheets().add("/styles/combo.box.css");
        scene.getStylesheets().add("/styles/text.field.css");
        scene.getStylesheets().add("/styles/scroll.bar.css");
        scene.getStylesheets().add("/styles/scroll.pane.css");
        scene.getStylesheets().add("/styles/tab.pane.css");
        scene.getStylesheets().add("/styles/prof.field.css");
        scene.getStylesheets().add("/styles/table.view.css");
        scene.getStylesheets().add("/styles/context.menu.css");

        stage.setTitle("Personnel reserve");
        try {
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        } catch (Exception ex) {
            logger.error("Не удалось загрузить иконку приложения ...", ex);
        }
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<Resume> createResumeObservableList(List<Resume> list) {
        ObservableList<Resume> resumeObservableList = FXCollections.observableList(list);
        resumeObservableList.addListener((ListChangeListener<Resume>) change -> {
            while (change.next()) {
                if (change.wasPermutated()) {
                    for (int i = change.getFrom(); i < change.getTo(); ++i) {
                        logger.debug("Permuted: " + i + " ");
                    }
                } else if (change.wasUpdated()) {
                    for (int i = change.getFrom(); i < change.getTo(); ++i) {
                        logger.debug("Updated: " + i + " ");
                    }
                } else {
                    for (Resume removedItem : change.getRemoved()) {
                        resumeDao.delete(removedItem);
                    }
                    for (Resume addedItem : change.getAddedSubList()) {
                        resumeDao.create(addedItem);
                    }
                }
            }
        });
        return resumeObservableList;
    }
  
    @FXML
    private void actionButtonAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalAddResume.fxml"));
        Parent parent = fxmlLoader.load();

        ModalAddResumeFxmlController dialogController = fxmlLoader.getController();
        dialogController.setResumeObservableList(resumeObservableList);
        dialogController.setCurrentProfField(currentProfField);
        dialogController.showAndWait(parent);

        table.refresh();
    }
    
    @FXML
    private void actionButtonEdit(ActionEvent event) throws IOException {
        Resume resume = table.getSelectionModel().getSelectedItem();

        if (resume != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalEditResume.fxml"));
            Parent parent = fxmlLoader.load();

            ModalEditResumeFxmlController dialogController = fxmlLoader.getController();
            dialogController.setResumeObservableList(resumeObservableList);
            dialogController.setResume(resume);
            dialogController.setCurrentProfField(currentProfField);
            dialogController.showAndWait(parent);

            table.refresh();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalMessage.fxml"));
            Parent parent = fxmlLoader.load();

            ModalMessageFxmlController dialogController = fxmlLoader.getController();
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

            ModalOpenResumeFxmlController dialogController = fxmlLoader.getController();
            dialogController.setResume(resume);
            dialogController.showAndWait(parent);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalMessage.fxml"));
            Parent parent = fxmlLoader.load();

            ModalMessageFxmlController dialogController = fxmlLoader.getController();
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
            if (wage != null) params.put(ResumeRequestParam.WAGE, BigDecimal.valueOf(Long.valueOf(wage)));
        } catch (Exception e) {
            logger.error(e);
        }
        
        if (currentProfField != null) params.put(ResumeRequestParam.PROF_FIELD_ID, currentProfField.getId());
        
        this.resumeObservableList = this.createResumeObservableList(resumeDao.findAll(params));
        table.setItems(resumeObservableList);
    }
    
    @FXML
    private void actionButtonClear(ActionEvent event) {
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        educationComboBox.setValue(null);
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
            try {
                ExelUtil.createXLSResumeList(path, resumeObservableList);
            } catch (IOException ex) {
                logger.error("не удалось выгрузить в exel", ex);
            }
        }
    }
    
    @FXML
    private void actionButtonDelete(ActionEvent event) throws IOException {
        Resume resume = table.getSelectionModel().getSelectedItem();

        if (resume != null) {
            resumeObservableList.remove(resume);
            table.refresh();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModalMessage.fxml"));
            Parent parent = fxmlLoader.load();

            ModalMessageFxmlController dialogController = fxmlLoader.getController();
            dialogController.setTitle("Ошибка");
            dialogController.setMessage("Выберите объект");
            dialogController.setNameButton("Хорошо");
            dialogController.showAndWait(parent);
        }

    }

}
