package ru.iworking.personnel.reserve.component;

import javafx.scene.control.TitledPane;
import ru.iworking.personnel.reserve.entity.ResumeState;
import ru.iworking.personnel.reserve.service.ResumeService;

public class ResumeStateTitledPane extends TitledPane {

    private ResumeState resumeState;

    private final ResumeService resumeService = ResumeService.INSTANCE;

    public ResumeStateTitledPane(ResumeState resumeState) {
        super();
        this.resumeState = resumeState;
        this.initText();
    }

    public void initText() {
        Long count = resumeService.countByResumeStateId(resumeState.getId());
        this.setText(resumeState.getNameView().getName() + " (" + count + ")");
    }

    public ResumeState getResumeState() {
        return resumeState;
    }
    public void setResumeState(ResumeState resumeState) {
        this.resumeState = resumeState;
    }

}
