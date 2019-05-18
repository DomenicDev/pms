package de.hfu.pms.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChangeAccountInformationController {

        @FXML
        private Button saveChangesButton;

        @FXML
        private Button ButtonExitScreen;

        @FXML
        private TextField textfieldChangeEmail;

        @FXML
        private TextField textfieldFornMeLastname;

        @FXML
        void handleExitButton(ActionEvent event) {
            ((Button) event.getSource()).getScene().getWindow().hide();



        }



}
