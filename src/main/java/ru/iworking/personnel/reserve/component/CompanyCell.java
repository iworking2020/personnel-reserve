package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.LogoService;

public class CompanyCell extends ListCell<Company> {

    private final CompanyTypeService companyTypeService;
    private final LogoService logoService;

    @Getter @Setter
    private Company company;

    public CompanyCell(CompanyTypeService companyTypeService, LogoService logoService) {
        this.companyTypeService = companyTypeService;
        this.logoService = logoService;
    }

    @Override
    protected void updateItem(Company company, boolean empty) {
        this.company = company;
        super.updateItem(company, empty);
        if(empty || company == null) {
            setText(null);
            setGraphic(null);
        } else {
            CompanyPane companyPane = new CompanyPane(companyTypeService, logoService);
            companyPane.setData(company);
            setText(null);
            setGraphic(companyPane);
        }
    }

}
