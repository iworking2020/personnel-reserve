package ru.iworking.personnel.reserve.component.list.view.cell;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.controller.ResumeCellController;
import ru.iworking.personnel.reserve.component.list.view.factory.ResumeCellControllerFactory;
import ru.iworking.personnel.reserve.component.list.view.pane.ResumePane;
import ru.iworking.personnel.reserve.entity.Resume;

@RequiredArgsConstructor
public class ResumeCell extends ListCell<Resume> {

    private final ResumeCellControllerFactory resumeCellControllerFactory;

    @Getter private ResumePane resumePane;
    @Getter private ResumeCellController resumeCellController;

    @Override
    protected void updateItem(Resume resume, boolean empty) {
        super.updateItem(resume, empty);
        if(empty || resume == null) {
            this.resumePane = null;
            this.resumeCellController = null;
            setText(null);
            setGraphic(null);
        } else {
            resumePane = new ResumePane();
            resumeCellController = resumeCellControllerFactory.create(resumePane);
            resumeCellController.setData(resume);
            setText(null);
            setGraphic(resumePane);
        }
    }



}
