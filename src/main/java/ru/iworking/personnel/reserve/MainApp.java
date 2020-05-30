package ru.iworking.personnel.reserve;

import com.gluonhq.ignite.spring.SpringContext;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iworking.personnel.reserve.controller.MainMenuController;
import ru.iworking.personnel.reserve.utils.AppUtil;
import ru.iworking.personnel.reserve.utils.db.HibernateUtil;

import java.io.IOException;
import java.util.Arrays;

public class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(MainMenuController.class);

    public static Stage PARENT_STAGE;

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

    private SpringContext context = new SpringContext(this, () -> Arrays.asList(MainApp.class.getPackage().getName()));

    @Autowired private FXMLLoader fxmlLoader;

    private void initLoadingPane(Stage stage) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/fxml/LoadingPane.fxml"));
        } catch (IOException e) {
            logger.error(e);
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
            HibernateUtil.getSessionFactory();
            fadeOut.play();
        });

        fadeOut.setOnFinished((e) -> {
            stage.hide();
            initMainStage();
        });
    }

    private void initMainStage() {
        Stage stage = new Stage();
        context.init();

        MainApp.PARENT_STAGE = stage;

        fxmlLoader.setLocation(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            logger.error(e);
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

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResource("/fonts/CenturyGothic.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/fonts/CenturyGothicBold.ttf").toExternalForm(), 14);
        initLoadingPane(stage);
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
        scene.getStylesheets().add("/styles/messager.css");
    }

}
