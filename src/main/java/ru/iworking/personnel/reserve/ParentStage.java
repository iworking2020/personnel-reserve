package ru.iworking.personnel.reserve;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import ru.iworking.personnel.reserve.utils.AppUtil;

import java.io.IOException;

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

        stage.setTitle("Personnel reserve");
        AppUtil.setIcon(stage);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
