package ru.iworking.personnel.reserve.component.list.view.controller;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.pane.ResumePane;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.ImageContainerService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RequiredArgsConstructor
public class ResumeCellController {

    private final ImageContainerService imageContainerService;

    private final ResumePane resumePane;
    @Getter private Resume resume;

    public void setData(Resume resume) {
        this.resume = resume;
        resumePane.getFioLabel().setText(resume.getProfile().getFullName());
        if (!Strings.isNullOrEmpty(resume.getProfession())) {
            resumePane.getProfessionLabel().setText(resume.getProfession());
        } else {
            resumePane.getProfessionLabel().setText("не указана");
        }

        if (resume.getPhotoId() != null) {
            setLogoImageById(resume.getPhotoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        ImageContainer imageContainer = imageContainerService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(imageContainer.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        resumePane.getImageView().setImage(img);
    }

    public void setDefaultImage() {
        javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                getClass().getClassLoader().getResourceAsStream("images/default-resume.jpg"),
                150,
                150,
                false,
                false);
        resumePane.getImageView().setImage(defaultImage);
    }
}
