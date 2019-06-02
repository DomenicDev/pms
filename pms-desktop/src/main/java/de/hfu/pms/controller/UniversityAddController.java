package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestAddUniversityEvent;
import de.hfu.pms.events.RequestUpdateUniversityEvent;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class UniversityAddController {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private TextField textFieldNameOfUniverity;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldCountry;

    @FXML
    private TextField textFieldShortForm;

    @FXML
    private Button ButtonUniversityAdd;

    @FXML
    private TextField textFieldContacttoUniversity;

    @FXML
    private Label title;

    private UniversityDTO university;

    private boolean editMode = false;

    public void edit(UniversityDTO university) {
        this.university = university;

        // fill text fields with university attributes
        textFieldNameOfUniverity.setText(university.getName());
        textFieldCountry.setText(university.getCountry());
        textFieldLocation.setText(university.getLocation());
        textFieldShortForm.setText(university.getAbbreviation());
        textFieldContacttoUniversity.setText(university.getContact());
        setEditMode(true);
    }

    public void setEditMode(boolean editMode) {
        if (editMode) {
            ButtonUniversityAdd.setText("Universität Ändern");
            title.setText("Universität Ändern");
        } else {
            ButtonUniversityAdd.setText("Universität Hinzufügen");
            title.setText("Universität Hinzufügen");
        }
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return editMode;
    }

    @FXML
    void handleAddButton(ActionEvent event) {

        if (university == null) {
            university = new UniversityDTO();
        }

        boolean validationSuccessful = writeToUniversityDTO();

        if (!validationSuccessful) {
            return;
        }

        if (!isEditMode()) {
            eventBus.post(new RequestAddUniversityEvent(university));
        } else {
            eventBus.post(new RequestUpdateUniversityEvent(university));
        }

        ((Button)event.getSource()).getScene().getWindow().hide();

    }
    private boolean writeToUniversityDTO(){

        FormValidator universityValidator = new FormValidator();

        String name = textFieldNameOfUniverity.getText();
        String location =textFieldLocation.getText();
        String country = textFieldCountry.getText();
        String shortForm =textFieldShortForm.getText();
        String contact =textFieldContacttoUniversity.getText();

        if(universityValidator.textFieldNotEmpty(textFieldNameOfUniverity)){
            university.setName(name);
        }
        if(universityValidator.textFieldNotEmpty(textFieldCountry)){
            university.setCountry(country);

        }
        if(universityValidator.textFieldNotEmpty(textFieldLocation)){
            university.setLocation(location);

        }
        university.setAbbreviation(shortForm);
        university.setContact(contact);

        return universityValidator.validationSuccessful();


    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }
}
