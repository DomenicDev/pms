package de.hfu.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddUserAdminAreaController {



        @FXML
        private TextField TextfieldUsername;

        @FXML
        private PasswordField TextfieldPassword;

        @FXML
        private PasswordField TextfieldPasswordRewrite;

        @FXML
        private Label LabelAreBothPasswortsSimilar;

        @FXML
        private Button ButtonAdd;

        @FXML
        private Button ButtonExitScene;

        @FXML
        private ComboBox<?> ComboboxRole;

        @FXML
        private TextField TextFieldEmail;

        @FXML
        private TextField TextfieldLastname;

        @FXML
        private TextField TextfieldForname;

        @FXML
        void handleActionEventAddUserInformation(ActionEvent event) {

        }

        @FXML
        void handleActionEventCloseScene(ActionEvent event) {
                ((Button) event.getSource()).getScene().getWindow().hide();

        }

    }
