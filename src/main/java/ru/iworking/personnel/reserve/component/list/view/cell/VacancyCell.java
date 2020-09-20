package ru.iworking.personnel.reserve.component.list.view.cell;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.controller.VacancyCellController;
import ru.iworking.personnel.reserve.component.list.view.factory.VacancyCellControllerFactory;
import ru.iworking.personnel.reserve.component.list.view.pane.VacancyPane;
import ru.iworking.personnel.reserve.entity.Vacancy;

@RequiredArgsConstructor
public class VacancyCell extends ListCell<Vacancy> {

    private final VacancyCellControllerFactory vacancyCellControllerFactory;

    @Getter private VacancyPane vacancyPane;
    @Getter private VacancyCellController vacancyCellController;

    @Override
    protected void updateItem(Vacancy vacancy, boolean empty) {
        super.updateItem(vacancy, empty);
        if(empty || vacancy == null) {
            this.vacancyPane = null;
            this.vacancyCellController = null;
            setText(null);
            setGraphic(null);
        } else {
            vacancyPane = new VacancyPane();
            vacancyCellController = vacancyCellControllerFactory.create(vacancyPane);
            vacancyCellController.setData(vacancy);
            setText(null);
            setGraphic(vacancyPane);
        }
    }

}
