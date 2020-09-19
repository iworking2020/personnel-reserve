package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationJavaFX;
import ru.iworking.personnel.reserve.component.Messager;
import ru.iworking.personnel.reserve.utils.ApplicationUtils;
import ru.iworking.personnel.reserve.utils.db.HibernateUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class MainMenuController implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainMenuController.class);

    @Autowired @Lazy private VacancyTabContentController vacancyTabContentController;

    @FXML private Pane parent;

    @FXML private CheckMenuItem winSearchCheckItem;
    @FXML private CheckMenuItem winResizable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isResizable(false);
        winResizable.setOnAction(event -> isResizable(winResizable.isSelected()));
        parent.getChildren().add(Messager.getInstance());
    }

    public void isResizable(boolean isResizable) {
        winResizable.setSelected(isResizable);
        vacancyTabContentController.isResizable(isResizable);
    }

    @FXML
    private void actionLoadData(ActionEvent event) {
        File currentDatabase = getCurrentDataBase();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(currentDatabase.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File newDatabase = fileChooser.showOpenDialog(ApplicationJavaFX.PARENT_STAGE);
        if (newDatabase != null) {
            HibernateUtil.shutDown();
            try {
                FileUtils.copyFile(newDatabase, currentDatabase);
            } catch (IOException e) {
                logger.error(e);
            }
            /*resumesPaneController.reload(event);
            vacanciesPaneController.reload(event);*/
            ApplicationUtils.reload();
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

        File copiedDatabase = fileChooser.showSaveDialog(ApplicationJavaFX.PARENT_STAGE);
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
