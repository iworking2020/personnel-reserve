package ru.iworking.personnel.reserve;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationJavaFX extends Application implements Loading {

    public static Stage PARENT_STAGE;

    private ConfigurableApplicationContext springContext;

    public static void main(final String[] args) {
        ApplicationJavaFX.launch(args);
    }

    @Override
    public void init() throws Exception {
        SpringApplication application = new SpringApplication(ApplicationJavaFX.class);
        application.setBannerMode(Banner.Mode.OFF);
        springContext = application.run();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void start(Stage stage) throws Exception {
        PARENT_STAGE = stage;
        Font.loadFont(getClass().getResource("/fonts/CenturyGothic.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/fonts/CenturyGothicBold.ttf").toExternalForm(), 14);
        initLoadingPane(springContext, stage);
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

}
