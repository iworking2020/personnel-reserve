package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.auth.api.model.IProfile;
import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.model.AppFunctionalInterface;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ResumeViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ResumeViewController.class);

    @FXML private Pane resumePaneView;

    @FXML private Label lastNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label middleNameLabel;

    @FXML private ImageView photoImageView;

    @FXML private Button buttonCancel;
    public Button getButtonCancel() {
        return buttonCancel;
    }

    private PhotoDao photoDao = PhotoDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        resumePaneView.setVisible(true);
        resumePaneView.setManaged(true);
    }

    public void show(AppFunctionalInterface function) {
        function.execute();
        this.show();
    }

    public void hide() {
        resumePaneView.setVisible(false);
        resumePaneView.setManaged(false);
    }

    public void hide(AppFunctionalInterface function) {
        function.execute();
        this.hide();
    }

    public void setData(Resume resume) {
        if (resume != null) {
            IProfile profile = resume.getProfile();
            if (profile != null) {
                lastNameLabel.setText(profile.getLastName());
                firstNameLabel.setText(profile.getFirstName());
                middleNameLabel.setText(profile.getMiddleName());
            }
            if (resume.getPhotoId() != null) {
                Photo photo = photoDao.findFromCash(resume.getPhotoId());
                InputStream targetStream = new ByteArrayInputStream(photo.getImage());
                Image img = new Image(targetStream);
                photoImageView.setImage(img);
            } else {
                Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("images/default.resume.jpg"));
                photoImageView.setImage(defaultImage);
            }
        } else {
            logger.debug("Resume is null. We can't view resume...");
        }
    }
}
