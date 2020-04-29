package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.utils.LocaleUtil;

public class CurrencyCellFactory implements Callback<ListView<Currency>, ListCell<Currency>> {

    private static final Logger logger = LogManager.getLogger(CurrencyCellFactory.class);

    @Override
    public ListCell<Currency> call(ListView<Currency> param) {
        return new ListCell<Currency>() {
            @Override
            protected void updateItem(Currency item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    try {
                        setText(item.getNameView().getName(LocaleUtil.getDefault()));
                    } catch (Exception ex) {
                        logger.error(ex);
                        setText("");
                    }
                }
            }
        };
    }

}
