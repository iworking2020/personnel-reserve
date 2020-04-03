package ru.iworking.personnel.reserve.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import ru.iworking.service.api.utils.LocaleUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class VacanciesPaneFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(VacanciesPaneFxmlController.class);

    private NumberPhoneFormatter numberPhoneFormatter = new NumberPhoneFormatter();

    private CompanyTypeDao companyTypeDao = CompanyTypeDao.getInstance();
    private CompanyDao companyDao = CompanyDao.getInstance();

    @FXML private Button editCompanyButton;
    @FXML private Button deleteCompanyButton;

    @FXML private TableView<Company> tableCompanies;
    @FXML private TableColumn<Company, String> companyTypeColumn;
    @FXML private TableColumn<Company, String> companyNameColumn;

    @FXML private VBox companyEditBlock;
    @FXML private ComboBox<CompanyType> companyTypeComboBox;
    @FXML private TextField nameCompanyTextField;
    @FXML private TextField numberPhoneTextField;
    @FXML private TextField webPageTextField;
    @FXML private TextField emailTextField;
    @FXML private TextArea addressTextArea;

    @FXML private Button saveCompanyButton;
    @FXML private Button updateCompanyButton;

    @FXML private VBox companyViewBlock;
    @FXML private Label companyTypeLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label companyNumberPhoneLabel;
    @FXML private Label companyEmailLabel;
    @FXML private Label companyWebPageLabel;
    @FXML private Label companyAddressLabel;

    private CompanyTypeCellFactory companyTypeCellFactory = new CompanyTypeCellFactory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberPhoneTextField.setTextFormatter(numberPhoneFormatter);

        companyTypeComboBox.setButtonCell(companyTypeCellFactory.call(null));
        companyTypeComboBox.setCellFactory(companyTypeCellFactory);
        companyTypeComboBox.setItems(FXCollections.observableList(companyTypeDao.findAllFromCash()));

        companyTypeColumn.setCellValueFactory(cellData -> {
            CompanyType companyType = companyTypeDao.findFromCash(cellData.getValue().getCompanyTypeId());
            String textColumn = companyType != null ? companyType.getAbbreviatedNameToView(LocaleUtil.getDefault()) : "не указан";
            return new ReadOnlyStringWrapper(textColumn);
        });
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));
        tableCompanies.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editCompanyButton.setDisable(false);
            deleteCompanyButton.setDisable(false);
            viewCompany(newSelection);
        });
    }

    private void viewCompany(ICompany company) {
        companyEditBlock.setVisible(false);
        companyViewBlock.setVisible(true);

        String companyTypePrefix = "Тип компании: ";
        Long companyTypeId = company.getCompanyTypeId();
        if (companyTypeId != null) {
            companyTypeLabel.setText(companyTypePrefix + companyTypeDao.findFromCash(companyTypeId).getNameToView(LocaleUtil.getDefault()));
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
    }

    private void setDataForCompanyEditBlock(Company company) {
        Long companyTypeId = company.getCompanyTypeId();
        if (companyTypeId != null) companyTypeComboBox.setValue(companyTypeDao.findFromCash(companyTypeId));
        nameCompanyTextField.setText(company.getName());
        NumberPhone numberPhone = company.getNumberPhone();
        if (numberPhone != null) numberPhoneTextField.setText(numberPhone.getNumber());
        webPageTextField.setText(company.getWebPage());
        emailTextField.setText(company.getEmail());
        Address address = company.getAddress();
        if (address != null) addressTextArea.setText(address.getStreet());
    }

    @FXML
    public void actionButtonCreateCompany(ActionEvent event) {
        clearCompanyEditBlock();
        companyEditBlock.setVisible(true);
        companyViewBlock.setVisible(false);
        saveCompanyButton.setVisible(true);
        updateCompanyButton.setVisible(false);
    }

    @FXML
    public void actionButtonUpdateCompaniesTable(ActionEvent event) {
        clearSelectionModelCompaniesTable();
        tableCompanies.setItems(FXCollections.observableList(companyDao.findAll()));
        companyViewBlock.setVisible(false);
        logger.debug("Companies table has been updated...");
    }

    @FXML
    public void actionButtonEditCompany(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
        if (company != null) {
            setDataForCompanyEditBlock(company);
            saveCompanyButton.setVisible(false);
            updateCompanyButton.setVisible(true);
            companyEditBlock.setVisible(true);
            companyViewBlock.setVisible(false);
        } else actionButtonSaveCompany(event);
    }

    @FXML
    public void actionButtonDeleteCompany(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
        if (company != null) companyDao.delete(company);
        actionButtonUpdateCompaniesTable(event);
    }

    @FXML
    public void actionButtonCancelCreateCompany(ActionEvent event) {
        companyEditBlock.setVisible(false);
        clearCompanyEditBlock();
    }

    @FXML
    public void actionButtonUpdateCompany(ActionEvent event) {
        Company company = tableCompanies.getSelectionModel().getSelectedItem();
        if (company == null) actionButtonSaveCompany(event); else {
            if (isValidFieldsCompanyEditBlock()) {
                CompanyType companyType = companyTypeComboBox.getValue();
                String nameCompanyStr = nameCompanyTextField.getText();
                String numberPhoneStr = numberPhoneTextField.getText();
                String webPageStr = webPageTextField.getText();
                String emailStr = emailTextField.getText();
                String addressStr = addressTextArea.getText();

                company.getNumberPhone().setNumber(numberPhoneStr);
                company.getAddress().setStreet(addressStr);

                if (companyType != null) company.setCompanyTypeId(companyType.getId());
                company.setName(nameCompanyStr);
                company.setWebPage(webPageStr);
                company.setEmail(emailStr);

                companyDao.update(company);
                logger.debug("Updated company: " + company.toString());
                companyEditBlock.setVisible(false);
                clearCompanyEditBlock();
                actionButtonUpdateCompaniesTable(event);
            } else {
                logger.debug("Fields company edit block is not valid...");
            }
        }
    }

    @FXML
    public void actionButtonSaveCompany(ActionEvent event) {
        if (isValidFieldsCompanyEditBlock()) {
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

            Company company = new Company();
            if (companyType != null) company.setCompanyTypeId(companyType.getId());
            company.setName(nameCompanyStr);
            company.setNumberPhone(numberPhone);
            company.setWebPage(webPageStr);
            company.setEmail(emailStr);
            company.setAddress(address);

            companyDao.create(company);
            logger.debug("Created new company: " + company.toString());
            companyEditBlock.setVisible(false);
            clearCompanyEditBlock();
            actionButtonUpdateCompaniesTable(event);
        } else {
            logger.debug("Fields company edit block is not valid...");
        }
    }

    public void updateCompanyTable(ActionEvent event) {
        actionButtonUpdateCompaniesTable(event);
    }

    public void updateCompanyEditBlock(ActionEvent event) {
        actionButtonCancelCreateCompany(event);
        companyTypeComboBox.setItems(FXCollections.observableList(companyTypeDao.findAllFromCash()));
    }

    private void clearSelectionModelCompaniesTable() {
        tableCompanies.getSelectionModel().clearSelection();
        editCompanyButton.setDisable(true);
        deleteCompanyButton.setDisable(true);
    }

    private Boolean isValidFieldsCompanyEditBlock() {
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

    public void clearCompanyEditBlock() {
        companyTypeComboBox.setValue(null);
        companyTypeComboBox.getStyleClass().remove("has-error");
        nameCompanyTextField.setText("");
        nameCompanyTextField.getStyleClass().remove("has-error");
        numberPhoneTextField.setText("");
        webPageTextField.setText("");
        emailTextField.setText("");
        addressTextArea.setText("");
    }

    public void reload(ActionEvent event) {
        companyTypeDao.clearCash();
        updateCompanyTable(event);
        updateCompanyEditBlock(event);
    }

}
