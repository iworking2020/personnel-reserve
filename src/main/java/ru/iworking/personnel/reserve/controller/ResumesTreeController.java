package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.model.TreeViewStep;
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.iworking.personnel.reserve.model.TreeViewStep.StepType.CATEGORY;
import static ru.iworking.personnel.reserve.model.TreeViewStep.StepType.VALUE;

public class ResumesTreeController extends FxmlController {

    @FXML private TreeView<TreeViewStep> resumesTreeView;
    public TreeView<TreeViewStep> getTreeView() {
        return resumesTreeView;
    }

    @FXML private Button updateButton;
    public Button getUpdateButton() {
        return updateButton;
    }

    @FXML private Button buttonEdit;
    public Button getButtonEdit() {
        return buttonEdit;
    }


    private ResumeStateDao resumeStateDao = ResumeStateDao.getInstance();
    private ResumeDao resumeDao = ResumeDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        TreeItem<TreeViewStep> object = resumesTreeView.getSelectionModel().getSelectedItem();
    }

    public void initData() {
        TreeItem<TreeViewStep> rootTreeNode = new TreeItem<>(new TreeViewStep(null, "Этапы обработки", CATEGORY));
        resumeStateDao.findAllFromCash().forEach(resumeState -> {
            TreeViewStep stepCategory = new TreeViewStep(resumeState.getId(), resumeState.getNameToView(LocaleUtil.getDefault()), CATEGORY);
            TreeItem<TreeViewStep> itemCategory = new TreeItem<>(stepCategory);
            resumeDao.findAllByResumeStateId(resumeState.getId()).forEach(resume -> {
                TreeViewStep stepResume = new TreeViewStep(resume.getId(), resume.getProfile().getFullName(), VALUE);
                TreeItem<TreeViewStep> itemValue = new TreeItem<>(stepResume);
                itemCategory.getChildren().add(itemValue);
            });
            rootTreeNode.getChildren().add(itemCategory);
        });
        resumesTreeView.setRoot(rootTreeNode);
        resumesTreeView.setShowRoot(false);
    }

    @FXML
    public void actionEdit(ActionEvent event) {
        ResumeEditController resumeEditController = (ResumeEditController) getControllerProvider().get(ResumeEditController.class);
        VacanciesPaneController vacanciesPaneController = (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class);
        resumeEditController.show();
        vacanciesPaneController.hideWrapperClient();
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        ResumeViewController resumeViewController = (ResumeViewController) getControllerProvider().get(ResumeViewController.class);
        ResumeEditController resumeEditController = (ResumeEditController) getControllerProvider().get(ResumeEditController.class);
        VacanciesPaneController vacanciesPaneController = (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class);
        resumeViewController.hide();
        resumeEditController.hide();
        reload();
        vacanciesPaneController.showWrapperClient();
    }

    public void reload() {
        resumeStateDao.clearCash();
        initData();
    }

}
