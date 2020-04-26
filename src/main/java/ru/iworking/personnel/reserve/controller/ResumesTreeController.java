package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.model.TreeViewStep;
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.iworking.personnel.reserve.model.TreeViewStep.StepType.CATEGORY;
import static ru.iworking.personnel.reserve.model.TreeViewStep.StepType.VALUE;

public class ResumesTreeController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumesTreeController.class);

    @FXML private TreeView<TreeViewStep> resumesTreeView;
    @FXML private Button updateButton;
    @FXML private Button buttonEdit;


    private ResumeStateDao resumeStateDao = ResumeStateDao.getInstance();
    private ResumeDao resumeDao = ResumeDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonEdit.setDisable(true);
        initData();
        //TreeItem<TreeViewStep> object = resumesTreeView.getSelectionModel().getSelectedItem();
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

        resumesTreeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                TreeViewStep treeStep = newValue.getValue();
                if (treeStep.getType() == TreeViewStep.StepType.VALUE) {
                    buttonEdit.setDisable(false);
                    Long id = treeStep.getCode();
                    if (id != null) {
                        try {
                            Resume resume = resumeDao.find(id);
                            if (resume == null) {
                                throw new NotFoundException("resume not found");
                            } else {
                                getResumeViewController().setData(resume);
                                getResumeViewController().show();
                                getResumeEditController().hide();
                                getResumeEditController().clear();
                                getVacanciesPaneController().hideWrapperClient();
                            }
                        } catch (Exception ex) {
                            logger.error(ex);
                            newValue.getParent().getChildren().remove(newValue);
                            logger.debug("resume is null..., remove from treeView");
                        }
                    } else {
                        logger.debug("treeStep.getCode() is null..., skip");
                    }
                } else {
                    buttonEdit.setDisable(true);
                    getResumeViewController().hide();
                    getResumeEditController().hide();
                    getResumeEditController().clear();
                    getVacanciesPaneController().showWrapperClient();
                }
            } else {
                buttonEdit.setDisable(true);
            }
        });
    }

    @FXML
    public void actionEdit(ActionEvent event) throws Exception {
        TreeViewStep treeStep = resumesTreeView.getSelectionModel().getSelectedItem().getValue();
        if (treeStep.getType() == TreeViewStep.StepType.VALUE) {
            Resume resume = resumeDao.find(treeStep.getCode());
            getResumeEditController().setData(resume);
            getResumeEditController().show();
            getVacanciesPaneController().hideWrapperClient();
        } else {
            throw new Exception("error load resume");
        }
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        getResumeViewController().hide();
        getResumeEditController().hide();
        getResumeEditController().clear();
        reload();
        getVacanciesPaneController().showWrapperClient();
    }

    public void reload() {
        resumeStateDao.clearCash();
        initData();
    }

    public ResumeEditController getResumeEditController() {
        return (ResumeEditController) getControllerProvider().get(ResumeEditController.class.getName());
    }

    public ResumeViewController getResumeViewController() {
        return (ResumeViewController) getControllerProvider().get(ResumeViewController.class.getName());
    }

    public VacanciesPaneController getVacanciesPaneController() {
        return (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class.getName());
    }
}
