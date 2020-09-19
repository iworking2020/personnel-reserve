package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

@Data
public class VacancyListViewPane extends BorderPane {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @FXML private Pane parentPane;

    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private Button updateButton;

    @FXML private ListView<Vacancy> vacancyListView;

    public VacancyListViewPane() {
        FXMLUtil.load("/fxml/components/VacancyListViewPane.fxml", this, this);
    }

}
