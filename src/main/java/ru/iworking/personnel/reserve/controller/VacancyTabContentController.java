package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class VacancyTabContentController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(VacancyTabContentController.class);

    @FXML private Pane wrapperResume;

    @FXML private ClientListViewController clientListViewController;
    @FXML private CompanyViewController companyViewController;
    @FXML private CompanyEditController companyEditController;
    @FXML private VacancyViewController vacancyViewController;
    @FXML private VacancyEditController vacancyEditController;
    @FXML private ResumeViewController resumeViewController;
    @FXML private ResumeEditController resumeEditController;

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