package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.component.CompanyListViewCell;
import ru.iworking.personnel.reserve.service.CompanyService;

import java.net.URL;
import java.util.ResourceBundle;

public class VacanciesPaneController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(VacanciesPaneController.class);

    @FXML private Pane wrapperResume;
    @FXML private Pane wrapperClient;

    @FXML private CompaniesTableController companiesTableController;
    @FXML private VacanciesTableController vacanciesTableController;

    //@FXML private ResumesTreeController resumesTreeController;

    @FXML private CompanyViewController companyViewController;
    @FXML private CompanyEditController companyEditController;
    @FXML private VacancyViewController vacancyViewController;
    @FXML private VacancyEditController vacancyEditController;
    @FXML private ResumeViewController resumeViewController;
    @FXML private ResumeEditController resumeEditController;

    @FXML private ListView companyListView;
    @FXML private ScrollPane resumesAccordionWrapper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyListView.setItems(FXCollections.observableList(CompanyService.INSTANCE.findAll()));
        companyListView.setCellFactory(listView -> new CompanyListViewCell());
        isResizable(false);
    }

    public void isResizable(boolean isResizable) {
        if (isResizable) {
            companyListView.setMinWidth(0.00);
            resumesAccordionWrapper.setMinWidth(0.00);
        } else {
            final double maxWidth1 = companyListView.getMaxWidth();
            companyListView.setMinWidth(maxWidth1);
            final double maxWidth2 = resumesAccordionWrapper.getMaxWidth();
            resumesAccordionWrapper.setMinWidth(maxWidth2);
        }
    }

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
