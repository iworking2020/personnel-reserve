package ru.iworking.personnel.reserve.utils.db;

public class PersistenceSeparator {

    private int index = 0;

    public PersistenceSeparator() {}

    public String get() {
        if (index == 0) {
            index++;
            return " WHERE";
        } else {
            return " AND";
        }
    }

}
