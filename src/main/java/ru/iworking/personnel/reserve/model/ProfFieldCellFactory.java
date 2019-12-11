package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.service.api.utils.LocaleUtils;

public class ProfFieldCellFactory implements Callback<ListView<ProfField>, ListCell<ProfField>> {

    @Override
    public ListCell<ProfField> call(ListView<ProfField> param) {
        return new ListCell<ProfField>() {
            @Override
            protected void updateItem(ProfField item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getNameToView(LocaleUtils.getDefault()));
                }
            }
        };
    }

}
