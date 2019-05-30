package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangeUserInformationEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeAccountInformationController implements Initializable {
    private EventBus eventBus = EventBusSystem.getEventBus();


    @FXML
    private Button saveChangesButton;

    @FXML
    private Button ButtonExitScreen;

    @FXML
    private TextField textfieldChangeEmail;

    @FXML
    private TextField textfieldForname;

    @FXML
    private Label labelChangeUsername;

    @FXML
    private Label lableChangeRole;

    @FXML
    private TextField textfieldLastname;


    private UserDTO user;

    @FXML
    void handleChangeUserInformationbutton(ActionEvent event) {
        if (user == null) {
            return;
        }
        boolean validationSuccessful = writeToUserDTO();
        if (!validationSuccessful) {
            return;
        }
        if (validationSuccessful) {
            eventBus.post(new RequestChangeUserInformationEvent(user));

        }
        ((Button) event.getSource()).getScene().getWindow().hide();


    }


    public void edit(UserDTO user) {
        this.user = user;
        textfieldChangeEmail.setText((user.getEmail()));
        textfieldForname.setText(user.getForename());
        labelChangeUsername.setText(user.getUsername());
        lableChangeRole.setText(user.getRole().name());
        textfieldLastname.setText(user.getLastname());
    }

    @FXML
    void handleExitButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    private boolean writeToUserDTO() {

        FormValidator userValidator = new FormValidator();

        String benutzername = labelChangeUsername.getText();
        String vorname = textfieldForname.getText();
        String email = textfieldChangeEmail.getText();
        String nachname = textfieldLastname.getText();


        if (userValidator.textFieldNotEmpty((textfieldForname))) {
            user.setForename(vorname);
        }
        if (userValidator.textFieldNotEmpty(textfieldLastname)) {
            user.setLastname(nachname);
        }
        if (userValidator.textFieldNotEmpty((textfieldChangeEmail))) {
            user.setEmail(email);
        }


        return userValidator.validationSuccessful();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
    }
}
