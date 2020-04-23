package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class CompaniesTableController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(CompaniesTableController.class);

    @FXML private Button createCompanyButton;
    public Button getCreateCompanyButton() {
        return createCompanyButton;
    }

    @FXML private Button updateCompaniesTableButton;
    public Button getUpdateCompaniesTableButton() {
        return updateCompaniesTableButton;
    }

    @FXML private Button editCompanyButton;
    public Button getEditCompanyButton() {
        return editCompanyButton;
    }

    @FXML private Button deleteCompanyButton;
    public Button getDeleteCompanyButton() {
        return deleteCompanyButton;
    }

    @FXML private TableView<Company> tableCompanies;
    public TableView<Company> getTableCompanies() {
        return tableCompanies;
    }

    @FXML private TableColumn<Company, String> companyTypeColumn;
    @FXML private TableColumn<Company, String> companyNameColumn;

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();
    private CompanyDao companyDao = CompanyDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyTypeColumn.setCellValueFactory(cellData -> {
            CompanyType companyType = companyTypeDao.findFromCash(cellData.getValue().getCompanyTypeId());
            String textColumn = companyType != null ? companyType.getAbbreviatedNameToView(LocaleUtil.getDefault()) : "не указан";
            return new ReadOnlyStringWrapper(textColumn);
        });
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));

    }

    public void enableTargetItemButtons() {
        editCompanyButton.setDisable(false);
        deleteCompanyButton.setDisable(false);
    }

    public void disableTargetItemButtons() {
        editCompanyButton.setDisable(true);
        deleteCompanyButton.setDisable(true);
    }

    public void clear() {
        if (tableCompanies.getSelectionModel() != null) tableCompanies.getSelectionModel().clearSelection();
        editCompanyButton.setDisable(true);
        deleteCompanyButton.setDisable(true);
    }
}
