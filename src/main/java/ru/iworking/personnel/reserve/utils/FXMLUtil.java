package ru.iworking.personnel.reserve.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ru.iworking.personnel.reserve.config.GuiceConfig;

import java.io.IOException;

public class FXMLUtil {

    public static Parent load(String pathFxml, Object controller, Object root) {
        FXMLLoader loader = new FXMLLoader(FXMLUtil.class.getResource(pathFxml));
        loader.setControllerFactory(instantiatedClass -> GuiceConfig.INJECTOR.getInstance(instantiatedClass));
        if (controller != null) loader.setController(controller);
        if (root != null) loader.setRoot(root);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Parent load(String pathFxml, Object controller) {
        return FXMLUtil.load(pathFxml, controller, null);
    }

    public static Parent load(String pathFxml) {
        return FXMLUtil.load(pathFxml, null, null);
    }

}
