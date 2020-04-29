package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.dao.VacancyDao;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class CompaniesTableController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(CompaniesTableController.class);

    @FXML private Button createCompanyButton;
    @FXML private Button updateCompaniesTableButton;
    @FXML private Button editCompanyButton;
    @FXML private Button deleteCompanyButton;

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
            String textColumn = companyType != null ? companyType.getAbbreviatedNameView().getName(LocaleUtil.getDefault()) : "не указан";
            return new ReadOnlyStringWrapper(textColumn);
        });
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));

        tableCompanies.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            enableTargetItemButtons();
            getCompanyViewController().setData(newSelection);
            getCompanyViewController().show();
            getVacanciesTableController().enableNoTargetButtons();
            if (newSelection != null) getVacanciesTableController().actionUpdate(null);
        });

    }

    @FXML
    public void actionCreate(ActionEvent event) {
        getCompanyEditController().clear();
        getCompanyEditController().show();
    }

    @FXML
    public void actionEdit(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
        if (company != null) {
            getCompanyEditController().setData(company);
            getCompanyEditController().show();
        } else getCompanyEditController().actionSave(event);
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        clear();
        getVacanciesTableController().disableNoTargetItemButtons();
        getVacanciesTableController().clear();
        getVacanciesTableController().actionUpdate(event);
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));
        getCompanyEditController().hide();
        getCompanyViewController().hide();
        logger.debug("Companies table has been updated...");
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
        if (company != null) {
            Long companyId = company.getId();
            companyDao.delete(company);
            VacancyDao.getInstance().deleteByCompanyId(companyId);
        }
        actionUpdate(event);
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

    public CompanyEditController getCompanyEditController() {
        return (CompanyEditController) getControllerProvider().get(CompanyEditController.class.getName());
    }

    public CompanyViewController getCompanyViewController() {
        return (CompanyViewController) getControllerProvider().get(CompanyViewController.class.getName());
    }

    public VacanciesTableController getVacanciesTableController() {
        return (VacanciesTableController) getControllerProvider().get(VacanciesTableController.class.getName());
    }
}
