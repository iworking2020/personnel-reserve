package ru.iworking.personnel.reserve.component.list.view.controller;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.pane.ResumePane;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@RequiredArgsConstructor
public class ResumeCellController {

    private final ImageContainerService imageContainerService;

    private final ImageUtil imageUtil;

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

        if (Objects.nonNull(resume.getPhoto())) {
            setLogoImage(resume.getPhoto().getImage());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImage(byte[] image) {
        InputStream targetStream = new ByteArrayInputStream(image);
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        resumePane.getImageView().setImage(img);
    }

    public void setLogoImageById(Long id) {
        ImageContainer imageContainer = imageContainerService.findById(id);
        this.setLogoImage(imageContainer.getImage());
    }

    public void setDefaultImage() {
        byte[] imageBytes = imageUtil.getDefaultResumeImage();
        if (Objects.nonNull(imageBytes) && imageBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                    inputStream,
                    150,
                    150,
                    false,
                    false);
            resumePane.getImageView().setImage(defaultImage);
        }
    }
}
