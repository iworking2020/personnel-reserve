package ru.iworking.personnel.reserve.component.list.view.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.pane.VacancyPane;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.service.ImageContainerServiceImpl;
import ru.iworking.personnel.reserve.service.VacancyServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RequiredArgsConstructor
public class VacancyCellController {

    private final VacancyServiceImpl vacancyService;
    private final ImageContainerServiceImpl imageContainerService;

    private final VacancyPane vacancyPane;
    @Getter private Vacancy vacancy;

    public void setData(Vacancy vacancy) {
        this.vacancy = vacancy;
        vacancyPane.getProfessionLabel().setText(vacancy.getProfession());
        if (vacancy.getPhotoId() != null) {
            setLogoImageById(vacancy.getPhotoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        ImageContainer logo = imageContainerService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        vacancyPane.getImageView().setImage(img);
    }

    public void setDefaultImage() {
        javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                getClass().getClassLoader().getResourceAsStream("images/default-vacancy.jpg"),
                150,
                150,
                false,
                false);
        vacancyPane.getImageView().setImage(defaultImage);
    }
}
