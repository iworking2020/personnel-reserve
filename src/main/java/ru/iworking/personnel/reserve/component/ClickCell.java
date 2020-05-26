package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.service.ClickService;
import ru.iworking.personnel.reserve.service.PhotoService;
import ru.iworking.personnel.reserve.service.ResumeStateService;

public class ClickCell extends ListCell<Click> {

    private final PhotoService photoService;
    private final ResumeStateService resumeStateService;
    private final ClickService clickService;

    public ClickCell(PhotoService photoService, ResumeStateService resumeStateService, ClickService clickService) {
        this.photoService = photoService;
        this.resumeStateService = resumeStateService;
        this.clickService = clickService;
    }

    @Override
    public void updateItem(Click click, boolean empty) {
        super.updateItem(click, empty);
        if(empty || click == null) {
            setText(null);
            setGraphic(null);
        } else {
            ClickPane clickPane = new ClickPane(photoService, resumeStateService, clickService);
            clickPane.setData(click);
            setText(null);
            setGraphic(clickPane);
        }
    }

}
