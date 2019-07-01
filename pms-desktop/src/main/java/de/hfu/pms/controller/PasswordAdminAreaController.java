package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangePasswordEvent;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Screen used for creating a new password for user.
 * The user has to enter the new password twice.
 * Only if the passwords match, the new password is accepted.
 */
public class PasswordAdminAreaController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private UserInfoDTO user;

    @FXML
    private Label labelUsername;

    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldRewrite;

    @FXML
    private Label labelAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        labelAlert.setVisible(false);
    }

    @FXML
    void handleChangePasswordEvent(ActionEvent event) {
        FormValidator userValidator = new FormValidator();
        userValidator.textFieldNotEmpty(passwordFieldPassword);
        userValidator.textFieldNotEmpty(passwordFieldRewrite);
        userValidator.passwordFieldsAreSimilar(passwordFieldPassword, passwordFieldRewrite);

        if (userValidator.validationSuccessful()) {
            eventBus.post(new RequestChangePasswordEvent(user.getUsername(), passwordFieldPassword.getText()));
            ((Button) event.getSource()).getScene().getWindow().hide();
        }
    }

    public void edit(UserInfoDTO user) {
        this.user = user;
        labelUsername.setText(user.getUsername());
    }

    @FXML
    void handleExitEvent(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}


