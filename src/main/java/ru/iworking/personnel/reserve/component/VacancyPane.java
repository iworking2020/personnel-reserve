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
import ru.iworking.personnel.reserve.entity.Logo;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.service.LogoService;
import ru.iworking.personnel.reserve.service.VacancyService;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VacancyPane extends HBox implements Initializable {

    @FXML private Pane parent;

    @FXML private ImageView imageView;
    @FXML private Label professionLabel;

    @Getter @Setter
    private Vacancy vacancy;

    @Inject private VacancyService vacancyService;
    @Inject private LogoService logoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GuiceConfig.INJECTOR.injectMembers(this);

        final Circle clip = new Circle(30, 28, 28);
        imageView.setClip(clip);
    }

    public VacancyPane() {
        FXMLUtil.load("/fxml/components/VacancyPane.fxml", this, this);
    }

    public void setData(Vacancy vacancy) {
        professionLabel.setText(vacancy.getProfession());

        if (vacancy.getLogoId() != null) {
            setLogoImageById(vacancy.getLogoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        Logo logo = logoService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        Image img = new Image(targetStream);
        imageView.setImage(img);
    }

    public void setDefaultImage() {
        Image defaultImage = new Image(
                getClass().getClassLoader().getResourceAsStream("images/default-vacancy.jpg"),
                150,
                150,
                false,
                false);
        imageView.setImage(defaultImage);
    }

}
