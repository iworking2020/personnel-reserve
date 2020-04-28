package ru.iworking.personnel.reserve.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FXMLUtils {

    public static <T extends Parent> void loadFXML(T component, String pathFxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(component);
        loader.setControllerFactory(theClass -> component);

        try {
            loader.load(component.getClass().getResourceAsStream(pathFxml));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
