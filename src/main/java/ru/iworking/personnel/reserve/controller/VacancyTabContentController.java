package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class VacancyTabContentController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacancyTabContentController.class);

    @FXML private ScrollPane resumesAccordionWrapper;
    @FXML private ScrollPane clientListViewWrapper;

    @FXML private ScrollPane clientScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isResizable(false);
    }

    public void isResizable(boolean isResizable) {
        if (isResizable) {
            clientListViewWrapper.setMinWidth(0.00);
            resumesAccordionWrapper.setMinWidth(0.00);
        } else {
            final double maxWidth1 = clientListViewWrapper.getMaxWidth();
            clientListViewWrapper.setMinWidth(maxWidth1);
            final double maxWidth2 = resumesAccordionWrapper.getMaxWidth();
            resumesAccordionWrapper.setMinWidth(maxWidth2);
        }
    }

    public ScrollPane getClientListViewWrapper() {
        return clientListViewWrapper;
    }

    public void downClientSrollPane() {
        clientScrollPane.setVvalue(1.0);
    }
}
