package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.component.CompanyListViewCell;
import ru.iworking.personnel.reserve.component.VacancyListViewPane;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.service.CompanyService;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientListViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ClientListViewController.class);

    @FXML private Pane parent;

    @FXML private ListView<Company> companyListView;

    @FXML private Button addButton;
    @FXML private Button updateButton;

    private VacancyListViewPane vacancyListViewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCompaniesList();
        companyListView.setCellFactory(listView -> {
            CompanyListViewCell cell = new CompanyListViewCell();
            cell.setOnMouseClicked(event -> createVacancyListViewPane(cell.getCompany()));
            return cell;
        });
        companyListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            getCompanyEditController().clear();
            getCompanyEditController().hide();
            getCompanyViewController().setData(newSelection);
            getCompanyViewController().show();
        });

        updateButton.setText("");
        addButton.setText("");
    }

    public void createVacancyListViewPane(Company company) {
        vacancyListViewPane = new VacancyListViewPane();
        vacancyListViewPane.setXPosition(getVacanciesPaneController().getClientListViewWrapper().getMaxWidth());
        vacancyListViewPane.show();
        parent.getChildren().add(vacancyListViewPane);
    }

    public void initCompaniesList() {
        companyListView.setItems(FXCollections.observableList(CompanyService.INSTANCE.findAll()));
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        companyListView.setItems(FXCollections.observableList(CompanyService.INSTANCE.findAll()));
        getCompanyEditController().hide();

        Company company = companyListView.getSelectionModel().getSelectedItem();
        if (company != null) {
            getCompanyViewController().setData(company);
            getCompanyViewController().show();
        } else {
            getCompanyViewController().hide();
        }
    }

    @FXML
    public void actionCreate(ActionEvent event) {
        //companyListView.getSelectionModel().clearSelection();
        getCompanyEditController().clear();
        getCompanyEditController().show();
        //getCompanyViewController().hide();
    }

    public VacancyListViewPane getVacancyListViewPane() {
        return vacancyListViewPane;
    }

    public void selectCompany(Company company) {
        companyListView.getSelectionModel().select(company);
    }

    public CompanyViewController getCompanyViewController() {
        return (CompanyViewController) getControllerProvider().get(CompanyViewController.class.getName());
    }

    public CompanyEditController getCompanyEditController() {
        return (CompanyEditController) getControllerProvider().get(CompanyEditController.class.getName());
    }

    public VacanciesPaneController getVacanciesPaneController() {
        return (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class.getName());
    }

}
