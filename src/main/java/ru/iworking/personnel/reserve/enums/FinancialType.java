package ru.iworking.personnel.reserve.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum FinancialType {
    COMMERCIAL,
    NOT_COMMERCIAL;

    private FinancialType() { }

    public static FinancialType getEnum(String value) {
        AtomicReference<FinancialType> returnedObject = new AtomicReference();
        returnedObject.set((FinancialType)null);
        if (value == null) {
            return null;
        } else {
            Arrays.stream(values()).forEach((type) -> {
                if (type.name().equals(value.toLowerCase()) || type.name().equals(value.toUpperCase())) {
                    returnedObject.set(type);
                }

            });
            return (FinancialType)returnedObject.get();
        }
    }
}
