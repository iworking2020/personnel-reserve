package ru.iworking.personnel.reserve.controller;

import java.util.HashMap;

public class ControllersPool extends HashMap<Class, Object> {

    private static volatile ControllersPool instance;

    public static ControllersPool getInstance() {
        ControllersPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ControllersPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ControllersPool();
                }
            }
        }
        return localInstance;
    }
    
}
