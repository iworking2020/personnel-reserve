package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.model.TreeCategory;
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.iworking.personnel.reserve.model.TreeCategory.Type.CATEGORY;

public class ResumesTreeController implements Initializable {

    @FXML private TreeView<TreeCategory> resumesTreeView;

    private ResumeStateDao resumeStateDao = ResumeStateDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TreeItem<TreeCategory> rootTreeNode = new TreeItem<>(new TreeCategory(null, "Этапы обработки", CATEGORY));
        resumeStateDao.findAllFromCash().forEach(resumeState -> rootTreeNode.getChildren().add(
                new TreeItem<TreeCategory>(
                        new TreeCategory(resumeState.getId(), resumeState.getNameToView(LocaleUtil.getDefault()), CATEGORY)
                )
        ));
        resumesTreeView.setRoot(rootTreeNode);
        resumesTreeView.setShowRoot(false);

        /*Object object = resumesTreeView.getSelectionModel().getSelectedItem();*/

    }


}
