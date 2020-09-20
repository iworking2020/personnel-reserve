package ru.iworking.personnel.reserve;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationJavaFX extends Application implements ParentStage {

    public static void main(final String[] args) { ApplicationJavaFX.launch(args); }

    public static Stage PARENT_STAGE;
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        SpringApplication application = new SpringApplication(ApplicationJavaFX.class);
        application.setBannerMode(Banner.Mode.OFF);
        springContext = application.run();
    }

    @Override
    public void start(Stage stage) {
        PARENT_STAGE = stage;
        Font.loadFont(getClass().getResource("/fonts/CenturyGothic.ttf").toExternalForm(), 14);
        Font.loadFont(getClass().getResource("/fonts/CenturyGothicBold.ttf").toExternalForm(), 14);
        initParentStage(springContext);
    }

    @Override
    public void stop() { springContext.close(); }

}
