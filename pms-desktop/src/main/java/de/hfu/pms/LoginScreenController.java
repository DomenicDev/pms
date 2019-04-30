package de.hfu.pms;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginScreenController {




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

    }

    private void dump() {
        System.out.println("in dump... ");
    }
    //public void getScene
}
