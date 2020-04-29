package ru.iworking.personnel.reserve.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.utils.LocaleUtil;

public class CompanyTypeCellFactory implements Callback<ListView<CompanyType>, ListCell<CompanyType>> {

    private static final Logger logger = LogManager.getLogger(CompanyTypeCellFactory.class);

    @Override
    public ListCell<CompanyType> call(ListView<CompanyType> param) {
        return new ListCell<CompanyType>() {
            @Override
            protected void updateItem(CompanyType item, boolean empty) {
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
