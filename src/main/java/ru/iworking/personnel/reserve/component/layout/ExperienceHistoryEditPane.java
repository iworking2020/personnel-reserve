package ru.iworking.personnel.reserve.component.layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;
import ru.iworking.personnel.reserve.entity.ExperienceHistory;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.util.Objects;


public class ExperienceHistoryEditPane extends VBox {

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

    public ExperienceHistoryEditPane() {
        FXMLUtil.load("/fxml/ExperienceHistoryEditBlock.fxml", this, this);
        initData();
    }

    public void initData() { }

    public void setExperienceHistory(ExperienceHistory experienceHistory) {
        experienceHistoryId = experienceHistory.getId();
        if (Objects.nonNull(experienceHistory.getDateStart())) {
            LocalDate startJoda =  experienceHistory.getDateStart();
            dateStartDatePicker.setValue(java.time.LocalDate.of(startJoda.getYear(), startJoda.getMonthOfYear(), startJoda.getDayOfMonth()));
        }
        if (Objects.nonNull(experienceHistory.getDateEnd())) {
            LocalDate endJoda = experienceHistory.getDateEnd();
            dateEndDatePicker.setValue(java.time.LocalDate.of(endJoda.getYear(), endJoda.getMonthOfYear(), endJoda.getDayOfMonth()));
        }
        editableText.setText(experienceHistory.getDescription());
    }

    public ExperienceHistory getExperienceHistory() {
        java.time.LocalDate start = dateStartDatePicker.getValue();
        java.time.LocalDate end  = dateEndDatePicker.getValue();
        ExperienceHistory experienceHistory = new ExperienceHistory();
        experienceHistory.setId(experienceHistoryId);
        if (Objects.nonNull(start)) {
            final LocalDate localDate = new LocalDate(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
            experienceHistory.setDateStart(localDate);
        }
        if (Objects.nonNull(end)) {
            final LocalDate localDate = new LocalDate(end.getYear(), end.getMonthValue(), end.getDayOfMonth());
            experienceHistory.setDateEnd(localDate);
        }
        experienceHistory.setDescription(editableText.getText());
        return experienceHistory;
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        ((Pane)parent.getParent()).getChildren().remove(parent);
    }

}
