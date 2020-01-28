package ru.iworking.personnel.reserve.model;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class BigDecimalFormatter extends TextFormatter {

    private static final Pattern pattern = Pattern.compile("\\d*|\\d+\\,\\d{2}");

    public BigDecimalFormatter() {
        super( (UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
    }
}
