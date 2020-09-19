package ru.iworking.personnel.reserve.utils;

import javafx.application.Platform;
import javafx.stage.Stage;
import ru.iworking.personnel.reserve.ApplicationJavaFX;

public class ApplicationUtils {

    public static void reload() {
        ApplicationJavaFX.PARENT_STAGE.close();
        Platform.runLater( () -> {
            try {
                new ApplicationJavaFX().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
