package ru.iworking.personnel.reserve.component.list.view.cell;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.controller.CompanyCellController;
import ru.iworking.personnel.reserve.component.list.view.factory.CompanyCellControllerFactory;
import ru.iworking.personnel.reserve.component.list.view.pane.CompanyPane;
import ru.iworking.personnel.reserve.entity.Company;

@RequiredArgsConstructor
public class CompanyCell extends ListCell<Company> {

    private final CompanyCellControllerFactory companyCellControllerFactory;

    @Getter private CompanyPane companyPane;
    @Getter private CompanyCellController companyCellController;

    @Override
    protected void updateItem(Company company, boolean empty) {
        super.updateItem(company, empty);
        if(empty || company == null) {
            this.companyPane = null;
            this.companyCellController = null;
            setText(null);
            setGraphic(null);
        } else {
            companyPane = new CompanyPane();
            companyCellController = companyCellControllerFactory.create(companyPane);
            companyCellController.setData(company);
            setText(null);
            setGraphic(companyPane);
        }
    }

}
