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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Screen Controller for the login screen.
 */
public class LoginScreenController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private ProgressBar loginProgressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        errorLabel.getStyleClass().add("error_text");

        loginProgressBar.setVisible(false);
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

        // make login progress bar visible
        setProgressBarVisible(true);

        // notify about the login request
        eventBus.post(new LoginRequestEvent(username, password));
    }

    private void setProgressBarVisible(boolean visible) {
        this.loginProgressBar.setVisible(visible);
    }

    @Subscribe
    public void handle(LoginFailedEvent event) {
        errorLabel.setText(event.getReason());
        errorLabel.setVisible(true);

        // reset progress bar
        setProgressBarVisible(false);
    }

}
