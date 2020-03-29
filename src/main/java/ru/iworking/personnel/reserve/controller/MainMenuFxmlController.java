package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainMenuFxmlController.class);

    @FXML private ResumesPaneFxmlController resumesPaneController;
    @FXML private VacanciesPaneFxmlController vacanciesPaneController;

    @FXML private CheckMenuItem winSearchCheckItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        winSearchCheckItem.setSelected(false);
        winSearchCheckItem.setOnAction(event -> {
            if (winSearchCheckItem.isSelected()) {
                resumesPaneController.getSearchPane().setVisible(true);
                GridPane.setColumnSpan(resumesPaneController.getTablePane(), 3);
                GridPane.setColumnSpan(resumesPaneController.getButtonsPane(), 3);
            } else {
                resumesPaneController.getSearchPane().setVisible(false);
                GridPane.setColumnSpan(resumesPaneController.getTablePane(), 4);
                GridPane.setColumnSpan(resumesPaneController.getButtonsPane(), 4);
            }
        });
    }

    private void addStylesheets(Scene scene) {
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/window.css");
        scene.getStylesheets().add("/styles/button.css");
        scene.getStylesheets().add("/styles/combo.box.css");
        scene.getStylesheets().add("/styles/date.picker.css");
        scene.getStylesheets().add("/styles/text.area.css");
        scene.getStylesheets().add("/styles/text.field.css");
        scene.getStylesheets().add("/styles/scroll.bar.css");
        scene.getStylesheets().add("/styles/scroll.pane.css");
        scene.getStylesheets().add("/styles/tab.pane.css");
        scene.getStylesheets().add("/styles/prof.field.css");
        scene.getStylesheets().add("/styles/table.view.css");
        scene.getStylesheets().add("/styles/context.menu.css");
        scene.getStylesheets().add("/styles/menu.bar.css");
    }

    public void show(Parent root) {
        Stage stage = MainApp.PARENT_STAGE;
        stage.setTitle("Personnel reserve");
        AppUtil.setIcon(stage);
        Scene scene = new Scene(root);
        addStylesheets(scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void actionLoadData(ActionEvent event) {
        File currentDatabase = getCurrentDataBase();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(currentDatabase.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File newDatabase = fileChooser.showOpenDialog(MainApp.PARENT_STAGE);
        if (newDatabase != null) {
            HibernateUtil.shutDown();
            try {
                FileUtils.copyFile(newDatabase, currentDatabase);
            } catch (IOException e) {
                logger.error(e);
            }
            resumesPaneController.reload(event);
            vacanciesPaneController.reload(event);
        }


    }

    @FXML
    private void actionUploadData(ActionEvent event) {
        File currentDatabase = getCurrentDataBase();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(currentDatabase.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File copiedDatabase = fileChooser.showSaveDialog(MainApp.PARENT_STAGE);
        if (copiedDatabase != null) {
            if (currentDatabase == null) throw new NullPointerException("currentDatabase is null");
            HibernateUtil.shutDown();
            try {
                FileUtils.copyFile(currentDatabase, copiedDatabase);
            } catch (IOException e) {
                logger.error(e);
            }
        } else {
            logger.info("user not select database for copy");
        }

    }

    private File getCurrentDataBase() {
        String urlForDatabase = HibernateUtil.getProperties().getProperty("hibernate.connection.url");
        String pathToDatabaseFile = urlForDatabase.substring(urlForDatabase.lastIndexOf(":")+1);
        String pathToDatabaseDir = pathToDatabaseFile.substring(0, pathToDatabaseFile.lastIndexOf("/"));
        String fileNameDataBase = pathToDatabaseFile.substring(pathToDatabaseFile.lastIndexOf("/")+1);

        File dir = new File(pathToDatabaseDir);
        FileFilter fileFilter = new WildcardFileFilter(fileNameDataBase+"*");
        File[] files = dir.listFiles(fileFilter);

        return files[0];
    }

}
