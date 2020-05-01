package ru.iworking.personnel.reserve.component;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.personnel.reserve.model.EducationCellFactory;

import java.io.IOException;

public class EducationEditBlock extends VBox {

    @FXML private Pane parentEducation;

    @FXML private ComboBox<Education> educationComboBox;

    private EducationCellFactory educationCellFactory = new EducationCellFactory();

    public EducationEditBlock() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EducationEditBlock.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initData();
    }

    public void initData() {
        educationComboBox.setButtonCell(educationCellFactory.call(null));
        educationComboBox.setCellFactory(educationCellFactory);
        educationComboBox.setItems(FXCollections.observableList(EducationDao.INSTANCE.findAll()));
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        ((Pane)parentEducation.getParent()).getChildren().remove(parentEducation);
    }

}
