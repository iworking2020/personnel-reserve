package ru.iworking.personnel.reserve;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.controller.MainMenuController;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

public class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(MainMenuController.class);

    public static Stage PARENT_STAGE;

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResource("/fonts/CenturyGothic.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/fonts/CenturyGothicBold.ttf").toExternalForm(), 14);

        MainApp.PARENT_STAGE = stage;

        Parent parent = FXMLUtil.load("/fxml/MainMenu.fxml");
        Scene scene = new Scene(parent);
        addStylesheets(scene);

        stage.setTitle("Personnel reserve");
        AppUtil.setIcon(stage);
        stage.setScene(scene);
        stage.show();

    }

    public static void reload() {
        MainApp.PARENT_STAGE.close();
        Platform.runLater( () -> {
            try {
                new MainApp().start(new Stage());
            } catch (Exception ex) {
                logger.error(ex);
            }
        });
    }

    public void addStylesheets(Scene scene) {
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
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
