package ru.iworking.personnel.reserve.component.list.view.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.pane.VacancyPane;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.service.VacancyService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@RequiredArgsConstructor
public class VacancyCellController {

    private final VacancyService vacancyService;
    private final ImageContainerService imageContainerService;

    private final ImageUtil imageUtil;

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
        byte[] imageBytes = imageUtil.getDefaultVacancyImage();
        if (Objects.nonNull(imageBytes) && imageBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                    inputStream,
                    150,
                    150,
                    false,
                    false);
            vacancyPane.getImageView().setImage(defaultImage);
        }
    }
}
