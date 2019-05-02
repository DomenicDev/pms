package de.hfu.pms;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.events.LoginRequestEvent;
import de.hfu.pms.shared.SHA;
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
        System.out.println("In init...");
        //exitButton.setText("Hallo Benni");
    }

    @FXML
    public void handleExitEvent() {
        System.out.println("exit clicked");
        Platform.exit();
    }

    @FXML
    public void handleLoginEvent(ActionEvent actionEvent) {
        System.out.println("Login clicked");
       // loginButton.setOnAction(event -> window.setScene);
        eventBus.post(new LoginRequestEvent(usernameTextField.getText(),
                SHA.getSHA(passwordField.getText())));
    }

    public void dump() {
        System.out.println("in dump... ");
    }
    //public void getScene
}
