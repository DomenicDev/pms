package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.EmploymentEntryDTO;
import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class EmploymentController extends AbstractPropertyFormController<EmploymentEntryDTO> {

    @FXML
    private ComboBox<EmploymentLocation> employmentLocationComboBox;

    @FXML
    private TextField kindOfEmploymentTextField;

    @FXML
    private ComboBox<Campus> employmentCampusComboBox;

    @FXML
    private CheckBox preTimesCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.employmentLocationComboBox.getItems().addAll(EmploymentLocation.values());
        this.employmentCampusComboBox.getItems().addAll(Campus.values());
    }

    @Override
    public void writeProperty() throws IllegalArgumentException {
        // extract values from gui elements
        EmploymentLocation location = this.employmentLocationComboBox.getValue();
        String kindOfEmployment = this.kindOfEmploymentTextField.getText();
        Campus campus = this.employmentCampusComboBox.getValue();
        boolean preTimes = this.preTimesCheckBox.isSelected();
        // ToDo: check for null values or empty strings

        // create dto object and fill with data
        property = new EmploymentEntryDTO();
        property.setEmploymentLocation(location);
        property.setKindOfEmployment(kindOfEmployment);
        property.setCampusOfDeployment(campus);
        property.setPreEmploymentTimeToBeCharged(preTimes);
    }

    @Override
    public void readProperty(EmploymentEntryDTO property) {
        throw new UnsupportedOperationException();
    }


}
