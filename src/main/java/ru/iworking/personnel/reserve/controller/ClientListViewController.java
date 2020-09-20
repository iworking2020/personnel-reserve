package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.layout.VacancyListViewPane;
import ru.iworking.personnel.reserve.component.list.view.cell.CompanyCell;
import ru.iworking.personnel.reserve.component.list.view.factory.CompanyCellControllerFactory;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.service.CompanyService;

import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class ClientListViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ClientListViewController.class);

    private final CompanyService companyService;

    @Autowired @Lazy private CompanyViewController companyViewController;
    @Autowired @Lazy private CompanyEditController companyEditController;
    @Autowired @Lazy private VacancyTabContentController vacanciesPaneController;
    @Autowired @Lazy private VacancyListViewController vacancyListViewController;
    @Autowired @Lazy private CompanyCellControllerFactory companyCellControllerFactory;

    @FXML private Pane parent;

    @FXML private ListView<Company> companyListView;

    @FXML private Button addButton;
    @FXML private Button updateButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyListView.setCellFactory(listView -> {
            CompanyCell cell = new CompanyCell(companyCellControllerFactory);
            cell.setOnMouseClicked(event -> {
                companyEditController.clear();
                companyEditController.hide();
                if (Objects.nonNull(cell.getCompanyCellController()))
                    createVacancyListViewPane(cell.getCompanyCellController().getCompany());
            });
            return cell;
        });
        companyListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            companyViewController.setData(newSelection);
            companyViewController.show();
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
        VacancyListViewPane vacancyListViewPane = new VacancyListViewPane();
        vacancyListViewController.setVacancyListViewPane(vacancyListViewPane);
        vacancyListViewController.init();
        vacancyListViewController.setXPosition(vacanciesPaneController.getClientListViewWrapper().getMaxWidth());
        vacancyListViewController.show();
        parent.getChildren().add(vacancyListViewPane);
    }

    public void initData() {
        companyListView.setItems(FXCollections.observableList(companyService.findAll()));
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        this.initData();
        companyEditController.hide();

        Company company = companyListView.getSelectionModel().getSelectedItem();
        if (company != null) {
            companyViewController.setData(company);
            companyViewController.show();
        } else {
            companyViewController.hide();
        }
    }

    @FXML
    public void actionCreate(ActionEvent event) {
        companyEditController.clear();
        companyEditController.show();
    }

    public void selectCompany(Company company) {
        companyListView.getSelectionModel().select(company);
    }



}
