package ru.iworking.personnel.reserve.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.component.layout.VacancyListViewPane;
import ru.iworking.personnel.reserve.component.list.view.cell.VacancyCell;
import ru.iworking.personnel.reserve.component.list.view.factory.VacancyCellControllerFactory;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.service.VacancyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VacancyListViewController {

    private final VacancyService vacancyService;
    @Setter private VacancyListViewPane vacancyListViewPane;

    @Autowired @Lazy private VacancyViewController vacancyViewController;
    @Autowired @Lazy private VacancyEditController vacancyEditController;
    @Autowired @Lazy private CompanyViewController companyViewController;
    @Autowired @Lazy private ClickListViewController clickListViewController;
    @Autowired @Lazy private ResumeViewController resumeViewController;
    @Autowired @Lazy private VacancyCellControllerFactory vacancyCellControllerFactory;

    private double x = 0.00;

    public void setData(List<Vacancy> data) {
        vacancyListViewPane.getVacancyListView().setItems(FXCollections.observableList(data));
    }

    public void initData() {
        if (Objects.nonNull(companyViewController.getCurrentCompany())) {
            Company company = companyViewController.getCurrentCompany();
            List<Vacancy> list = vacancyService.findAllByCompany(company);
            vacancyListViewPane.getVacancyListView().setItems(FXCollections.observableList(list));
        }
    }

    private void initClickData(Vacancy vacancy) {
        resumeViewController.isDisableClickButton(false);
        List<Click> list = new ArrayList();
        if (vacancy != null && vacancy.getClicks() != null) list = vacancy.getClicks().stream()
                .filter(click -> Objects.nonNull(click.getResume()) && Objects.nonNull(click.getVacancy()))
                .collect(Collectors.toList());
        if (list.size() > 0) {
            clickListViewController.setData(list);
            clickListViewController.show();
        } else {
            clickListViewController.hide();
            clickListViewController.clear();
        }
    }

    public void init() {
        vacancyListViewPane.getVacancyListView().setCellFactory(listView -> {
            VacancyCell cell = new VacancyCell(vacancyCellControllerFactory);
            return cell;
        });
        vacancyListViewPane.getVacancyListView().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            vacancyEditController.clear();
            vacancyEditController.hide();
            vacancyViewController.setData(newSelection);
            vacancyViewController.show();
            this.initClickData(newSelection);
        });

        vacancyListViewPane.getBackButton().setText("");
        vacancyListViewPane.getUpdateButton().setText("");
        vacancyListViewPane.getAddButton().setText("");

        vacancyListViewPane.getBackButton().setOnAction(this::actionBack);
        vacancyListViewPane.getUpdateButton().setOnAction(this::actionUpdate);
        vacancyListViewPane.getAddButton().setOnAction(this::actionCreate);

        initData();
    }

    public void setXPosition(double x) {
        this.x = x;
        vacancyListViewPane.getParentPane().translateXProperty().set(x);
    }

    public void show() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(vacancyListViewPane.getParentPane().translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void hide() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(vacancyListViewPane.getParentPane().translateXProperty(), x, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        timeline.setOnFinished(event -> this.remove());
    }

    public void actionCreate(ActionEvent event) {
        vacancyEditController.clear();
        vacancyEditController.show();
    }

    public void actionBack(ActionEvent event) {
        if (Objects.nonNull(vacancyListViewPane)) {
            if (x > 0) this.hide(); else this.remove();
            vacancyEditController.hide();
            vacancyEditController.clear();
            vacancyViewController.hide();
            vacancyViewController.clear();
            clickListViewController.hide();
            clickListViewController.clear();
            resumeViewController.isDisableClickButton(true);
        }
    }

    public void actionUpdate(ActionEvent event) {
        this.initData();
        vacancyEditController.hide();

        if (Objects.nonNull(vacancyListViewPane)) {
            Vacancy vacancy = vacancyListViewPane.getVacancyListView().getSelectionModel().getSelectedItem();
            if (vacancy != null) {
                vacancyViewController.setData(vacancy);
                vacancyViewController.show();
                this.initClickData(vacancy);
            } else {
                vacancyViewController.hide();
            }
        }
    }

    public void select(Vacancy vacancy) {
        vacancyListViewPane.getVacancyListView().getSelectionModel().select(vacancy);
    }

    public void remove() {
        Pane parentPane = (Pane) vacancyListViewPane.getParentPane().getParent();
        if (Objects.nonNull(parentPane)) parentPane.getChildren().remove(vacancyListViewPane.getParentPane());
    }
}
