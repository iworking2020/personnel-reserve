package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.iworking.personnel.reserve.dao.CurrencyDao;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.service.api.utils.LocaleUtil;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class VacanciesTableController implements Initializable {

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

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

    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();

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
        tableVacancies.getSelectionModel().clearSelection();
        disableTargetItemButtons();
    }

}
