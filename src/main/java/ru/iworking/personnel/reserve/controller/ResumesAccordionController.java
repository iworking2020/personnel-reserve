package ru.iworking.personnel.reserve.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.ResumeService;
import ru.iworking.personnel.reserve.service.ResumeStateService;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumesAccordionController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(ResumesAccordionController.class);

    private ResumeStateService resumeStateService = ResumeStateService.INSTANCE;
    private ResumeService resumeService = ResumeService.INSTANCE;

    @FXML private Accordion resumesAccordion;

    @FXML private Button updateButton;

    class ResumeButton extends Button {

        private Long resumeId;

        public Long getResumeId() {
            return resumeId;
        }
        public void setResumeId(Long resumeId) {
            this.resumeId = resumeId;
        }

        public ResumeButton(String text, Long resumeId) {
            super(text);
            this.resumeId = resumeId;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }

    public void initData() {
        resumesAccordion.getPanes().removeAll(resumesAccordion.getPanes());
        resumeStateService.findAll().forEach(resumeState -> {
            Long count = resumeService.countByResumeStateId(resumeState.getId());

            TitledPane titledPane = new TitledPane();
            titledPane.setText(resumeState.getNameView().getName() + " (" + count + ")");

            VBox vBoxResumes = new VBox();
            resumeService.findAllByResumeStateId(resumeState.getId()).forEach(resume -> {
                if (resume.getProfile() != null) {

                    ResumeButton button = new ResumeButton(resume.getProfile().getFullName(), resume.getId());
                    button.getStyleClass().add("invisible");
                    button.setOnAction(event -> {
                        Resume resume1 = resumeService.findById(button.getResumeId());
                        if (resume1 != null) {
                            getResumeViewController().setData(resume);
                            getResumeViewController().show();
                            getResumeEditController().hide();
                            getResumeEditController().clear();
                            getVacanciesPaneController().hideWrapperClient();
                        }
                    });

                    AnchorPane wrapper = new AnchorPane(button);
                    AnchorPane.setLeftAnchor(button, 0.00);
                    AnchorPane.setRightAnchor(button, 0.00);

                    vBoxResumes.getChildren().add(wrapper);
                }
            });

            ScrollPane scrollPane = new ScrollPane(vBoxResumes);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            titledPane.setContent(scrollPane);
            resumesAccordion.getPanes().add(titledPane);
        });
    }

    @FXML
    public void actionUpdate(ActionEvent event) {
        getResumeViewController().hide();
        getResumeEditController().hide();
        getResumeEditController().clear();
        reload();
        getVacanciesPaneController().showWrapperClient();
    }

    public void reload() {
        initData();
    }

    public ResumeEditController getResumeEditController() {
        return (ResumeEditController) getControllerProvider().get(ResumeEditController.class.getName());
    }

    public ResumeViewController getResumeViewController() {
        return (ResumeViewController) getControllerProvider().get(ResumeViewController.class.getName());
    }

    public VacanciesPaneController getVacanciesPaneController() {
        return (VacanciesPaneController) getControllerProvider().get(VacanciesPaneController.class.getName());
    }
}
