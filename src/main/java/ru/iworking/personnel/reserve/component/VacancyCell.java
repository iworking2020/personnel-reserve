package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.iworking.personnel.reserve.entity.Logo;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.service.LogoService;
import ru.iworking.personnel.reserve.service.VacancyService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VacancyCell extends ListCell<Vacancy> implements Initializable {

    @FXML private Pane parent;

    @FXML private ImageView imageView;
    @FXML private Label professionLabel;

    private Vacancy vacancy;

    private final VacancyService vacancyService = VacancyService.INSTANCE;
    private final LogoService logoService = LogoService.INSTANCE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        imageView.setClip(clip);
    }

    @Override
    protected void updateItem(Vacancy vacancy, boolean empty) {
        this.vacancy = vacancy;
        super.updateItem(vacancy, empty);
        if(empty || vacancy == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/VacancyCell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            professionLabel.setText(vacancy.getProfession());

            if (vacancy.getLogoId() != null) {
                setLogoImageById(vacancy.getLogoId());
            } else {
                setDefaultImage();
            }

            setText(null);
            setGraphic(parent);
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

    public Vacancy getVacancy() {
        return vacancy;
    }
}
