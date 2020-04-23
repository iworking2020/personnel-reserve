package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.service.api.utils.LocaleUtil;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class VacanciesTableController extends FxmlController {

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @FXML private Button addVacancyButton;
    public Button getAddVacancyButton() {
        return addVacancyButton;
    }

    @FXML private Button updateVacanciesButton;
    public Button getUpdateVacanciesButton() {
        return updateVacanciesButton;
    }

    @FXML private Button editVacancyButton;
    public Button getEditVacancyButton() {
        return editVacancyButton;
    }

    @FXML private Button deleteVacancyButton;
    public Button getDeleteVacancyButton() {
        return deleteVacancyButton;
    }

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
    }

    public void setData(List<Vacancy> data) {
        tableVacancies.setItems(FXCollections.observableList(data));
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

}
