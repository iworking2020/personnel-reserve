package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.entity.Resume;

public class ResumeCell extends ListCell<Resume> {

    @Getter @Setter
    private Resume resume;

    @Override
    protected void updateItem(Resume resume, boolean empty) {
        this.resume = resume;
        super.updateItem(resume, empty);
        if(empty || resume == null) {
            setText(null);
            setGraphic(null);
        } else {
            ResumePane resumePane = new ResumePane();
            resumePane.setData(resume);
            setText(null);
            setGraphic(resumePane);
        }
    }



}
