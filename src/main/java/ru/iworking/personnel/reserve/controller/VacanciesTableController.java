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
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class VacanciesTableController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(VacanciesTableController.class);

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @FXML private Button addVacancyButton;
    @FXML private Button updateVacanciesButton;
    @FXML private Button editVacancyButton;
    @FXML private Button deleteVacancyButton;

    @FXML private TableView<Vacancy> tableVacancies;
    public TableView<Vacancy> getTableVacancies() {
        return tableVacancies;
    }

    @FXML private TableColumn<Vacancy, String> professionCol;
    @FXML private TableColumn<Vacancy, String> profFieldCol;
    @FXML private TableColumn<Vacancy, String> workTypeCol;
    @FXML private TableColumn<Vacancy, String> educationCol;
    @FXML private TableColumn<Vacancy, String> wageCol;
    @FXML private TableColumn<Vacancy, String> currencyCol;

    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();
    private VacancyDao vacancyDao = VacancyDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        professionCol.setCellValueFactory(new PropertyValueFactory<>("profession"));
        profFieldCol.setCellValueFactory(cellData -> {
            String textColumn = "не указана";
            if (cellData.getValue() != null && cellData.getValue().getProfFieldId() != null) {
                ProfField profField = profFieldDao.findFromCash(cellData.getValue().getProfFieldId());
                textColumn = profField.getNameView().getName();
            }
            return new ReadOnlyStringWrapper(textColumn);
        });
        workTypeCol.setCellValueFactory(cellData -> {
            String textColumn = "не указан";
            if (cellData.getValue() != null && cellData.getValue().getWorkTypeId()!= null) {
                WorkType workType = workTypeDao.findFromCash(cellData.getValue().getWorkTypeId());
                textColumn = workType.getNameView().getName();
            }
            return new ReadOnlyStringWrapper(textColumn);
        });
        educationCol.setCellValueFactory(cellData -> {
            String textColumn = "не указано";
            if (cellData.getValue() != null && cellData.getValue().getEducationId() != null) {
                Education education = educationDao.findFromCash(cellData.getValue().getEducationId());
                textColumn = education.getNameView().getName();
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
            String textColumn = currency != null ? currency.getNameView().getName() : "не указана";
            return new ReadOnlyStringWrapper(textColumn);
        });

        tableVacancies.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            enableTargetItemButtons();
            getVacancyViewController().setData(newSelection);
            getVacancyViewController().show();
        });
    }

    public void setData(List<Vacancy> data) {
        tableVacancies.setItems(FXCollections.observableList(data));
    }

    @FXML
    public void actionCreate(ActionEvent event) {
        getVacancyEditController().clear();
        getVacancyEditController().show();
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        clear();
        disableTargetItemButtons();
        if (getCompaniesTableController().getTableCompanies().getSelectionModel() != null) {
            Company company = getCompaniesTableController().getTableCompanies().getSelectionModel().getSelectedItem();
            if (company != null) setData(vacancyDao.findAllByCompanyId(company.getId()));
        }
        getVacancyEditController().hide();
        getVacancyViewController().hide();
        logger.debug("Vacancies table has been updated...");
    }

    @FXML
    public void actionEdit(ActionEvent event) {
        Vacancy vacancy = tableVacancies.getSelectionModel().getSelectedItem();
        if (vacancy != null) {
            getVacancyEditController().setData(vacancy);
            getVacancyEditController().show();
        } else getVacancyEditController().actionSave(event);
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        Vacancy vacancy = tableVacancies.getSelectionModel().getSelectedItem();
        if (vacancy != null) vacancyDao.delete(vacancy);
        actionUpdate(event);
    }

    public void enableTargetItemButtons() {
        editVacancyButton.setDisable(false);
        deleteVacancyButton.setDisable(false);
    }

    public void disableTargetItemButtons() {
        editVacancyButton.setDisable(true);
        deleteVacancyButton.setDisable(true);
    }

    public void enableNoTargetButtons() {
        addVacancyButton.setDisable(false);
        updateVacanciesButton.setDisable(false);
    }

    public void disableNoTargetItemButtons() {
        addVacancyButton.setDisable(true);
        updateVacanciesButton.setDisable(true);
    }

    public void clear() {
        if (tableVacancies.getSelectionModel()!=null) tableVacancies.getSelectionModel().clearSelection();
        tableVacancies.setItems(null);
        disableTargetItemButtons();
    }

    public VacancyEditController getVacancyEditController() {
        return (VacancyEditController) getControllerProvider().get(VacancyEditController.class.getName());
    }

    public VacancyViewController getVacancyViewController() {
        return (VacancyViewController) getControllerProvider().get(VacancyViewController.class.getName());
    }

    public CompaniesTableController getCompaniesTableController() {
        return (CompaniesTableController) getControllerProvider().get(CompaniesTableController.class.getName());
    }
}
