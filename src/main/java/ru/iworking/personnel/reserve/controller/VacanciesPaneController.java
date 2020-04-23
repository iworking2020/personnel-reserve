package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.dao.VacancyDao;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.model.TreeViewStep;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.iworking.personnel.reserve.model.TreeViewStep.StepType;

public class VacanciesPaneController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(VacanciesPaneController.class);

    @FXML private Pane wrapperResume;
    @FXML private Pane wrapperClient;

    @FXML private CompaniesTableController companiesTableController;
    @FXML private VacanciesTableController vacanciesTableController;

    @FXML private ResumesTreeController resumesTreeController;

    @FXML private CompanyViewController companyViewController;
    @FXML private CompanyEditController companyEditController;
    @FXML private VacancyViewController vacancyViewController;
    @FXML private VacancyEditController vacancyEditController;
    @FXML private ResumeViewController resumeViewController;
    @FXML private ResumeEditController resumeEditController;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void showWrapperClient() {
        wrapperClient.setVisible(true);
        wrapperClient.setManaged(true);
    }

    public void hideWrapperClient() {
        wrapperClient.setVisible(false);
        wrapperClient.setManaged(false);
    }

    public void reloadVacancyTable(ActionEvent event) {
        vacanciesTableController.actionUpdate(event);
    }

    public void reloadCompanyTable(ActionEvent event) {
        companiesTableController.actionUpdate(event);
    }

    public void reload(ActionEvent event) {
        reloadCompanyTable(event);
        companyEditController.reload(event);
        reloadVacancyTable(event);
        vacancyEditController.reload(event);
    }

}
