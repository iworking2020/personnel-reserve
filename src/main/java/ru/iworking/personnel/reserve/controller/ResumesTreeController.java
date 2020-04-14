package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.model.TreeStep;
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.iworking.personnel.reserve.model.TreeStep.Type.CATEGORY;
import static ru.iworking.personnel.reserve.model.TreeStep.Type.VALUE;

public class ResumesTreeController implements Initializable {

    @FXML private TreeView<TreeStep> resumesTreeView;

    private ResumeStateDao resumeStateDao = ResumeStateDao.getInstance();
    private ResumeDao resumeDao = ResumeDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TreeItem<TreeStep> rootTreeNode = new TreeItem<>(new TreeStep(null, "Этапы обработки", CATEGORY));
        resumeStateDao.findAllFromCash().forEach(resumeState -> {

                TreeStep stepCategory = new TreeStep(resumeState.getId(), resumeState.getNameToView(LocaleUtil.getDefault()), CATEGORY);
                TreeItem<TreeStep> itemCategory = new TreeItem<>(stepCategory);

                resumeDao.findAllByResumeStateId(resumeState.getId()).forEach(resume -> {

                    TreeStep stepResume = new TreeStep(resume.getId(), resume.getProfile().getFullName(), VALUE);
                    TreeItem<TreeStep> itemValue = new TreeItem<>(stepResume);

                    itemCategory.getChildren().add(itemValue);
                });

                rootTreeNode.getChildren().add(itemCategory);
            }
        );
        resumesTreeView.setRoot(rootTreeNode);
        resumesTreeView.setShowRoot(false);

        /*Object object = resumesTreeView.getSelectionModel().getSelectedItem();*/

    }


}
