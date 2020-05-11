package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.PhotoService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ResumeCell extends ListCell<Resume> implements Initializable {

    private final PhotoService photoService = PhotoService.INSTANCE;

    @FXML private Pane parent;

    @FXML private Label fioLabel;
    @FXML private Label professionLabel;

    @FXML private ImageView imageView;

    private Resume resume;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    protected void updateItem(Resume resume, boolean empty) {
        this.resume = resume;
        super.updateItem(resume, empty);
        if(empty || resume == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/ResumeCell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            fioLabel.setText(resume.getProfile().getFullName());
            professionLabel.setText(resume.getProfession());

            if (resume.getPhotoId() != null) {
                setLogoImageById(resume.getPhotoId());
            } else {
                setDefaultImage();
            }

            setText(null);
            setGraphic(parent);
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
