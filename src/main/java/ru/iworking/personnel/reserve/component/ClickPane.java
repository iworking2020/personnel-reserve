package ru.iworking.personnel.reserve.component;

import com.google.common.base.Strings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.springframework.context.ApplicationContext;
import ru.iworking.personnel.reserve.ApplicationContextProvider;
import ru.iworking.personnel.reserve.controller.ClickListViewController;
import ru.iworking.personnel.reserve.controller.ControllerProvider;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.ResumeState;
import ru.iworking.personnel.reserve.model.ResumeStateCellFactory;
import ru.iworking.personnel.reserve.service.ClickService;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.service.ResumeStateService;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ClickPane extends FlowPane implements Initializable {

    private final ControllerProvider controllerProvider = ControllerProvider.getInstance();

    private final ImageContainerService imageContainerService;
    private final ResumeStateService resumeStateService;
    private final ClickService clickService;

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

    public ClickPane() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        this.imageContainerService = context.getBean(ImageContainerService.class);
        this.resumeStateService = context.getBean(ResumeStateService.class);
        this.clickService = context.getBean(ClickService.class);
        FXMLUtil.load("/fxml/components/ClickPane.fxml", this, this);
    }

    public void setData(Click click) {
        this.click = click;
        fioLabel.setText(click.getResume().getProfile().getFullName());
        if (!Strings.isNullOrEmpty(click.getResume().getProfession())) {
            professionLabel.setText(click.getResume().getProfession());
        } else {
            professionLabel.setText("не указана");
        }
        resumeStateComboBox.setValue(click.getResumeState());
        if (click.getResume().getPhotoId() != null) {
            setLogoImageById(click.getResume().getPhotoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        ImageContainer imageContainer = imageContainerService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(imageContainer.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        imageView.setImage(img);
    }

    public void setDefaultImage() {
        javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
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
