package ru.iworking.personnel.reserve;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.iworking.personnel.reserve.utils.FileUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class ApplicationParent extends Application implements ParentStage {

    private static final Logger logger = LogManager.getLogger(ApplicationParent.class);

    private static final String FOLDER_FONTS = "fonts";

    public static Stage PARENT_STAGE;
    private ConfigurableApplicationContext springContext;

    private void initFonts() {
        try {
            File folder = FileUtil.getProjectFolder(FOLDER_FONTS);
            File[] listFiles = folder.listFiles((dir, name) -> name.endsWith(".ttf"));

            Stream.of(listFiles).parallel()
                    .map(File::toURI)
                    .filter(Objects::nonNull)
                    .map(uri -> {
                        try {
                            return uri.toURL();
                        } catch (MalformedURLException e) {
                            logger.error(e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .forEach(url -> Font.loadFont(url.toExternalForm(), 14));
        } catch (NotFoundException e) {
            logger.error(e);
        }

    }

    @Override
    public void init() {
        SpringApplication application = new SpringApplication(ApplicationJavaFX.class);
        application.setBannerMode(Banner.Mode.OFF);
        springContext = application.run();
    }

    @Override
    public void start(Stage stage) {
        PARENT_STAGE = stage;
        initFonts();
        initParentStage(springContext);
    }

    @Override
    public void stop() { if (springContext != null) springContext.close(); }

}
