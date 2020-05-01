package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.ResumeState;

public class ResumeStateCellFactory implements Callback<ListView<ResumeState>, ListCell<ResumeState>> {

    private static final Logger logger = LogManager.getLogger(ResumeStateCellFactory.class);

    @Override
    public ListCell<ResumeState> call(ListView<ResumeState> param) {
        return new ListCell<ResumeState>() {
            @Override
            protected void updateItem(ResumeState item, boolean empty) {
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
