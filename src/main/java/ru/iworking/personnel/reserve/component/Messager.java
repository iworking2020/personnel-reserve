package ru.iworking.personnel.reserve.component;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Messager extends BorderPane {

    public enum MESSAGE_POSITION {TOP}

    private Pane topMessagePane;
    private Label topLabel;
    private Button buttonOk;

    private double y_TopMessagePane =  0.00;

    private Messager() {
        hide();
        this.init();
    }

    private static Messager instance;

    public static Messager getInstance() {
        if (instance == null) {
            synchronized (Messager.class) {
                if (instance == null) {
                    instance = new Messager();
                }
            }
        }
        return instance;
    }

    public void init() {
        initTopMessagePane();
        setTop(topMessagePane);
    }

    public void initTopMessagePane() {
        topLabel= new Label();
        BorderPane.setAlignment(topLabel, Pos.CENTER);

        buttonOk = new Button("ок");
        buttonOk.setOnAction(event -> hidePanes());
        BorderPane.setAlignment(buttonOk, Pos.CENTER);

        topMessagePane = new BorderPane();
        topMessagePane.setMinHeight(40);
        topMessagePane.setPrefHeight(40);
        topMessagePane.setMaxHeight(40);

        ((BorderPane)topMessagePane).setCenter(topLabel);
        ((BorderPane)topMessagePane).setRight(buttonOk);

        topMessagePane.getStyleClass().add("message-pane");

        setYPosTopMessagePane(0-topMessagePane.getPrefHeight());
    }

    public void sendMessageWithAction(String text, MessageAction messageAction) {
        this.sendMessage(text, null);
        buttonOk.setOnAction(event -> {
            hidePanes();
            messageAction.action();
        });
    }

    public void sendMessage(String text) {
        this.sendMessage(text, null);
    }

    public void sendMessage(String text, MESSAGE_POSITION position) {
        if (position == null || position == MESSAGE_POSITION.TOP) sendTopMessage(text);
        show();
        showTopMessagePane();
    }

    private void sendTopMessage(String text) {
        topLabel.setText(text);
    }

    public void setYPosTopMessagePane(double y) {
        this.y_TopMessagePane = y;
        topMessagePane.translateYProperty().set(y);
    }

    public void showTopMessagePane() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(topMessagePane.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(350), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private interface Function {
        void execute();
    }

    public void hideTopMessagePane(Function function) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(topMessagePane.translateYProperty(), y_TopMessagePane, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(350), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        if (function != null) timeline.setOnFinished(event -> function.execute());
    }

    public void show() {
        setVisible(true);
        setManaged(true);
    }

    public void hidePanes() {
        hideTopMessagePane(this::hide);
    }

    public void hide() {
        setVisible(false);
        setManaged(false);
    }

}
