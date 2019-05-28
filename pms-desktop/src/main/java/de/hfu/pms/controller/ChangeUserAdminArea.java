package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangePasswordEvent;
import de.hfu.pms.events.RequestChangeUserRoleEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeUserAdminArea implements Initializable {


    private EventBus eventBus = EventBusSystem.getEventBus();

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
    private Label labelUsername;

    @FXML
    private PasswordField TextFieldEmail;
    private UserDTO user;

    public void edit(UserDTO user) {
        this.user = user;

        // fill text fields with university attributes
        labelFornameLastname.setText(user.getUsername()+user.getLastname());
        labelUsername.setText(user.getUsername());
        //ComboboxRole.getSelectionModel().select(RepresentationWrapper.find(UserDTO.));
        TextfieldPassword.setText(user.getPassword());
        TextFieldEmail.setText(user.getEmail());

    }

    @FXML
    void handleActionEventChangeUserInformation(ActionEvent event) {
        if(user == null){
            user =new UserDTO();

        }
        boolean validationSuccessful = writeToUserDTO();

        if (!validationSuccessful){
            return;
        }else{
            eventBus.post(new RequestChangePasswordEvent(user, user.getPassword()));
            eventBus.post((new RequestChangeUserRoleEvent(user)));

            ((Button)event.getSource()).getScene().getWindow().hide();

        }

    }

    private boolean writeToUserDTO() {

        FormValidator userValidator = new FormValidator();


        String password = TextfieldPassword.getText();
        String email = TextFieldEmail.getText();


        if (userValidator.textFieldNotEmpty((TextfieldPassword))) {
            user.setPassword(password);
        }

        if (userValidator.textFieldNotEmpty((TextFieldEmail))) {
            user.setEmail(email);
        }
        if (userValidator.textFieldNotEmpty(TextfieldPasswordRewrite))
        {
        }
        return userValidator.validationSuccessful();
    }

    @FXML
    void handleActionEventCloseScene(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    void handleActionEventDeleteuser(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        //ComboboxRole.getItems().addAll(RepresentationWrapper.getWrappedRole());
    }
}




