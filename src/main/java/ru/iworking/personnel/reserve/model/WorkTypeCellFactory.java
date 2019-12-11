package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.service.api.utils.LocaleUtils;

public class WorkTypeCellFactory implements Callback<ListView<WorkType>, ListCell<WorkType>> {

    @Override
    public ListCell<WorkType> call(ListView<WorkType> param) {
        return new ListCell<WorkType>() {
            @Override
            protected void updateItem(WorkType item, boolean empty) {
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
