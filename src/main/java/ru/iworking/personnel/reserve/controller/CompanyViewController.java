package ru.iworking.personnel.reserve.controller;

import com.google.common.base.Strings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iworking.personnel.reserve.component.VacancyListViewPane;
import ru.iworking.personnel.reserve.entity.Address;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.entity.NumberPhone;
import ru.iworking.personnel.reserve.service.CompanyService;
import ru.iworking.personnel.reserve.service.CompanyTypeService;
import ru.iworking.personnel.reserve.service.ImageContainerService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CompanyViewController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(CompanyViewController.class);

    @FXML private VBox companyView;
    @FXML private Label companyTypeLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label companyNumberPhoneLabel;
    @FXML private Label companyEmailLabel;
    @FXML private Label companyWebPageLabel;
    @FXML private Label companyAddressLabel;
    @FXML private ImageView imageView;

    @Autowired private CompanyTypeService companyTypeService;
    @Autowired private CompanyService companyService;
    @Autowired private ImageContainerService imageContainerService;

    private Company currentCompany = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
        final Circle clip = new Circle(75, 75, 70);
        imageView.setClip(clip);
    }

    public void show() {
        companyView.setVisible(true);
        companyView.setManaged(true);
    }

    public void hide() {
        companyView.setVisible(false);
        companyView.setManaged(false);
    }

    public void setData(Company company) {
        if (company != null) {
            currentCompany = company;

            String companyTypePrefix = "Тип компании: ";
            Long companyTypeId = company.getCompanyTypeId();
            if (companyTypeId != null) {
                try {
                    companyTypeLabel.setText(companyTypePrefix + companyTypeService.findById(companyTypeId).getNameView().getName());
                } catch (Exception ex) {
                    companyTypeLabel.setText(companyTypePrefix + "");
                    logger.error(ex);
                }
            } else {
                companyTypeLabel.setText(companyTypePrefix + "не указан");
            }

            String companyNamePrefix = "Наименование: ";
            companyNameLabel.setText(companyNamePrefix + company.getName());
            String companyNumberPhonePrefix = "Номер тел.: ";
            NumberPhone numberPhone = company.getNumberPhone();
            if (numberPhone != null && !numberPhone.getNumber().isEmpty()) {
                companyNumberPhoneLabel.setText(companyNumberPhonePrefix + numberPhone.getNumber());
            } else {
                companyNumberPhoneLabel.setText(companyNumberPhonePrefix + "не указан");
            }

            String companyEmailPrefix = "Эл. почта: ";
            String email = company.getEmail();
            if (email != null && !email.isEmpty()) {
                companyEmailLabel.setText(companyEmailPrefix + email);
            } else {
                companyEmailLabel.setText(companyEmailPrefix + "не указан");
            }

            String companyWebPagePrefix = "Эл. адрес: ";
            String webPage = company.getWebPage();
            if (webPage != null && !webPage.isEmpty()) {
                companyWebPageLabel.setText(companyWebPagePrefix + company.getWebPage());
            } else {
                companyWebPageLabel.setText(companyWebPagePrefix + "не указан");
            }

            String companyAddresPrefix = "Адрес: ";
            Address address = company.getAddress();
            if (address != null) {
                List<String> list = new LinkedList();

                if (!Strings.isNullOrEmpty(address.getCountry())) list.add(address.getCountry());
                if (!Strings.isNullOrEmpty(address.getRegion())) list.add(address.getRegion());
                if (!Strings.isNullOrEmpty(address.getCity())) list.add(address.getCity());
                if (!Strings.isNullOrEmpty(address.getStreet())) list.add(address.getStreet());
                if (!Strings.isNullOrEmpty(address.getHouse())) list.add(address.getHouse());

                String fullAddress = list.stream().collect(Collectors.joining( ", " ));

                if (!Strings.isNullOrEmpty(fullAddress)) {
                    companyAddressLabel.setText(companyAddresPrefix + fullAddress);
                } else {
                    companyAddressLabel.setText(companyAddresPrefix + "не указан");
                }
            } else {
                companyAddressLabel.setText(companyAddresPrefix + "не указан");
            }

            if (company.getImageContainerId() != null) {
                setLogoImageById(company.getImageContainerId());
            } else {
                setDefaultImage();
            }
        } else {
            logger.debug("Company is null. We can't view company...");
        }
    }

    @FXML
    public void actionEditCompany(ActionEvent event) {
        getCompanyEditController().setData(currentCompany);
        getCompanyEditController().show();
    }

    @FXML
    public void actionDelete(ActionEvent event) {
        companyService.deleteById(currentCompany.getId());
        getClientListViewController().actionUpdate(event);
        VacancyListViewPane vacancyListViewPane = getClientListViewController().getVacancyListViewPane();
        if (vacancyListViewPane != null) vacancyListViewPane.actionBack(null);
    }

    public void setLogoImageById(Long id) {
        ImageContainer logo = imageContainerService.findById(id);
        InputStream targetStream = new ByteArrayInputStream(logo.getImage());
        javafx.scene.image.Image img = new javafx.scene.image.Image(targetStream);
        imageView.setImage(img);
    }

    public void setDefaultImage() {
        javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(
                getClass().getClassLoader().getResourceAsStream("images/default-company.jpg"),
                150,
                150,
                false,
                false);
        imageView.setImage(defaultImage);
    }

    public Company getCurrentCompany() {
        return currentCompany;
    }
    public void setCurrentCompany(Company currentCompany) {
        this.currentCompany = currentCompany;
    }

    public CompanyEditController getCompanyEditController() {
        return (CompanyEditController) getControllerProvider().get(CompanyEditController.class.getName());
    }

    public ClientListViewController getClientListViewController() {
        return (ClientListViewController) getControllerProvider().get(ClientListViewController.class.getName());
    }
}
