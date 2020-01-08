package ru.iworking.personnel.reserve.utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppUtil {

    private static final Logger logger = LogManager.getLogger(AppUtil.class);

    public static void setIcon(Stage stage) {
        try {
            stage.getIcons().add(new Image(AppUtil.class.getClassLoader().getResourceAsStream("images/icon.png")));
        } catch (Exception ex) {
            logger.error("Не удалось загрузить иконку приложения ...", ex);
        }
    }

}
