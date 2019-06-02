package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.EmploymentEntryDTO;
import de.hfu.pms.shared.enums.Campus;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class EmploymentController extends AbstractPropertyFormController<EmploymentEntryDTO> {

    @FXML
    private TextField employmentLocationTextField;

    @FXML
    private TextField kindOfEmploymentTextField;

    @FXML
    private ComboBox<Campus> employmentCampusComboBox;

    @FXML
    private DatePicker employmentBeginDatePicker;

    @FXML
    private DatePicker employmentEndDatePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.employmentCampusComboBox.getItems().addAll(Campus.values());
    }

    @Override
    public void writeProperty() throws IllegalArgumentException {
        // extract values from gui elements
        String location = this.employmentLocationTextField.getText();
        String kindOfEmployment = this.kindOfEmploymentTextField.getText();
        Campus campus = this.employmentCampusComboBox.getValue();
        LocalDate begin = employmentBeginDatePicker.getValue();
        LocalDate end = employmentEndDatePicker.getValue();
        // ToDo: check for null values or empty strings

        // create dto object and fill with data
        property = new EmploymentEntryDTO();
        property.setEmploymentLocation(location);
        property.setKindOfEmployment(kindOfEmployment);
        property.setCampusOfDeployment(campus);
        property.setEmploymentBegin(begin);
        property.setEmploymentEnd(end);
    }

    @Override
    public void readProperty(EmploymentEntryDTO property) {
        throw new UnsupportedOperationException();
    }


}
