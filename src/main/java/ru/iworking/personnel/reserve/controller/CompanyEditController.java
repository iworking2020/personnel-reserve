package ru.iworking.personnel.reserve.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.entity.Address;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.entity.NumberPhone;
import ru.iworking.personnel.reserve.model.CompanyTypeCellFactory;
import ru.iworking.personnel.reserve.model.NumberPhoneFormatter;
import ru.iworking.personnel.reserve.service.CompanyService;
import ru.iworking.personnel.reserve.service.CompanyTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyEditController extends FxmlController {

    private static final Logger logger = LogManager.getLogger(CompanyEditController.class);

    @FXML private VBox companyEdit;

    @FXML private ComboBox<CompanyType> companyTypeComboBox;
    @FXML private TextField nameCompanyTextField;
    @FXML private TextField numberPhoneTextField;
    @FXML private TextField webPageTextField;
    @FXML private TextField emailTextField;
    @FXML private TextArea addressTextArea;

    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    private CompanyTypeCellFactory companyTypeCellFactory = new CompanyTypeCellFactory();

    private CompanyTypeService companyTypeService = CompanyTypeService.INSTANCE;
    private CompanyService companyService = CompanyService.INSTANCE;

    private Company currentCompany = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);

        companyTypeComboBox.setButtonCell(companyTypeCellFactory.call(null));
        companyTypeComboBox.setCellFactory(companyTypeCellFactory);
        companyTypeComboBox.setItems(FXCollections.observableList(companyTypeService.findAll()));

    }

    @FXML
    public void actionSave(ActionEvent event) {
        if (isValid()) {
            Company company = save();
            hide();
            clear();
            getCompanyListViewController().actionUpdate(event);
            getCompanyListViewController().selectCompany(company);
            //getCompaniesTableController().actionUpdate(event);
        } else {
            logger.debug("Fields company edit block is not valid...");
        }
    }

    public void show() {
        companyEdit.setVisible(true);
        companyEdit.setManaged(true);
    }

    public void hide() {
        companyEdit.setVisible(false);
        companyEdit.setManaged(false);
    }

    public void setData(Company company) {
        if (company != null) {
            currentCompany = company;
            Long companyTypeId = company.getCompanyTypeId();
            if (companyTypeId != null) companyTypeComboBox.setValue(companyTypeService.findById(companyTypeId));
            nameCompanyTextField.setText(company.getName());
            NumberPhone numberPhone = company.getNumberPhone();
            if (numberPhone != null) numberPhoneTextField.setText(numberPhone.getNumber());
            webPageTextField.setText(company.getWebPage());
            emailTextField.setText(company.getEmail());
            Address address = company.getAddress();
            if (address != null) addressTextArea.setText(address.getStreet());
        } else {
            clear();
            logger.debug("company is null..");
        }
    }

    public Company save() {
        Long companyId = null;
        if (currentCompany != null) companyId = currentCompany.getId();

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
            company = companyService.findById(companyId);
        }

        if (companyType != null) company.setCompanyTypeId(companyType.getId());
        company.setName(nameCompanyStr);
        company.setNumberPhone(numberPhone);
        company.setWebPage(webPageStr);
        company.setEmail(emailStr);
        company.setAddress(address);

        if (companyId == null) {
            companyService.persist(company);
        } else {
            companyService.update(company);
        }
        logger.debug("Created new company: " + company.toString());

        return company;
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
        currentCompany = null;
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
        companyTypeComboBox.setItems(FXCollections.observableList(companyTypeService.findAll()));
    }

    public ClientListViewController getCompanyListViewController() {
        return (ClientListViewController) getControllerProvider().get(ClientListViewController.class.getName());
    }
}
