package ru.iworking.personnel.reserve;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import ru.iworking.personnel.reserve.utils.AppUtil;

import java.io.IOException;

public interface ParentStage {

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
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/window.css");
        scene.getStylesheets().add("/styles/button.css");
        scene.getStylesheets().add("/styles/combo-box.css");
        scene.getStylesheets().add("/styles/date-picker.css");
        scene.getStylesheets().add("/styles/text-area.css");
        scene.getStylesheets().add("/styles/text-field.css");
        scene.getStylesheets().add("/styles/scroll-bar.css");
        scene.getStylesheets().add("/styles/scroll-pane.css");
        scene.getStylesheets().add("/styles/tab-pane.css");
        scene.getStylesheets().add("/styles/prof-field.css");
        scene.getStylesheets().add("/styles/table-view.css");
        scene.getStylesheets().add("/styles/context-menu.css");
        scene.getStylesheets().add("/styles/menu-bar.css");
        scene.getStylesheets().add("/styles/vacancies-pane.css");
        scene.getStylesheets().add("/styles/tree-view.css");
        scene.getStylesheets().add("/styles/split-pane.css");
        scene.getStylesheets().add("/styles/accordion.css");
        scene.getStylesheets().add("/styles/list-view.css");
        scene.getStylesheets().add("/styles/messager.css");
    }

}
