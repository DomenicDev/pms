package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangeUserInformationEvent;
import de.hfu.pms.shared.dto.UserInfoDTO;
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
    private TextField textfieldChangeEmail;

    @FXML
    private TextField textfieldForename;

    @FXML
    private Label labelChangeUsername;

    @FXML
    private Label userRoleLabel;

    @FXML
    private TextField surnameTextField;


    private UserInfoDTO user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
    }

    @FXML
    void handleOnActionSaveButton(ActionEvent event) {
        if (user == null) {
            return;
        }

        FormValidator userValidator = new FormValidator();

        // we want to make sure that all fields are set
        userValidator.textFieldNotEmpty(textfieldForename);
        userValidator.textFieldNotEmpty(surnameTextField);
        userValidator.textFieldNotEmpty(textfieldChangeEmail);

        if (!userValidator.validationSuccessful()) {
            return;
        }

        String forename = textfieldForename.getText();
        String surname = surnameTextField.getText();
        String email = textfieldChangeEmail.getText();

        eventBus.post(new RequestChangeUserInformationEvent(user.getUsername(), forename, surname, email));

        ((Button) event.getSource()).getScene().getWindow().hide();
    }


    public void edit(UserInfoDTO user) {
        this.user = user;
        textfieldChangeEmail.setText(user.getEmail());
        textfieldForename.setText(user.getForename());
        labelChangeUsername.setText(user.getUsername());
        userRoleLabel.setText(user.getRole().name());
        surnameTextField.setText(user.getLastname());
    }

    @FXML
    void handleExitButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
