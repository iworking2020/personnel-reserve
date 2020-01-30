package ru.iworking.personnel.reserve.model;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NumberPhoneFormatter extends TextFormatter {

    private static final Pattern pattern = Pattern.compile("^\\+?\\d*");

    public NumberPhoneFormatter() {
        super( (UnaryOperator<Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
    }

}
