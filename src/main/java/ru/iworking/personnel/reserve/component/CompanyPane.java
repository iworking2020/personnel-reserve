package ru.iworking.personnel.reserve.component;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import ru.iworking.personnel.reserve.config.GuiceConfig;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.entity.Logo;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.LogoService;
import ru.iworking.personnel.reserve.utils.FXMLUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CompanyPane extends HBox implements Initializable {

    @FXML private Label companyTypeLabel;
    @FXML private Label companyNameLabel;

    @FXML private ImageView companyImageView;

    @FXML private Pane parent;

    @Getter @Setter
    private Company company;

    @Inject private CompanyTypeService companyTypeService;
    @Inject private LogoService logoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GuiceConfig.INJECTOR.injectMembers(this);

        final Circle clip = new Circle(30, 28, 28);
        companyImageView.setClip(clip);
    }

    public CompanyPane() {
        FXMLUtil.load("/fxml/components/CompanyPane.fxml", this, this);
    }

    public void setData(Company company) {
        this.company = company;
        CompanyType companyType = companyTypeService.findById(company.getCompanyTypeId());

        String viewName = companyType.getNameView().getName();

        if (viewName.length() < 15) {
            companyTypeLabel.setText(viewName);
        } else {
            String abbreviatedName = companyType.getAbbreviatedNameView().getName();
            companyTypeLabel.setText(abbreviatedName);
        }

        companyNameLabel.setText("\""+ company.getName() +"\"");

        if (company.getLogoId() != null) {
            setLogoImageById(company.getLogoId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        Logo logo = logoService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        Image img = new Image(targetStream);
        companyImageView.setImage(img);
    }

    public void setDefaultImage() {
        Image defaultImage = new Image(
                getClass().getClassLoader().getResourceAsStream("images/default-company.jpg"),
                150,
                150,
                false,
                false);
        companyImageView.setImage(defaultImage);
    }

}
