package ru.iworking.personnel.reserve.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.entity.Logo;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.LogoService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CompanyCell extends ListCell<Company> implements Initializable {

    @FXML private Label companyTypeLabel;
    @FXML private Label companyNameLabel;

    @FXML private ImageView companyImageView;

    @FXML private Pane parent;

    private Company company;

    private final CompanyTypeService companyTypeService = CompanyTypeService.INSTANCE;
    private final LogoService logoService = LogoService.INSTANCE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        companyImageView.setClip(clip);
    }

    @Override
    protected void updateItem(Company company, boolean empty) {
        this.company = company;
        super.updateItem(company, empty);
        if(empty || company == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/CompanyCell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
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

            setText(null);
            setGraphic(parent);
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

    public Company getCompany() {
        return company;
    }
}
