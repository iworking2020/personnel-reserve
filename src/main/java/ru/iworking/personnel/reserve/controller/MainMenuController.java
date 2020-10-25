package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationJavaFX;
import ru.iworking.personnel.reserve.component.Messager;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class MainMenuController implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainMenuController.class);

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired @Lazy private VacancyTabContentController vacancyTabContentController;

    private final JobLauncher jobLauncher;

    private final Job exportResumeJob;
    private final Job importResumeJob;

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
    private void actionImportData(ActionEvent event) throws
            JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию для импорта");
        File dirSave = directoryChooser.showDialog(ApplicationJavaFX.PARENT_STAGE);
        if (dirSave != null) {
            String pathResumeJson = String.format("%s%s", dirSave.getAbsolutePath(), "/resume.json");
            if (new File(pathResumeJson).exists()) {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addString("JobID", String.valueOf(System.currentTimeMillis()))
                        .addString("inputFileUrl", pathResumeJson)
                        .toJobParameters();
                jobLauncher.run(importResumeJob, jobParameters);
            } else {
                logger.debug("resume.json not found");
            }
        } else {
            logger.info("user not select export path");
        }
    }

    @FXML
    private void actionExportData(ActionEvent event) throws
            JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException
    {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYYMMddhhmmss");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию для сохранения");
        File dirSave = directoryChooser.showDialog(ApplicationJavaFX.PARENT_STAGE);
        if (dirSave != null) {
            String path = String.format("%s%s%s", dirSave.getAbsolutePath(), "/personnel-reserve-", LocalDateTime.now().toString(formatter));

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .addString("outputFileUrl", String.format("%s%s", path, "/resume.json"))
                    .toJobParameters();
            jobLauncher.run(exportResumeJob, jobParameters);
        } else {
            logger.info("user not select export path");
        }

    }

    private File getCurrentDataBase() {
        String urlForDatabase = datasourceUrl;
        String pathToDatabaseFile = urlForDatabase.substring(urlForDatabase.lastIndexOf(":")+1);
        String pathToDatabaseDir = pathToDatabaseFile.substring(0, pathToDatabaseFile.lastIndexOf("/"));
        String fileNameDataBase = pathToDatabaseFile.substring(pathToDatabaseFile.lastIndexOf("/")+1);

        File dir = new File(pathToDatabaseDir);
        FileFilter fileFilter = new WildcardFileFilter(fileNameDataBase+"*");
        File[] files = dir.listFiles(fileFilter);

        return files[0];
    }

}
