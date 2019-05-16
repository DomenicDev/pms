package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.EventBusSystem;
import de.hfu.pms.events.CreateEmploymentEntryEvent;
import de.hfu.pms.shared.dto.EmploymentEntryDTO;
import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EmploymentController {

    private EventBus eventBus = EventBusSystem.getEventBus();

    private EmploymentEntryDTO employmentEntry = null;

    @FXML
    private ComboBox<EmploymentLocation> employmentLocationComboBox;

    @FXML
    private TextField kindOfEmploymentTextField;

    @FXML
    private ComboBox<Campus> employmentCampusComboBox;

    @FXML
    private CheckBox preTimesCheckBox;

    @FXML
    public void initialize() {
        this.employmentLocationComboBox.getItems().addAll(EmploymentLocation.values());
        this.employmentCampusComboBox.getItems().addAll(Campus.values());
    }

    @FXML
    public void handleOnActionSubmitButton(ActionEvent event) {
        writeDataToEmploymentDTO();

        // notify the parent about the submit
        eventBus.post(new CreateEmploymentEntryEvent(employmentEntry));

        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void handleOnActionCancelButton() {
        ((Stage) preTimesCheckBox.getScene().getWindow()).close();
    }

    private void writeDataToEmploymentDTO() {
        // extract values from gui elements
        EmploymentLocation location = this.employmentLocationComboBox.getValue();
        String kindOfEmployment = this.kindOfEmploymentTextField.getText();
        Campus campus = this.employmentCampusComboBox.getValue();
        boolean preTimes = this.preTimesCheckBox.isSelected();
        // ToDo: check for null values or empty strings

        // create dto object and fill with data
        this.employmentEntry = new EmploymentEntryDTO();
        this.employmentEntry.setEmploymentLocation(location);
        this.employmentEntry.setKindOfEmployment(kindOfEmployment);
        this.employmentEntry.setCampusOfDeployment(campus);
        this.employmentEntry.setPreEmploymentTimeToBeCharged(preTimes);
    }


}
