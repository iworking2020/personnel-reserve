package ru.iworking.personnel.reserve.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.entity.ExperienceHistory;

import java.io.IOException;


public class ExperienceHistoryEditBlock extends VBox {

    @FXML private Pane parent;

    @Getter
    @Setter
    @FXML private DatePicker dateStartDatePicker;
    @Getter
    @Setter
    @FXML private DatePicker dateEndDatePicker;
    @Getter
    @Setter
    @FXML private TextArea editableText;
    @Getter
    @Setter
    private Long experienceHistoryId;

    public ExperienceHistoryEditBlock() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExperienceHistoryEditBlock.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initData();
    }

    public void initData() { }

    public void setExperienceHistory(ExperienceHistory experienceHistory) {
        experienceHistoryId = experienceHistory.getId();
        dateStartDatePicker.setValue(experienceHistory.getDateStart());
        dateEndDatePicker.setValue(experienceHistory.getDateEnd());
        editableText.setText(experienceHistory.getDescription());
    }

    public ExperienceHistory getExperienceHistory() {
        return ExperienceHistory.builder()
                .id(experienceHistoryId)
                .dateStart(dateStartDatePicker.getValue())
                .dateEnd(dateEndDatePicker.getValue())
                .description(editableText.getText())
                .build();
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        ((Pane)parent.getParent()).getChildren().remove(parent);
    }

}
