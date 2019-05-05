package de.hfu.pms;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.events.LoginRequestEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreenController {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;


    @FXML
    private void initialize() {

    }

    @FXML
    public void handleExitEvent() {
        Platform.exit();
    }

    @FXML
    public void handleLoginEvent(ActionEvent actionEvent) {
        // extract credentials from text fields
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        // check if there are any empty textfields
        if (username == null || password == null) {
            // todo: show prompt which tells the user to fill in all text fields
            return;
        }

        // notify about the login request
        eventBus.post(new LoginRequestEvent(username, password));
    }

}
