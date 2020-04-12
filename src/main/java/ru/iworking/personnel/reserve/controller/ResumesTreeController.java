package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ResumesTreeController implements Initializable {

    @FXML private TreeView resumesTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] categories = {
                "Отправлено приглашение",
                "Тех. интервью",
                "Интервью",
                "Резюме у заказчика",
                "Интервью с заказчиком",
                "Предложение кандидату",
                "Испытательный срок",
                "Принят на работу",
                "Отклонен рекрутером",
                "Отклонен заказчиком",
                "Резерв"
        };

        TreeItem rootTreeNode = new TreeItem<>("Этапы обработки");
        Arrays.asList(categories).forEach(category -> rootTreeNode.getChildren().add(new TreeItem<>(category)));

        resumesTreeView.setRoot(rootTreeNode);
        resumesTreeView.setShowRoot(false);

        /*Object object = resumesTreeView.getSelectionModel().getSelectedItem();*/

    }


}
