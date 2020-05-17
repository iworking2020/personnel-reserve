package ru.iworking.personnel.reserve.component;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.iworking.personnel.reserve.controller.ClickListViewController;
import ru.iworking.personnel.reserve.controller.ControllerProvider;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.entity.ResumeState;
import ru.iworking.personnel.reserve.model.ResumeStateCellFactory;
import ru.iworking.personnel.reserve.service.ClickService;
import ru.iworking.personnel.reserve.service.PhotoService;
import ru.iworking.personnel.reserve.service.ResumeStateService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ClickItem extends FlowPane implements Initializable {

    private final ControllerProvider controllerProvider = ControllerProvider.getInstance();

    private final PhotoService photoService = PhotoService.INSTANCE;
    private final ResumeStateService resumeStateService = ResumeStateService.INSTANCE;
    private final ClickService clickService = ClickService.INSTANCE;

    private final ResumeStateCellFactory resumeStateCellFactory = new ResumeStateCellFactory();

    @FXML private Pane parent;

    @FXML private Label fioLabel;
    @FXML private Label professionLabel;

    @FXML private Pane wrapperImage;
    @FXML private ImageView imageView;
    @FXML private ComboBox<ResumeState> resumeStateComboBox;

    private Click click;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        wrapperImage.setClip(clip);

        resumeStateComboBox.setButtonCell(resumeStateCellFactory.call(null));
        resumeStateComboBox.setCellFactory(resumeStateCellFactory);
        resumeStateComboBox.setItems(FXCollections.observableList(resumeStateService.findAll()));
        resumeStateComboBox.setOnAction(event -> {
            click.setResumeState(resumeStateComboBox.getValue());
            clickService.update(click);
        });
    }

    public ClickItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/ClickItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setData(Click click) {
        this.click = click;
        fioLabel.setText(click.getResume().getProfile().getFullName());
        professionLabel.setText(click.getResume().getProfession());
        resumeStateComboBox.setValue(click.getResumeState());
        if (click.getResume().getPhotoId() != null) {
            setLogoImageById(click.getResume().getPhotoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        Photo photo = photoService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(photo.getImage());
        Image img = new Image(targetStream);
        imageView.setImage(img);
    }

    public void setDefaultImage() {
        Image defaultImage = new Image(
                getClass().getClassLoader().getResourceAsStream("images/default-resume.jpg"),
                150,
                150,
                false,
                false);
        imageView.setImage(defaultImage);
    }

    @FXML
    private void actionUnfasten() {
        getClickListViewController().unfastenItem(click);
    }

    public ClickListViewController getClickListViewController() {
        return (ClickListViewController) getControllerProvider().get(ClickListViewController.class.getName());
    }

    public ControllerProvider getControllerProvider() {
        return controllerProvider;
    }

}
