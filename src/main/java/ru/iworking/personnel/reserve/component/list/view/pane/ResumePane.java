package ru.iworking.personnel.reserve.component.list.view.pane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumePane extends HBox implements Initializable {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter @FXML private Pane parentPane;

    @Getter @FXML private Label fioLabel;
    @Getter @FXML private Label professionLabel;

    @Getter @FXML private Pane wrapperImage;
    @Getter @FXML private ImageView imageView;

    public ResumePane() {
        FXMLUtil.load("/fxml/components/ResumePane.fxml", this, this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        wrapperImage.setClip(clip);
    }

}
