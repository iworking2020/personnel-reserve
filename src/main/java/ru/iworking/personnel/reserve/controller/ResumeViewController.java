package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.auth.api.model.IProfile;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.resume.api.model.IResume;
import ru.iworking.vacancy.api.model.IVacancy;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumeViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ResumeViewController.class);

    @FXML private Pane resumePaneView;

    @FXML private Label lastNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label middleNameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        resumePaneView.setVisible(true);
    }

    public void hide() {
        resumePaneView.setVisible(false);
    }

    public void setData(Resume resume) {
        if (resume != null) {
            IProfile profile = resume.getProfile();
            if (profile != null) {
                lastNameLabel.setText(profile.getLastName());
                firstNameLabel.setText(profile.getFirstName());
                middleNameLabel.setText(profile.getMiddleName());
            }
        } else {
            logger.debug("Resume is null. We can't view resume...");
        }
    }
}
