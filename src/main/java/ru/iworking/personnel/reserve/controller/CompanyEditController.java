package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.company.api.model.ICompany;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.entity.Address;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.entity.NumberPhone;
import ru.iworking.personnel.reserve.model.CompanyTypeCellFactory;
import ru.iworking.personnel.reserve.model.NumberPhoneFormatter;
import ru.iworking.service.api.model.IAddress;
import ru.iworking.service.api.model.INumberPhone;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyEditController implements Initializable {

    private static final Logger logger = LogManager.getLogger(CompanyEditController.class);

    @FXML private VBox companyEdit;

    @FXML private TextField companyIdTextField;
    @FXML private ComboBox<CompanyType> companyTypeComboBox;
    @FXML private TextField nameCompanyTextField;
    @FXML private TextField numberPhoneTextField;
    @FXML private TextField webPageTextField;
    @FXML private TextField emailTextField;
    @FXML private TextArea addressTextArea;

    @FXML private Button saveCompanyButton;

    public Button getSaveCompanyButton() {
        return saveCompanyButton;
    }

    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    private CompanyTypeCellFactory companyTypeCellFactory = new CompanyTypeCellFactory();

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();
    private CompanyDao companyDao = CompanyDao.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);

        companyTypeComboBox.setButtonCell(companyTypeCellFactory.call(null));
        companyTypeComboBox.setCellFactory(companyTypeCellFactory);
        companyTypeComboBox.setItems(FXCollections.observableList(companyTypeDao.findAllFromCash()));

    }

    public void view() {
        companyEdit.setVisible(true);
    }

    public void hide() {
        companyEdit.setVisible(false);
    }

    public void setData(ICompany company) {
        if (company != null) {
            if (company.getId() != null) companyIdTextField.setText(company.getId().toString());
            Long companyTypeId = company.getCompanyTypeId();
            if (companyTypeId != null) companyTypeComboBox.setValue(companyTypeDao.findFromCash(companyTypeId));
            nameCompanyTextField.setText(company.getName());
            INumberPhone numberPhone = company.getNumberPhone();
            if (numberPhone != null) numberPhoneTextField.setText(numberPhone.getNumber());
            webPageTextField.setText(company.getWebPage());
            emailTextField.setText(company.getEmail());
            IAddress address = company.getAddress();
            if (address != null) addressTextArea.setText(address.getStreet());
        } else {
            logger.debug("company is null..");
        }
    }

    public Boolean save() {
        if (isValid()) {
            Long companyId = null;
            String companyIdStr = companyIdTextField.getText();
            if (companyIdStr != null && !companyIdStr.isEmpty()) {
                companyId = Long.valueOf(companyIdStr);
            }

            CompanyType companyType = companyTypeComboBox.getValue();
            String nameCompanyStr = nameCompanyTextField.getText();
            String numberPhoneStr = numberPhoneTextField.getText();
            String webPageStr = webPageTextField.getText();
            String emailStr = emailTextField.getText();
            String addressStr = addressTextArea.getText();

            NumberPhone numberPhone = new NumberPhone();
            numberPhone.setNumber(numberPhoneStr);

            Address address = new Address();
            address.setStreet(addressStr);

            Company company;
            if (companyId == null) {
                company = new Company();
            } else {
                company = companyDao.find(companyId);
            }

            if (companyType != null) company.setCompanyTypeId(companyType.getId());
            company.setName(nameCompanyStr);
            company.setNumberPhone(numberPhone);
            company.setWebPage(webPageStr);
            company.setEmail(emailStr);
            company.setAddress(address);

            if (companyId == null) {
                companyDao.create(company);
            } else {
                companyDao.update(company);
            }
            logger.debug("Created new company: " + company.toString());
            return true;
        } else {
            logger.debug("Fields company edit block is not valid...");
            return false;
        }
    }

    private Boolean isValid() {
        Boolean isValid = true;
        if (companyTypeComboBox.getValue() == null) {
            companyTypeComboBox.getStyleClass().add("has-error");
            isValid = false;
        }
        if (nameCompanyTextField.getText() == null || nameCompanyTextField.getText().length() <= 0) {
            nameCompanyTextField.getStyleClass().add("has-error");
            isValid = false;
        }
        return isValid;
    }

    public void clear() {
        companyIdTextField.setText("");
        companyTypeComboBox.setValue(null);
        companyTypeComboBox.getStyleClass().remove("has-error");
        nameCompanyTextField.setText("");
        nameCompanyTextField.getStyleClass().remove("has-error");
        numberPhoneTextField.setText("");
        webPageTextField.setText("");
        emailTextField.setText("");
        addressTextArea.setText("");
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        hide();
        clear();
    }

    public void reload(ActionEvent event) {
        actionCancel(event);
        companyTypeDao.clearCash();
        companyTypeComboBox.setItems(FXCollections.observableList(companyTypeDao.findAllFromCash()));
    }
}
