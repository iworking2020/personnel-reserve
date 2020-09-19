package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.ResumeCell;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.ResumeService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResumeListViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ResumeListViewController.class);

    private final ResumeService resumeService;

    @Autowired @Lazy private ResumeEditController resumeEditController;
    @Autowired @Lazy private ResumeViewController resumeViewController;

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
            resumeViewController.setData(newSelection);
            resumeViewController.show();
            resumeEditController.hide();
            resumeEditController.clear();
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
                    .filter(resume1 -> resume1.getId().equals(resume.getId()))
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
                ObservableList<Resume> resumes = resumeListView.getItems();
                resumes.stream()
                        .forEach(resume -> {
                            if (resume.getId().equals(finalResumeId)) resumeListView.getSelectionModel().select(resume);
                        });
            }
        }
    }

    @FXML
    public void actionCreate(ActionEvent event) {
        resumeEditController.clear();
        resumeEditController.show();
    }

}
