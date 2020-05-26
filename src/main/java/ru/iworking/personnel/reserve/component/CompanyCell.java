package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.entity.Company;

public class CompanyCell extends ListCell<Company> {

    @Getter @Setter
    private Company company;

    @Override
    protected void updateItem(Company company, boolean empty) {
        this.company = company;
        super.updateItem(company, empty);
        if(empty || company == null) {
            setText(null);
            setGraphic(null);
        } else {
            CompanyPane companyPane = new CompanyPane();
            companyPane.setData(company);
            setText(null);
            setGraphic(companyPane);
        }
    }

}
