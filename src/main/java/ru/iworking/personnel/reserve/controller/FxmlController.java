package ru.iworking.personnel.reserve.controller;

import javafx.fxml.Initializable;

public abstract class FxmlController implements Initializable {

    private ControllerProvider controllerProvider = ControllerProvider.getInstance();

    public ControllerProvider getControllerProvider() {
        return controllerProvider;
    }

    public FxmlController() {
        controllerProvider.put(this.getClass().getName(), this);
    }

}
