package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import ru.iworking.personnel.reserve.ApplicationContextProvider;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.ImageContainerService;
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

    private final CompanyTypeService companyTypeService;
    private final ImageContainerService imageContainerService;

    public CompanyPane() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        this.companyTypeService = context.getBean(CompanyTypeService.class);
        this.imageContainerService = context.getBean(ImageContainerService.class);
        FXMLUtil.load("/fxml/components/CompanyPane.fxml", this, this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        companyImageView.setClip(clip);
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

        if (company.getImageContainerId() != null) {
            setLogoImageById(company.getImageContainerId());
        } else {
            setDefaultImage();
        }
    }

    public void setLogoImageById(Long id) {
        ImageContainer logo = imageContainerService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        companyImageView.setImage(img);
    }

    public void setDefaultImage() {
        javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                getClass().getClassLoader().getResourceAsStream("images/default-company.jpg"),
                150,
                150,
                false,
                false);
        companyImageView.setImage(defaultImage);
    }

}
