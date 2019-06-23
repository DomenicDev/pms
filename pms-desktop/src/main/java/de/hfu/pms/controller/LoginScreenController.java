package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.LoginFailedEvent;
import de.hfu.pms.events.LoginRequestEvent;
import de.hfu.pms.utils.FormValidator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        errorLabel.getStyleClass().add("error_text");
    }

    @FXML
    public void handleExitEvent() {
        Platform.exit();
    }

    @FXML
    public void handleLoginEvent() {
        // check that both text fields are not empty
        FormValidator validator = new FormValidator();
        validator.textFieldNotEmpty(usernameTextField);
        validator.textFieldNotEmpty(passwordField);

        if (!validator.validationSuccessful()) {
            return;
        }

        // extract login credentials
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        // reset error label flag
        errorLabel.setVisible(false);

        // notify about the login request
        eventBus.post(new LoginRequestEvent(username, password));
    }

    @Subscribe
    public void handle(LoginFailedEvent event) {
        errorLabel.setText(event.getReason());
        errorLabel.setVisible(true);
    }


}
