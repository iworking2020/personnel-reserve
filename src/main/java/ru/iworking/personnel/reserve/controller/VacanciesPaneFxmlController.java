package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.entity.Company;

import java.net.URL;
import java.util.ResourceBundle;

public class VacanciesPaneFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacanciesPaneFxmlController.class);

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();
    private CompanyDao companyDao = CompanyDao.getInstance();

    @FXML private CompaniesTableController companiesTableController;
    @FXML private VacanciesTableController vacanciesTableController;
    @FXML private CompanyViewController companyViewController;
    @FXML private CompanyEditController companyEditController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companiesTableController.getTableCompanies().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            companiesTableController.enableTargetItemButtons();
            companyViewController.setData(newSelection);
            companyViewController.view();
            vacanciesTableController.enableNoTargetButtons();
        });

        companiesTableController.getCreateCompanyButton().setOnAction(event -> actionButtonCreateCompany(event));
        companiesTableController.getUpdateCompaniesTableButton().setOnAction(event -> actionButtonUpdateCompaniesTable(event));
        companiesTableController.getEditCompanyButton().setOnAction(event -> actionButtonEditCompany(event));
        companiesTableController.getDeleteCompanyButton().setOnAction(event -> actionButtonDeleteCompany(event));

        companyEditController.getSaveCompanyButton().setOnAction(event -> actionButtonSaveCompany(event));
    }

    public void actionButtonCreateCompany(ActionEvent event) {
        companyEditController.clear();
        companyEditController.view();
    }

    public void actionButtonUpdateCompaniesTable(ActionEvent event) {
        companiesTableController.clear();
        vacanciesTableController.disableNoTargetItemButtons();
        vacanciesTableController.clear();
        companiesTableController.getTableCompanies().setItems(FXCollections.observableList(companyDao.findAll()));
        companyEditController.hide();
        companyViewController.hide();
        logger.debug("Companies table has been updated...");
    }

    public void actionButtonEditCompany(ActionEvent event) {
        Company company = companiesTableController.getTableCompanies().getSelectionModel().getSelectedItem();
        if (company != null) {
            companyEditController.setData(company);
            companyEditController.view();
        } else actionButtonSaveCompany(event);
    }

    public void actionButtonDeleteCompany(ActionEvent event) {
        Company company = companiesTableController.getTableCompanies().getSelectionModel().getSelectedItem();
        if (company != null) companyDao.delete(company);
        actionButtonUpdateCompaniesTable(event);
    }

    public void actionButtonSaveCompany(ActionEvent event) {
        Boolean isSaved = companyEditController.save();
        if (isSaved) {
            companyEditController.hide();
            companyEditController.clear();
            actionButtonUpdateCompaniesTable(event);
        }
    }

    public void actionButtonCreateVacancy(ActionEvent event) {
        //clearVacancyEditBlock();
        //vacancyEditBlock.setVisible(true);
    }

    public void reloadCompanyTable(ActionEvent event) {
        actionButtonUpdateCompaniesTable(event);
    }

    public void reload(ActionEvent event) {
        companyTypeDao.clearCash();
        reloadCompanyTable(event);
        companyEditController.reload(event);
    }

}
