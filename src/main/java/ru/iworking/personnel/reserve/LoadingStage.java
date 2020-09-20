package ru.iworking.personnel.reserve;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.springframework.context.ConfigurableApplicationContext;
import ru.iworking.personnel.reserve.utils.AppUtil;

import java.io.IOException;

@Deprecated
public interface LoadingStage extends ParentStage {

    default void initLoadingPane(ConfigurableApplicationContext springContext, Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoadingPane.fxml"));
        loader.setControllerFactory(springContext::getBean);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        scene.getStylesheets().add("/styles/loading-pane.css");
        stage.setScene(scene);
        AppUtil.setIcon(stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), parent);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(800), parent);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();

        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished((e) -> {
            stage.hide();
            initParentStage(springContext);
        });
    }



}
