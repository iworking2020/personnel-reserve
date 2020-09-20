package ru.iworking.personnel.reserve.component.list.view.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.list.view.controller.VacancyCellController;
import ru.iworking.personnel.reserve.component.list.view.pane.VacancyPane;
import ru.iworking.personnel.reserve.service.ImageContainerServiceImpl;
import ru.iworking.personnel.reserve.service.VacancyServiceImpl;

@Component
@Lazy
@RequiredArgsConstructor
public class VacancyCellControllerFactory {

    private final VacancyServiceImpl vacancyService;
    private final ImageContainerServiceImpl imageContainerService;

    public VacancyCellController create(VacancyPane vacancyPane) {
        return new VacancyCellController(vacancyService, imageContainerService, vacancyPane);
    }

}
