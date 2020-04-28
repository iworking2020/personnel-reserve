package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import ru.iworking.personnel.reserve.utils.FXMLUtils;

public class EducationEditBlock extends VBox {

    @FXML private TextArea upperText;

    public EducationEditBlock() {
        FXMLUtils.loadFXML(this, "/fxml/EducationEditBlock.fxml");
    }

}
