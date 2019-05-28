package de.hfu.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChangeUserAdminArea {


    @FXML
    private TextField TextfieldUsername;

    @FXML
    private PasswordField TextfieldPassword;

    @FXML
    private PasswordField TextfieldPasswordRewrite;

    @FXML
    private Label LabelAreBothPasswortsSimilar;

    @FXML
    private Button ButtonChange;

    @FXML
    private Button ButtonExitScene;

    @FXML
    private Label labelFornameLastname;

    @FXML
    private Button ButtonDeleteUser;

    @FXML
    private ComboBox<?> ComboboxRole;

    @FXML
    void handleActionEventChangeUserInformation(ActionEvent event) {

    }

    @FXML
    void handleActionEventCloseScene(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void handleActionEventDeleteuser(ActionEvent event) {

    }

}



