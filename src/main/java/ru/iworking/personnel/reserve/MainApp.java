package ru.iworking.personnel.reserve;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ru.iworking.personnel.reserve.controller.MainMenuFxmlController;
import ru.iworking.personnel.reserve.utils.FileUtil;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

public class MainApp extends Application {

    public static Stage PARENT_STAGE;

    @Override
    public void start(Stage stage) throws Exception {
        MainApp.PARENT_STAGE = stage;

        FileUtil.createProjectDir();
        HibernateUtil.getSessionFactory();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent parent = fxmlLoader.load();

        MainMenuFxmlController dialogController = fxmlLoader.getController();
        dialogController.show(parent);

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
