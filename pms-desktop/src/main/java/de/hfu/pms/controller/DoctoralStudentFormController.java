package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.EventBusSystem;
import de.hfu.pms.events.SaveDoctoralStudentEvent;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PersonalDataDTO;
import de.hfu.pms.shared.enums.FamilyStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DoctoralStudentFormController {

    private DoctoralStudentDTO doctoralStudent = null;

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    // PERSONAL DATA
    @FXML private TextField lastNameTextField;

    @FXML
    public void initialize() {

    }

    public void resetAllInputFields() {
        doctoralStudent = null;
        // todo
    }

    public void fillFormMask(DoctoralStudentDTO doctoralStudent) {
        this.doctoralStudent = doctoralStudent;
        // todo.. fill text fields
    }

    @FXML
    public void handleOnActionCancelButton() {
        cancel();
    }

    private void cancel() {
        // todo
    }

    @FXML
    public void handleOnActionSaveButton() {
        if (doctoralStudent == null) {
            // we are not editing an existing object but actually creating a new one
            this.doctoralStudent = new DoctoralStudentDTO();
            this.doctoralStudent.setPersonalData(new PersonalDataDTO());
            // todo add missing parts....
        } else {
            // if we are here, we edit an already existing student
            // we must not set the ID !!!
        }
        // write form data to java object
        writeToDoctoralStudentDTO();

        // post a new save event to notify subscribers about the save action
        // they should take care about the actual saving process
        eventBus.post(new SaveDoctoralStudentEvent(doctoralStudent));

        // after saving, we can reset our input fields
        resetAllInputFields();
    }

    private <T> T checkForNull(T t) throws IllegalArgumentException {
        if (t == null) {
            throw new IllegalArgumentException("null is not a valid value");
        }
        return t;
    }

    private void writeToDoctoralStudentDTO() {
        // we first check personal data
        PersonalDataDTO personalData = doctoralStudent.getPersonalData();

        String lastName = checkForNull(lastNameTextField.getText());
        // todo ... for now we insert some test data
        personalData.setLastName(lastName);
        personalData.setForename("Bernd");
        personalData.setFamilyStatus(FamilyStatus.Married);
    }


}
