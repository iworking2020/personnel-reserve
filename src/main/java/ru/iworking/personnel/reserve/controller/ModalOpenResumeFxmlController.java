package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.MainApp;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.utils.TextUtils;
import ru.iworking.service.api.utils.LocaleUtils;
import ru.iworking.service.api.utils.TimeUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ModalOpenResumeFxmlController implements Initializable {

    static final Logger logger = LogManager.getLogger(ModalOpenResumeFxmlController.class);

    @FXML private Label lastNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label middleNameLabel;
    @FXML private Label numberPhoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label professionLabel;
    @FXML private Label profFieldLabel;
    @FXML private Label wageLabel;
    @FXML private Label workTypeLabel;
    @FXML private Label educationLabel;
    @FXML private Label experienceLabel;
    @FXML private Label addressLabel;

    private Resume resume;

    public void setResume(Resume resume) {
        this.resume = resume;
        this.setValues(this.resume);
    }

    public void setValues(Resume resume) {
        lastNameLabel.setText(resume.getLastName());
        firstNameLabel.setText(resume.getFirstName());
        middleNameLabel.setText(resume.getMiddleName());
        numberPhoneLabel.setText(resume.getNumberPhone());
        emailLabel.setText(resume.getEmail());
        professionLabel.setText(resume.getProfession());
        if (resume.getProfField() != null) {
            profFieldLabel.setText(resume.getProfField().getNameToView(LocaleUtils.getDefault()));
        } else {
            profFieldLabel.setText("не указана");
        }
        String wageString = resume.getCurrency() != null ?
                resume.getWage() + " " + resume.getCurrency().getNameToView(LocaleUtils.getDefault()) :
                resume.getWage();
        wageString = wageString.length() > 0 ? wageString : "не указана";
        wageLabel.setText(wageString);
        if (resume.getWorkType() != null) {
            workTypeLabel.setText(resume.getWorkType().getNameToView(LocaleUtils.getDefault()));
        } else {
            workTypeLabel.setText("не указан");
        }
        if (resume.getEducation() != null) {
            educationLabel.setText(resume.getEducation().getNameToView(LocaleUtils.getDefault()));
        } else {
            educationLabel.setText("не указано");
        }

        Integer age = TimeUtils.calAge(resume.getExperience().getDateStart(), resume.getExperience().getDateEnd());
        
        experienceLabel.setText(age == null || age <= 0 ? "без опыта" : age + " " + TextUtils.nameForNumbers(age));
        addressLabel.setText(resume.getAddress());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void showAndWait(Parent parent) {
        Stage primaryStage = MainApp.PARENT_STAGE;

        Scene scene = new Scene(parent);
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/button.css");
        scene.getStylesheets().add("/styles/modal.css");

        Stage modal = new Stage();
        modal.setTitle("Открыть");
        try {
            modal.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        } catch (Exception ex) {
            logger.error("Не удалось загрузить иконку приложения ...", ex);
        }
        modal.setScene(scene);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(primaryStage);
        modal.showAndWait();
    }

    @FXML
    private void actionButtonCancel(ActionEvent event) {
        this.closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    

}
