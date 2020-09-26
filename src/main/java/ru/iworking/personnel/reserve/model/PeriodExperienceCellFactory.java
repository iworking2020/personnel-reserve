package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.PeriodExperience;

public class PeriodExperienceCellFactory implements Callback<ListView<PeriodExperience>, ListCell<PeriodExperience>> {

    private static final Logger logger = LogManager.getLogger(PeriodExperienceCellFactory.class);

    @Override
    public ListCell<PeriodExperience> call(ListView<PeriodExperience> param) {
        return new ListCell<PeriodExperience>() {
            @Override
            protected void updateItem(PeriodExperience item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    try {
                        setText(item.getNameView().getName());
                    } catch (Exception ex) {
                        logger.error(ex);
                        setText("");
                    }
                }
            }
        };
    }

}
