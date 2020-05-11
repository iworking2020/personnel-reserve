package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.component.ResumeCell;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.ResumeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumeListViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumeListViewController.class);

    private final ResumeService resumeService = ResumeService.INSTANCE;

    @FXML private Pane parent;

    @FXML private ListView<Resume> resumeListView;

    @FXML private Button addButton;
    @FXML private Button updateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resumeListView.setCellFactory(listView -> {
            ResumeCell cell = new ResumeCell();
            return cell;
        });
        resumeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            getResumeViewController().setData(newSelection);
            getResumeViewController().show();
            getResumeEditController().hide();
            getResumeEditController().clear();
        });

        updateButton.setText("");
        addButton.setText("");

        initData();
    }

    public void initData() {
        resumeListView.setItems(FXCollections.observableList(resumeService.findAll()));
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        this.initData();
    }

    @FXML
    public void actionCreate(ActionEvent event) {
        /*getCompanyEditController().clear();
        getCompanyEditController().show();*/
    }

    public ResumeEditController getResumeEditController() {
        return (ResumeEditController) getControllerProvider().get(ResumeEditController.class.getName());
    }

    public ResumeViewController getResumeViewController() {
        return (ResumeViewController) getControllerProvider().get(ResumeViewController.class.getName());
    }

}
