package ru.iworking.personnel.reserve.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.company.api.model.ICompany;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.service.api.model.IAddress;
import ru.iworking.service.api.model.INumberPhone;
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(CompanyViewController.class);

    @FXML private VBox companyView;
    @FXML private Label companyTypeLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label companyNumberPhoneLabel;
    @FXML private Label companyEmailLabel;
    @FXML private Label companyWebPageLabel;
    @FXML private Label companyAddressLabel;

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        companyView.setVisible(true);
        companyView.setManaged(true);
    }

    public void hide() {
        companyView.setVisible(false);
        companyView.setManaged(false);
    }

    public void setData(ICompany company) {
        if (company != null) {

            String companyTypePrefix = "Тип компании: ";
            Long companyTypeId = company.getCompanyTypeId();
            if (companyTypeId != null) {
                try {
                    companyTypeLabel.setText(companyTypePrefix + companyTypeDao.findFromCash(companyTypeId).getNameToView(LocaleUtil.getDefault()));
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
            INumberPhone numberPhone = company.getNumberPhone();
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
            IAddress address = company.getAddress();
            if (address != null && !address.getStreet().isEmpty()) {
                companyAddressLabel.setText(companyAddresPrefix + address.getStreet());
            } else {
                companyAddressLabel.setText(companyAddresPrefix + "не указан");
            }
        } else {
            logger.debug("Company is null. We can't view company...");
        }
    }
}
