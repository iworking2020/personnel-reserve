package ru.iworking.personnel.reserve.component.list.view.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.list.view.controller.ResumeCellController;
import ru.iworking.personnel.reserve.component.list.view.pane.ResumePane;
import ru.iworking.personnel.reserve.service.ImageContainerService;

@Component
@Lazy
@RequiredArgsConstructor
public class ResumeCellControllerFactory {

    private final ImageContainerService imageContainerService;

    public ResumeCellController create(ResumePane resumePane) {
        return new ResumeCellController(imageContainerService, resumePane);
    }

}
