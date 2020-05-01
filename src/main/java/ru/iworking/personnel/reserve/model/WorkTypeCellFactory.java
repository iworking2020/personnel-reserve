package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.WorkType;

public class WorkTypeCellFactory implements Callback<ListView<WorkType>, ListCell<WorkType>> {

    private static final Logger logger = LogManager.getLogger(WorkTypeCellFactory.class);

    @Override
    public ListCell<WorkType> call(ListView<WorkType> param) {
        return new ListCell<WorkType>() {
            @Override
            protected void updateItem(WorkType item, boolean empty) {
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
