package ru.iworking.personnel.reserve.component;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.entity.Vacancy;

public class VacancyCell extends ListCell<Vacancy> {

    @Getter @Setter
    private Vacancy vacancy;

    @Override
    protected void updateItem(Vacancy vacancy, boolean empty) {
        this.vacancy = vacancy;
        super.updateItem(vacancy, empty);
        if(empty || vacancy == null) {
            setText(null);
            setGraphic(null);
        } else {
            VacancyPane vacancyPane = new VacancyPane();
            vacancyPane.setData(vacancy);
            setText(null);
            setGraphic(vacancyPane);
        }
    }

}
