package ru.iworking.personnel.reserve.component.list.view.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.list.view.controller.ResumeCellController;
import ru.iworking.personnel.reserve.component.list.view.pane.ResumePane;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

@Component
@Lazy
@RequiredArgsConstructor
public class ResumeCellControllerFactory {

    private final ImageContainerService imageContainerService;
    private final ImageUtil imageUtil;

    public ResumeCellController create(ResumePane resumePane) {
        return new ResumeCellController(imageContainerService, imageUtil, resumePane);
    }

}
