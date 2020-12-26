package ru.iworking.personnel.reserve;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public interface ParentStage {

    Logger logger = LogManager.getLogger(ApplicationParent.class);

    default void initParentStage(ConfigurableApplicationContext springContext) {
        Stage stage = new Stage();

        ApplicationJavaFX.PARENT_STAGE = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        loader.setControllerFactory(springContext::getBean);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        scene.setRoot(parent);
        addStylesheets(scene);

        stage.setTitle("Personnel reserve");
        AppUtil.setIcon(stage);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    default void addStylesheets(Scene scene) {
        final String FOLDER_STYLES = "styles";
        try {
            File folder = FileUtil.getResourceFolder(FOLDER_STYLES);
            File[] listFiles = folder.listFiles((dir, name) -> name.endsWith(".css"));
            Arrays.asList(listFiles).parallelStream()
                    .forEachOrdered(file -> {
                        String path = String.format("/%s/%s", FOLDER_STYLES, file.getName());
                        scene.getStylesheets().add(path);
                    });
        } catch (NotFoundException | URISyntaxException e ) {
            logger.error(e);
        }
    }

}
