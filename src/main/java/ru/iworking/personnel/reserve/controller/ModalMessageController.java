package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationJavaFX;
import ru.iworking.personnel.reserve.utils.AppUtil;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ModalMessageController implements Initializable {

    static final Logger logger = LogManager.getLogger(ModalMessageController.class);

    private String message = "";
    private String title = "";
    private String nameButton = "";

    @FXML private Button buttonOk;
    @FXML private Label labelMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameButton() {
        return nameButton;
    }

    public void setNameButton(String nameButton) {
        this.nameButton = nameButton;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void actionButtonOk(ActionEvent event) {
        this.closeStage(event);
    }

    public void showAndWait(Parent parent) {
        Stage primaryStage = ApplicationJavaFX.PARENT_STAGE;

        labelMessage.setText(message);
        buttonOk.setText(nameButton);

        Scene scene = new Scene(parent);
        scene.getStylesheets().add("/styles/main.css");
        scene.getStylesheets().add("/styles/modal.css");
        scene.getStylesheets().add("/styles/button.css");

        Stage modal = new Stage();
        modal.setTitle(title);
        AppUtil.setIcon(modal);
        modal.setScene(scene);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(primaryStage);
        modal.setResizable(false);
        modal.showAndWait();
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
