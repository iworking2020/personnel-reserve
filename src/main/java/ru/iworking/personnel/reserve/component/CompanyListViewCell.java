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
import ru.iworking.personnel.reserve.service.CompanyTypeService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompanyListViewCell extends ListCell<Company> implements Initializable {

    @FXML private Label companyTypeLabel;
    @FXML private Label companyNameLabel;

    @FXML private ImageView companyImageView;

    @FXML private Pane parent;

    private final CompanyTypeService companyTypeService = CompanyTypeService.INSTANCE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Circle clip = new Circle(30, 28, 28);
        companyImageView.setClip(clip);
    }

    @Override
    protected void updateItem(Company item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/CompanyListViewCell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            CompanyType companyType = companyTypeService.findById(item.getCompanyTypeId());

            String viewName = companyType.getNameView().getName();

            if (viewName.length() < 15) {
                companyTypeLabel.setText(viewName);
            } else {
                String abbreviatedName = companyType.getAbbreviatedNameView().getName();
                companyTypeLabel.setText(abbreviatedName);
            }

            companyNameLabel.setText("\""+ item.getName() +"\"");

            Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("images/default-company.jpg"));
            companyImageView.setImage(defaultImage);

            setText(null);
            setGraphic(parent);
        }
    }
}
