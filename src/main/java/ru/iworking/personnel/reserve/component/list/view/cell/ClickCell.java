package ru.iworking.personnel.reserve.component.list.view.cell;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.controller.ClickCellController;
import ru.iworking.personnel.reserve.component.list.view.factory.ClickCellControllerFactory;
import ru.iworking.personnel.reserve.component.list.view.pane.ClickPane;
import ru.iworking.personnel.reserve.entity.Click;

@RequiredArgsConstructor
public class ClickCell extends ListCell<Click> {

    private final ClickCellControllerFactory clickCellControllerFactory;

    @Getter private ClickPane clickPane;
    @Getter private ClickCellController clickCellController;

    @Override
    public void updateItem(Click click, boolean empty) {
        super.updateItem(click, empty);
        if(empty || click == null) {
            this.clickPane = null;
            this.clickCellController = null;
            setText(null);
            setGraphic(null);
        } else {
            clickPane = new ClickPane();
            clickCellController = clickCellControllerFactory.create(clickPane);
            clickCellController.init();
            clickCellController.setData(click);
            setText(null);
            setGraphic(clickPane);
        }
    }

}
