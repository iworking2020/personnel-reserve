package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.service.api.utils.LocaleUtils;

public class CurrencyCellFactory implements Callback<ListView<Currency>, ListCell<Currency>> {

    @Override
    public ListCell<Currency> call(ListView<Currency> param) {
        return new ListCell<Currency>() {
            @Override
            protected void updateItem(Currency item, boolean empty) {
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
