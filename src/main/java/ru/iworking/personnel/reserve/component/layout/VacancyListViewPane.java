package ru.iworking.personnel.reserve.component.layout;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

public class VacancyListViewPane extends BorderPane {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter @FXML private Pane parentPane;

    @Getter @FXML private Button backButton;
    @Getter @FXML private Button addButton;
    @Getter @FXML private Button updateButton;

    @Getter @FXML private ListView<Vacancy> vacancyListView;

    public VacancyListViewPane() {
        FXMLUtil.load("/fxml/components/VacancyListViewPane.fxml", this, this);
    }

}
