package ru.iworking.personnel.reserve.controller;

import com.zaxxer.hikari.HikariDataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.iworking.personnel.reserve.ApplicationJavaFX;
import ru.iworking.personnel.reserve.component.Messager;
import ru.iworking.personnel.reserve.entity.ApplicationProperty;
import ru.iworking.personnel.reserve.service.ApplicationPropertiesService;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class MainMenuController implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainMenuController.class);
    private static final String SYSTEM_NAME_IS_RESIZE_WINDOWS = "isResizeWindows";

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    private final HikariDataSource hikariDataSource;

    @Autowired @Lazy private VacancyTabContentController vacancyTabContentController;

    private final ApplicationPropertiesService applicationPropertiesService;

    @FXML private Pane parent;

    @FXML private CheckMenuItem winSearchCheckItem;
    @FXML private CheckMenuItem winResizable;
    @FXML private TabPane mainTabPane;
    @Getter private List<Tab> listAddTabs = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainTabPane.getTabs().addAll(listAddTabs);
        Optional<ApplicationProperty> applicationPropertyOptional = applicationPropertiesService.findFirstByName(SYSTEM_NAME_IS_RESIZE_WINDOWS);
        Boolean isResizeWindowsProp = applicationPropertyOptional.isPresent() ? Boolean.valueOf(applicationPropertyOptional.get().getValue()) : false;
        isResizable(isResizeWindowsProp);
        winResizable.setOnAction(event -> isResizable(winResizable.isSelected()));
        parent.getChildren().add(Messager.getInstance());
    }

    public void isResizable(boolean isResizable) {
        Optional<ApplicationProperty> applicationPropertyOptional = applicationPropertiesService.findFirstByName(SYSTEM_NAME_IS_RESIZE_WINDOWS);
        if (applicationPropertyOptional.isPresent()) {
            ApplicationProperty applicationProperty = applicationPropertyOptional.get();
            applicationProperty.setValue(String.valueOf(isResizable));
            applicationPropertiesService.update(applicationProperty);
        }
        winResizable.setSelected(isResizable);
        vacancyTabContentController.isResizable(isResizable);
    }

    /**
     * DirectoryChooser directoryChooser = new DirectoryChooser();
     *         directoryChooser.setTitle("Выберите директорию для импорта");
     *         File dirSave = directoryChooser.showDialog(ApplicationJavaFX.PARENT_STAGE);
     *         if (dirSave != null) {
     *             String pathResumeJson = String.format("%s%s", dirSave.getAbsolutePath(), "/resume.json");
     *             if (new File(pathResumeJson).exists()) {
     *                 JobParameters jobParameters = new JobParametersBuilder()
     *                         .addString("JobID", String.valueOf(System.currentTimeMillis()))
     *                         .addString("inputFileUrl", pathResumeJson)
     *                         .toJobParameters();
     *                 jobLauncher.run(importResumeJob, jobParameters);
     *             } else {
     *                 logger.debug("resume.json not found");
     *             }
     *             String pathCompanyJson = String.format("%s%s", dirSave.getAbsolutePath(), "/company.json");
     *             if (new File(pathCompanyJson).exists()) {
     *                 JobParameters jobParameters = new JobParametersBuilder()
     *                         .addString("JobID", String.valueOf(System.currentTimeMillis()))
     *                         .addString("inputFileUrl", pathCompanyJson)
     *                         .toJobParameters();
     *                 jobLauncher.run(importCompanyJob, jobParameters);
     *             } else {
     *                 logger.debug("company.json not found");
     *             }
     *         } else {
     *             logger.info("user not select export path");
     *         }
     */
    @FXML
    private void actionImportData(ActionEvent event) {
        File currentDatabase = getCurrentDataBase();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(currentDatabase.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File newDatabase = fileChooser.showOpenDialog(ApplicationJavaFX.PARENT_STAGE);
        if (newDatabase != null) {
            hikariDataSource.close();
            try {
                FileUtils.copyFile(newDatabase, currentDatabase);
            } catch (IOException e) {
                logger.error(e);
            }
            Messager.getInstance()
                    .sendMessageWithAction("Данные были загружены. Необходим перезапуск...", () -> ApplicationJavaFX.PARENT_STAGE.close());
        }

    }

    /*final DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYYMMddhhmmss");

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

            JobParameters jobParametersCompany = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .addString("outputFileUrl", String.format("%s%s", path, "/company.json"))
                    .toJobParameters();
            jobLauncher.run(exportCompanyJob, jobParametersCompany);
        } else {
            logger.info("user not select export path");
        }*/
    @FXML
    private void actionExportData(ActionEvent event) {

        File currentDatabase = getCurrentDataBase();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(currentDatabase.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File copiedDatabase = fileChooser.showSaveDialog(ApplicationJavaFX.PARENT_STAGE);
        if (copiedDatabase != null) {
            if (currentDatabase == null) throw new NullPointerException("currentDatabase is null");
            hikariDataSource.close();
            try {
                FileUtils.copyFile(currentDatabase, copiedDatabase);
            } catch (IOException e) {
                logger.error(e);
            }
            Messager.getInstance()
                    .sendMessageWithAction("Данные были выгружены. Необходим перезапуск...", () -> ApplicationJavaFX.PARENT_STAGE.close());
        } else {
            logger.info("user not select database for copy");
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

    private HikariDataSource createDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = (HikariDataSource) properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

}
