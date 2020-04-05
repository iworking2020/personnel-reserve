package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.model.BigDecimalFormatter;
import ru.iworking.service.api.utils.LocaleUtil;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class VacanciesPaneFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacanciesPaneFxmlController.class);

    private BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();
    private CompanyDao companyDao = CompanyDao.getInstance();
    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();

    @FXML private CompanyViewController companyViewController;
    @FXML private CompanyEditController companyEditController;

    @FXML private Button editCompanyButton;
    @FXML private Button deleteCompanyButton;

    @FXML private TableView<Company> tableCompanies;
    @FXML private TableColumn<Company, String> companyTypeColumn;
    @FXML private TableColumn<Company, String> companyNameColumn;

    @FXML private Button addVacancyButton;
    @FXML private Button updateVacanciesButton;
    @FXML private Button editVacancyButton;
    @FXML private Button deleteVacancyButton;

    @FXML private TableView<Vacancy> tableVacancies;
    @FXML private TableColumn<Vacancy, String> professionCol;
    @FXML private TableColumn<Vacancy, String> profFieldCol;
    @FXML private TableColumn<Vacancy, String> workTypeCol;
    @FXML private TableColumn<Vacancy, String> educationCol;
    @FXML private TableColumn<Vacancy, String> wageCol;
    @FXML private TableColumn<Vacancy, String> currencyCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        companyTypeColumn.setCellValueFactory(cellData -> {
            CompanyType companyType = companyTypeDao.findFromCash(cellData.getValue().getCompanyTypeId());
            String textColumn = companyType != null ? companyType.getAbbreviatedNameToView(LocaleUtil.getDefault()) : "не указан";
            return new ReadOnlyStringWrapper(textColumn);
        });
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));
        tableCompanies.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editCompanyButton.setDisable(false);
            deleteCompanyButton.setDisable(false);
            companyViewController.setData(newSelection);
            companyViewController.view();
            addVacancyButton.setDisable(false);
            updateVacanciesButton.setDisable(false);
        });

        professionCol.setCellValueFactory(new PropertyValueFactory<>("profession"));
        profFieldCol.setCellValueFactory(cellData -> {
            String textColumn = "не указана";
            if (cellData.getValue() != null && cellData.getValue().getProfFieldId() != null) {
                ProfField profField = profFieldDao.findFromCash(cellData.getValue().getProfFieldId());
                textColumn = profField.getNameToView(LocaleUtil.getDefault());
            }
            return new ReadOnlyStringWrapper(textColumn);
        });
        workTypeCol.setCellValueFactory(cellData -> {
            String textColumn = "не указан";
            if (cellData.getValue() != null && cellData.getValue().getWorkTypeId()!= null) {
                WorkType workType = workTypeDao.findFromCash(cellData.getValue().getWorkTypeId());
                textColumn = workType.getNameToView(LocaleUtil.getDefault());
            }
            return new ReadOnlyStringWrapper(textColumn);
        });
        educationCol.setCellValueFactory(cellData -> {
            String textColumn = "не указано";
            if (cellData.getValue() != null && cellData.getValue().getEducationId() != null) {
                Education education = educationDao.findFromCash(cellData.getValue().getEducationId());
                textColumn = education.getNameToView(LocaleUtil.getDefault());
            }
            return new ReadOnlyStringWrapper(textColumn);
        });
        wageCol.setCellValueFactory(cellData -> {
            BigDecimal wage = null;
            if (cellData.getValue().getWage() != null) wage = cellData.getValue().getWage().getCountBigDecimal();
            String textColumn = wage != null ? decimalFormat.format(wage) : "договорная";
            return new ReadOnlyStringWrapper(textColumn);
        });
        currencyCol.setCellValueFactory(cellData -> {
            Wage wage = cellData.getValue().getWage();
            Currency currency = wage != null ? currencyDao.findFromCash(wage.getCurrencyId()) : null;
            String textColumn = currency != null ? currency.getNameToView(LocaleUtil.getDefault()) : "не указана";
            return new ReadOnlyStringWrapper(textColumn);
        });

        companyEditController.getSaveCompanyButton().setOnAction(event -> actionButtonSaveCompany(event));
    }

    @FXML
    public void actionButtonCreateCompany(ActionEvent event) {
        companyEditController.clear();
        companyEditController.view();
    }

    @FXML
    public void actionButtonUpdateCompaniesTable(ActionEvent event) {
        clearSelectionModelCompaniesTable();
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));
        companyEditController.hide();
        companyViewController.hide();
        logger.debug("Companies table has been updated...");
    }

    @FXML
    public void actionButtonEditCompany(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
        if (company != null) {
            companyEditController.setData(company);
            companyEditController.view();
        } else actionButtonSaveCompany(event);
    }

    @FXML
    public void actionButtonDeleteCompany(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
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

    @FXML
    public void actionButtonCreateVacancy(ActionEvent event) {
        //clearVacancyEditBlock();
        //vacancyEditBlock.setVisible(true);
    }

    public void reloadCompanyTable(ActionEvent event) {
        actionButtonUpdateCompaniesTable(event);
    }

    private void clearSelectionModelCompaniesTable() {
        tableCompanies.getSelectionModel().clearSelection();
        editCompanyButton.setDisable(true);
        deleteCompanyButton.setDisable(true);
        addVacancyButton.setDisable(true);
        updateVacanciesButton.setDisable(true);
        clearSelectionModelVacanciesTable();
    }

    private void clearSelectionModelVacanciesTable() {
        tableVacancies.getSelectionModel().clearSelection();
        editVacancyButton.setDisable(true);
        deleteVacancyButton.setDisable(true);
    }

    public void reload(ActionEvent event) {
        companyTypeDao.clearCash();
        reloadCompanyTable(event);
        companyEditController.reload(event);
    }

}
