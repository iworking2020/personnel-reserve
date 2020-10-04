package ru.iworking.personnel.reserve.component.list.view.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.list.view.controller.CompanyCellController;
import ru.iworking.personnel.reserve.component.list.view.pane.CompanyPane;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

@Component
@Lazy
@RequiredArgsConstructor
public class CompanyCellControllerFactory {

    private final CompanyTypeService companyTypeService;
    private final ImageContainerService imageContainerService;
    private final ImageUtil imageUtil;

    public CompanyCellController create(CompanyPane companyPane) {
        return new CompanyCellController(companyTypeService, imageContainerService, imageUtil, companyPane);
    }

}
