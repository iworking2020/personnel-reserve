package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.PhotoService;

public class ResumeCell extends ListCell<Resume> {

    private final PhotoService photoService;

    @Getter @Setter
    private Resume resume;

    public ResumeCell(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Override
    protected void updateItem(Resume resume, boolean empty) {
        this.resume = resume;
        super.updateItem(resume, empty);
        if(empty || resume == null) {
            setText(null);
            setGraphic(null);
        } else {
            ResumePane resumePane = new ResumePane(photoService);
            resumePane.setData(resume);
            setText(null);
            setGraphic(resumePane);
        }
    }



}
