package ru.iworking.personnel.reserve.controller;

import java.util.HashMap;

public class ControllerProvider extends HashMap<Class<?>, Object> {

    private static volatile ControllerProvider instance;

    public static ControllerProvider getInstance() {
        ControllerProvider localInstance = instance;
        if (localInstance == null) {
            synchronized (ControllerProvider.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ControllerProvider();
                }
            }
        }
        return localInstance;
    }

    private ControllerProvider() {}

}
