package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iworking.personnel.reserve.component.CompanyCell;
import ru.iworking.personnel.reserve.component.VacancyListViewPane;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.service.CompanyService;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.LogoService;
import ru.iworking.personnel.reserve.service.VacancyService;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ClientListViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ClientListViewController.class);

    @Autowired private CompanyService companyService;
    @Autowired private CompanyTypeService companyTypeService;
    @Autowired private VacancyService vacancyService;
    @Autowired private LogoService logoService;

    @FXML private Pane parent;

    @FXML private ListView<Company> companyListView;

    @FXML private Button addButton;
    @FXML private Button updateButton;

    private VacancyListViewPane vacancyListViewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyListView.setCellFactory(listView -> {
            CompanyCell cell = new CompanyCell(companyTypeService, logoService);
            cell.setOnMouseClicked(event -> {
                getCompanyEditController().clear();
                getCompanyEditController().hide();
                createVacancyListViewPane(cell.getCompany());
            });
            return cell;
        });
        companyListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            getCompanyViewController().setData(newSelection);
            getCompanyViewController().show();
        });

        updateButton.setText("");
        addButton.setText("");

        initData();
    }

    public void createVacancyListViewPane(Company company) {
        Iterator<Node> i = parent.getChildren().iterator();
        while (i.hasNext()) {
            Node node = i.next(); // должен быть вызван перед тем, как вызывается i.remove()
            if (node instanceof VacancyListViewPane) i.remove();
        }
        vacancyListViewPane = new VacancyListViewPane(vacancyService);
        vacancyListViewPane.setXPosition(getVacanciesPaneController().getClientListViewWrapper().getMaxWidth());
        vacancyListViewPane.show();
        parent.getChildren().add(vacancyListViewPane);
    }

    public void initData() {
        companyListView.setItems(FXCollections.observableList(companyService.findAll()));
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        this.initData();
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
        getCompanyEditController().clear();
        getCompanyEditController().show();
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

    public VacancyTabContentController getVacanciesPaneController() {
        return (VacancyTabContentController) getControllerProvider().get(VacancyTabContentController.class.getName());
    }

}
