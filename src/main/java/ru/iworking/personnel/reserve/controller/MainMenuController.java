package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.utils.db.HibernateUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(MainMenuController.class);

    @FXML private VacancyTabContentController vacancyTabContentController;

    @FXML private CheckMenuItem winSearchCheckItem;
    @FXML private CheckMenuItem winResizable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isResizable(false);
        winResizable.setOnAction(event -> isResizable(winResizable.isSelected()));
    }

    public void isResizable(boolean isResizable) {
        winResizable.setSelected(isResizable);
        getVacanciesPaneController().isResizable(isResizable);
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
            /*resumesPaneController.reload(event);
            vacanciesPaneController.reload(event);*/
            MainApp.reload();
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

    public VacancyTabContentController getVacanciesPaneController() {
        return (VacancyTabContentController) getControllerProvider().get(VacancyTabContentController.class.getName());
    }

}
