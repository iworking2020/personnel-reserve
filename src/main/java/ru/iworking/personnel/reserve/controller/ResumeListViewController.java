package ru.iworking.personnel.reserve.controller;

import com.google.inject.Inject;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ResumeListViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumeListViewController.class);

    @Inject private ResumeService resumeService;

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
            //getClickListViewController().selectionItem(newSelection);
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

    public void clearSelection() {
        if (resumeListView.getSelectionModel() != null) resumeListView.getSelectionModel().clearSelection();
    }

    public void selectionItem(Resume resume) {
        if (resumeListView.getSelectionModel() != null) {
            List<Resume> list = resumeListView.getItems().stream()
                    .filter(resume1 -> resume1.getId() == resume.getId())
                    .collect(Collectors.toList());
            if (list.size() > 0) {
                resumeListView.getSelectionModel().select(list.get(0));
            } else {
                this.clearSelection();
            }

        }
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        Long resumeId = null;
        if (resumeListView.getSelectionModel() != null) {
            Resume resume = resumeListView.getSelectionModel().getSelectedItem();
            if (resume != null) resumeId = resume.getId();
        }
        this.initData();
        if (resumeListView.getSelectionModel() != null) {
            if (resumeId != null) {
                Long finalResumeId = resumeId;
                resumeListView.getItems().stream()
                        .filter(resume -> resume.getId() == finalResumeId)
                        .forEach(resume -> resumeListView.getSelectionModel().select(resume));
            }
        }
    }

    @FXML
    public void actionCreate(ActionEvent event) {
        getResumeEditController().clear();
        getResumeEditController().show();
    }

    public ResumeEditController getResumeEditController() {
        return (ResumeEditController) getControllerProvider().get(ResumeEditController.class.getName());
    }

    public ResumeViewController getResumeViewController() {
        return (ResumeViewController) getControllerProvider().get(ResumeViewController.class.getName());
    }

    public ClickListViewController getClickListViewController() {
        return (ClickListViewController) getControllerProvider().get(ClickListViewController.class.getName());
    }

}
