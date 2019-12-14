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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.service.api.utils.LocaleUtils;
import ru.iworking.service.api.utils.TimeUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuFxmlController implements Initializable {

    static final Logger logger = LogManager.getLogger(MainMenuFxmlController.class);

    private ResumeDao resumeDao = new ResumeDao();
    private ProfFieldDao profFieldDao = new ProfFieldDao();

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

    private ObservableList<Resume> resumeObservableList;
    private ProfField currentProfField = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            });
            profFieldVBox.getChildren().add(button);
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
        scene.getStylesheets().add("/styles/scroll.bar.css");
        scene.getStylesheets().add("/styles/scroll.pane.css");
        scene.getStylesheets().add("/styles/tab.pane.css");
        scene.getStylesheets().add("/styles/prof.field.css");
        scene.getStylesheets().add("/styles/table.view.css");

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
                        resumeDao.update(resumeObservableList.get(i));
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
        System.out.println("actionButtonFind!");
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
