package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.service.api.utils.LocaleUtil;

public class ProfFieldCellFactory implements Callback<ListView<ProfField>, ListCell<ProfField>> {

    private static final Logger logger = LogManager.getLogger(ProfFieldCellFactory.class);

    @Override
    public ListCell<ProfField> call(ListView<ProfField> param) {
        return new ListCell<ProfField>() {
            @Override
            protected void updateItem(ProfField item, boolean empty) {
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
