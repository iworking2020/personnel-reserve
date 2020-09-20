package ru.iworking.personnel.reserve.component.list.view.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.list.view.controller.ClickCellController;
import ru.iworking.personnel.reserve.component.list.view.pane.ClickPane;
import ru.iworking.personnel.reserve.controller.ClickListViewController;
import ru.iworking.personnel.reserve.service.ClickService;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.service.ResumeStateService;

@Component
@Lazy
@RequiredArgsConstructor
public class ClickCellControllerFactory {

    private final ImageContainerService imageContainerService;
    private final ResumeStateService resumeStateService;
    private final ClickService clickService;
    private final ClickListViewController clickListViewController;

    public ClickCellController create(ClickPane clickPane) {
        return new ClickCellController(imageContainerService, resumeStateService, clickService, clickListViewController, clickPane);
    }

}
