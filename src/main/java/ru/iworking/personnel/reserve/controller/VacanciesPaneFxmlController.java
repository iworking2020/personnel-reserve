package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.dao.VacancyDao;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.Vacancy;

import java.net.URL;
import java.util.ResourceBundle;

public class VacanciesPaneFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacanciesPaneFxmlController.class);

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();
    private CompanyDao companyDao = CompanyDao.getInstance();
    private VacancyDao vacancyDao = VacancyDao.getInstance();

    @FXML private CompaniesTableController companiesTableController;
    @FXML private VacanciesTableController vacanciesTableController;
    @FXML private CompanyViewController companyViewController;
    @FXML private CompanyEditController companyEditController;
    @FXML private VacancyViewController vacancyViewController;
    @FXML private VacancyEditController vacancyEditController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companiesTableController.getTableCompanies().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            companiesTableController.enableTargetItemButtons();
            companyViewController.setData(newSelection);
            companyViewController.view();
            vacanciesTableController.enableNoTargetButtons();
            if (newSelection != null) actionButtonUpdateVacanciesTable(null);
        });

        companiesTableController.getCreateCompanyButton().setOnAction(event -> actionButtonCreateCompany(event));
        companiesTableController.getUpdateCompaniesTableButton().setOnAction(event -> actionButtonUpdateCompaniesTable(event));
        companiesTableController.getEditCompanyButton().setOnAction(event -> actionButtonEditCompany(event));
        companiesTableController.getDeleteCompanyButton().setOnAction(event -> actionButtonDeleteCompany(event));

        companyEditController.getSaveCompanyButton().setOnAction(event -> actionButtonSaveCompany(event));

        vacanciesTableController.getTableVacancies().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            vacanciesTableController.enableTargetItemButtons();
            vacancyViewController.setData(newSelection);
            vacancyViewController.view();
        });

        vacanciesTableController.getAddVacancyButton().setOnAction(event -> actionButtonCreateVacancy(event));
        vacanciesTableController.getUpdateVacanciesButton().setOnAction(event -> actionButtonUpdateVacanciesTable(event));
        vacanciesTableController.getEditVacancyButton().setOnAction(event -> actionButtonEditVacancy(event));
        vacanciesTableController.getDeleteVacancyButton().setOnAction(event -> actionButtonDeleteVacancy(event));

        vacancyEditController.getSaveVacancyButton().setOnAction(event -> actionButtonSaveVacancy(event));
    }

    public void actionButtonCreateCompany(ActionEvent event) {
        companyEditController.clear();
        companyEditController.view();
    }

    public void actionButtonUpdateCompaniesTable(ActionEvent event) {
        companiesTableController.clear();
        vacanciesTableController.disableNoTargetItemButtons();
        vacanciesTableController.clear();
        actionButtonUpdateVacanciesTable(event);
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
        if (company != null) {
            Long companyId = company.getId();
            companyDao.delete(company);
            vacancyDao.deleteByCompanyId(companyId);
        }
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
        vacancyEditController.clear();
        vacancyEditController.view();
    }

    public void actionButtonUpdateVacanciesTable(ActionEvent event) {
        vacanciesTableController.clear();
        vacanciesTableController.disableTargetItemButtons();
        if (companiesTableController.getTableCompanies().getSelectionModel() != null) {
            Company company = companiesTableController.getTableCompanies().getSelectionModel().getSelectedItem();
            if (company != null) vacanciesTableController.setData(vacancyDao.findAllByCompanyId(company.getId()));
        }
        vacancyEditController.hide();
        vacancyViewController.hide();
        logger.debug("Vacancies table has been updated...");
    }

    public void actionButtonEditVacancy(ActionEvent event) {
        Vacancy vacancy = vacanciesTableController.getTableVacancies().getSelectionModel().getSelectedItem();
        if (vacancy != null) {
            vacancyEditController.setData(vacancy);
            vacancyEditController.view();
        } else actionButtonSaveVacancy(event);
    }

    public void actionButtonDeleteVacancy(ActionEvent event) {
        Vacancy vacancy = vacanciesTableController.getTableVacancies().getSelectionModel().getSelectedItem();
        if (vacancy != null) vacancyDao.delete(vacancy);
        actionButtonUpdateVacanciesTable(event);
    }

    public void actionButtonSaveVacancy(ActionEvent event) {
        Long companyId = companiesTableController.getTableCompanies().getSelectionModel().getSelectedItem().getId();
        Boolean isSaved = vacancyEditController.save(companyId);
        if (isSaved) {
            vacancyEditController.hide();
            vacancyEditController.clear();
            actionButtonUpdateVacanciesTable(event);
        }
    }

    public void reloadVacancyTable(ActionEvent event) {
        actionButtonUpdateVacanciesTable(event);
    }

    public void reloadCompanyTable(ActionEvent event) {
        actionButtonUpdateCompaniesTable(event);
    }

    public void reload(ActionEvent event) {
        reloadCompanyTable(event);
        companyEditController.reload(event);
        reloadVacancyTable(event);
        vacancyEditController.reload(event);
    }

}
