package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.service.VacancyService;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VacancyPane extends HBox implements Initializable {

    @FXML
    private Pane parent;

    @FXML private ImageView imageView;
    @FXML private Label professionLabel;

    @Getter @Setter
    private Vacancy vacancy;

    @Autowired private VacancyService vacancyService;
    @Autowired private ImageContainerService imageContainerService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        imageView.setClip(clip);
    }

    public VacancyPane() {
        FXMLUtil.load("/fxml/components/VacancyPane.fxml", this, this);
    }

    public void setData(Vacancy vacancy) {
        professionLabel.setText(vacancy.getProfession());

        if (vacancy.getPhotoId() != null) {
            setLogoImageById(vacancy.getPhotoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        ImageContainer logo = imageContainerService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        imageView.setImage(img);
    }

    public void setDefaultImage() {
        javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                getClass().getClassLoader().getResourceAsStream("images/default-vacancy.jpg"),
                150,
                150,
                false,
                false);
        imageView.setImage(defaultImage);
    }

}
