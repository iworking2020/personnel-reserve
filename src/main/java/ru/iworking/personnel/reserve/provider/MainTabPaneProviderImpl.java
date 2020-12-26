package ru.iworking.personnel.reserve.provider;

import javafx.scene.control.Tab;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.MainTabPaneProvider;
import ru.iworking.personnel.reserve.controller.MainMenuController;

@Component
@RequiredArgsConstructor
public class MainTabPaneProviderImpl implements MainTabPaneProvider {

    private static final Logger logger = LogManager.getLogger(MainTabPaneProviderImpl.class);

    private final MainMenuController mainMenuController;

    @Override
    public void addTab(Tab tab) {
        mainMenuController.getListAddTabs().add(tab);
    }

}
