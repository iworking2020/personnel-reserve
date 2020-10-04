package ru.iworking.personnel.reserve.component.list.view.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.pane.CompanyPane;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@RequiredArgsConstructor
public class CompanyCellController {

    private final CompanyTypeService companyTypeService;
    private final ImageContainerService imageContainerService;

    private final ImageUtil imageUtil;

    private final CompanyPane companyPane;
    @Getter private Company company;

    public void setData(Company company) {
        this.company = company;
        CompanyType companyType = companyTypeService.findById(company.getCompanyTypeId());

        String viewName = companyType.getNameView().getName();

        if (viewName.length() < 15) {
            companyPane.getCompanyTypeLabel().setText(viewName);
        } else {
            String abbreviatedName = companyType.getAbbreviatedNameView().getName();
            companyPane.getCompanyTypeLabel().setText(abbreviatedName);
        }

        companyPane.getCompanyNameLabel().setText("\""+ company.getName() +"\"");

        if (company.getLogo() != null) {
            setLogoImage(company.getLogo());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImage(ImageContainer logo) {
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        companyPane.getCompanyImageView().setImage(img);
    }

    public void setDefaultImage() {
        byte[] imageBytes = imageUtil.getDefaultCompanyImage();
        if (Objects.nonNull(imageBytes) && imageBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                    inputStream,
                    150,
                    150,
                    false,
                    false);
            companyPane.getCompanyImageView().setImage(defaultImage);
        }
    }

}
