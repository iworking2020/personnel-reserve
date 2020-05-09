package ru.iworking.personnel.reserve.component;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VacancyListViewPane extends BorderPane implements Initializable {

    @FXML
    private Pane parent;

    @FXML
    private Button backButton;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setText("");
    }

    @FXML
    public void actionBack(ActionEvent event) {
        if (x > 0) this.hide(); else this.remove();
    }

    public void remove() {
        ((Pane)parent.getParent()).getChildren().remove(parent);
    }

}
