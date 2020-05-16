package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import ru.iworking.personnel.reserve.entity.Click;

public class ClickCell extends ListCell<Click> {

    @Override
    public void updateItem(Click click, boolean empty) {
        super.updateItem(click, empty);
        if(empty || click == null) {
            setText(null);
            setGraphic(null);
        } else {
            ClickItem clickItem = new ClickItem();
            clickItem.setData(click);
            setText(null);
            setGraphic(clickItem);
        }
    }

}
