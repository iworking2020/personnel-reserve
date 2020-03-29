package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.service.api.utils.LocaleUtil;

public class EducationCellFactory implements Callback<ListView<Education>, ListCell<Education>> {

    private static final Logger logger = LogManager.getLogger(EducationCellFactory.class);

    @Override
    public ListCell<Education> call(ListView<Education> param) {
        return new ListCell<Education>() {
            @Override
            protected void updateItem(Education item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    try {
                        setText(item.getNameToView(LocaleUtil.getDefault()));
                    } catch (Exception ex) {
                        logger.error(ex);
                        setText("");
                    }

                }
            }
        };
    }

}
