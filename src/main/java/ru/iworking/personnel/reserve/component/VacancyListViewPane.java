package ru.iworking.personnel.reserve.component;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ru.iworking.personnel.reserve.controller.ControllerProvider;
import ru.iworking.personnel.reserve.controller.VacancyEditController;
import ru.iworking.personnel.reserve.controller.VacancyViewController;
import ru.iworking.personnel.reserve.entity.Vacancy;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VacancyListViewPane extends BorderPane implements Initializable {

    private ControllerProvider controllerProvider = ControllerProvider.getInstance();

    @FXML private Pane parent;

    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private Button updateButton;

    @FXML private ListView<Vacancy> vacancyListView;

    private double x = 0.00;

    public VacancyListViewPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/VacancyListViewPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        controllerProvider.put(this.getClass().getName(), this);
    }

    public void setData(List<Vacancy> data) {
        vacancyListView.setItems(FXCollections.observableList(data));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vacancyListView.setCellFactory(listView -> {
            VacancyCell cell = new VacancyCell();
            return cell;
        });
        vacancyListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            getVacancyEditController().clear();
            getVacancyEditController().hide();
            getVacancyViewController().setData(newSelection);
            getVacancyViewController().show();
        });

        backButton.setText("");
        updateButton.setText("");
        addButton.setText("");
    }

    public void setXPosition(double x) {
        this.x = x;
        parent.translateXProperty().set(x);
    }

    public void show() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(parent.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void hide() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(parent.translateXProperty(), x, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        timeline.setOnFinished(event -> this.remove());
    }

    @FXML
    public void actionBack(ActionEvent event) {
        if (x > 0) this.hide(); else this.remove();
    }

    public void remove() {
        ((Pane)parent.getParent()).getChildren().remove(parent);
    }

    public VacancyViewController getVacancyViewController() {
        return (VacancyViewController) getControllerProvider().get(VacancyViewController.class.getName());
    }

    public VacancyEditController getVacancyEditController() {
        return (VacancyEditController) getControllerProvider().get(VacancyEditController.class.getName());
    }

    public ControllerProvider getControllerProvider() {
        return controllerProvider;
    }


}
