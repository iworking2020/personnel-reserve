package ru.iworking.personnel.reserve.component;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.config.GuiceConfig;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.PhotoService;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ResumePane extends HBox implements Initializable {

    @Inject private PhotoService photoService;

    @FXML private Pane parent;

    @FXML private Label fioLabel;
    @FXML private Label professionLabel;

    @FXML private Pane wrapperImage;
    @FXML private ImageView imageView;

    @Getter
    @Setter
    private Resume resume;

    public ResumePane() {
        GuiceConfig.INJECTOR.injectMembers(this);

        FXMLUtil.load("/fxml/components/ResumePane.fxml", this, this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        wrapperImage.setClip(clip);
    }

    public void setData(Resume resume) {
        this.resume = resume;
        fioLabel.setText(resume.getProfile().getFullName());
        professionLabel.setText(resume.getProfession());

        if (resume.getPhotoId() != null) {
            setLogoImageById(resume.getPhotoId());
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



}
