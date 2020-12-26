package ru.iworking.personnel.reserve.provider;

import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationParent;
import ru.iworking.personnel.reserve.PrimaryStageProvider;

@Component
public class PrimaryStageProviderImpl implements PrimaryStageProvider {
    @Override
    public Stage getPrimaryStage() {
        return ApplicationParent.PARENT_STAGE;
    }
}
