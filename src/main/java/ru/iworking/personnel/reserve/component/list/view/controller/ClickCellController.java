package ru.iworking.personnel.reserve.component.list.view.controller;

import com.google.common.base.Strings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.component.list.view.pane.ClickPane;
import ru.iworking.personnel.reserve.controller.ClickListViewController;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.service.ClickService;
import ru.iworking.personnel.reserve.service.ImageContainerService;
import ru.iworking.personnel.reserve.service.ResumeStateService;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@RequiredArgsConstructor
public class ClickCellController {

    private final ImageContainerService imageContainerService;
    private final ResumeStateService resumeStateService;
    private final ClickService clickService;
    private final ClickListViewController clickListViewController;

    private final ImageUtil imageUtil;

    private final ClickPane clickPane;
    @Getter private Click click;

    public void init() {
        clickPane.getResumeStateComboBox().setItems(FXCollections.observableList(resumeStateService.findAll()));
        clickPane.getResumeStateComboBox().setOnAction(event -> {
            click.setResumeState(clickPane.getResumeStateComboBox().getValue());
            clickService.update(click);
        });
        clickPane.getButtonUnfasten().setOnAction(this::actionUnfasten);
    }

    public void setData(Click click) {
        this.click = click;
        clickPane.getFioLabel().setText(click.getResume().getProfile().getFullName());
        if (!Strings.isNullOrEmpty(click.getResume().getProfession())) {
            clickPane.getProfessionLabel().setText(click.getResume().getProfession());
        } else {
            clickPane.getProfessionLabel().setText("не указана");
        }
        clickPane.getResumeStateComboBox().setValue(click.getResumeState());
        if (Objects.nonNull(click.getResume()) && Objects.nonNull(click.getResume().getPhoto())) {
            setLogoImage(click.getResume().getPhoto().getImage());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImage(byte[] image) {
        InputStream targetStream = new ByteArrayInputStream(image);
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        clickPane.getImageView().setImage(img);
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
            clickPane.getImageView().setImage(defaultImage);
        }
    }

    private void actionUnfasten(ActionEvent event) {
        clickListViewController.unfastenItem(click);
    }

}
