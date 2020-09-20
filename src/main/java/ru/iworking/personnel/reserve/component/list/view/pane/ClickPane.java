package ru.iworking.personnel.reserve.component.list.view.pane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.iworking.personnel.reserve.entity.ResumeState;
import ru.iworking.personnel.reserve.model.ResumeStateCellFactory;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ClickPane extends FlowPane implements Initializable {

    private final ResumeStateCellFactory resumeStateCellFactory = new ResumeStateCellFactory();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter @FXML private Pane parentPane;

    @Getter @FXML private Label fioLabel;
    @Getter @FXML private Label professionLabel;

    @Getter @FXML private Pane wrapperImage;
    @Getter @FXML private ImageView imageView;
    @Getter @FXML private ComboBox<ResumeState> resumeStateComboBox;

    @Getter @FXML private Button buttonUnfasten;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        wrapperImage.setClip(clip);
        resumeStateComboBox.setButtonCell(resumeStateCellFactory.call(null));
        resumeStateComboBox.setCellFactory(resumeStateCellFactory);

    }

    public ClickPane() {
        FXMLUtil.load("/fxml/components/ClickPane.fxml", this, this);
    }



}
