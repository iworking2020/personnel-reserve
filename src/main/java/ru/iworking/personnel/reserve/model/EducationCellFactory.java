package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.service.api.utils.LocaleUtils;

public class EducationCellFactory implements Callback<ListView<Education>, ListCell<Education>> {

    @Override
    public ListCell<Education> call(ListView<Education> param) {
        return new ListCell<Education>() {
            @Override
            protected void updateItem(Education item, boolean empty) {
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
