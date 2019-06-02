package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangePasswordEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordAdminAreaController implements Initializable {

        private EventBus eventBus = EventBusSystem.getEventBus();

        private UserDTO user;


        @FXML
        private Label lableUsername;

        @FXML
        private PasswordField PasswordFieldPassword;

        @FXML
        private PasswordField PasswordFieldRewrite;

        @FXML
        private Label labelAlert;

        @FXML
        void handleChangePasswordEvent(ActionEvent event) {
                if(user == null){
                        user =new UserDTO();}

                boolean validationSuccessful = writeToUserDTO();

                if (!validationSuccessful){
                        return;
                }else{
                        eventBus.post(new RequestChangePasswordEvent(user,PasswordFieldPassword.getText()));
                        ((Button)event.getSource()).getScene().getWindow().hide();

                }



        }
        public void edit(UserDTO user) {
                this.user = user;

                //PasswordFieldPassword.setText(user.getPassword());
                lableUsername.setText(user.getUsername());

        }



        @FXML
        void handleExitEvent(ActionEvent event) {
                ((Button) event.getSource()).getScene().getWindow().hide();

        }

        private boolean writeToUserDTO() {

                FormValidator userValidator = new FormValidator();

                String password = PasswordFieldPassword.getText();

                if (userValidator.textFieldNotEmpty((PasswordFieldPassword))) {
                        user.getPassword();
                }

                if (!userValidator.passwordFieldsAreSimilar(PasswordFieldPassword,PasswordFieldRewrite)){
                        labelAlert.setVisible(true);
                }

                if(userValidator.textFieldNotEmpty(PasswordFieldRewrite)){}

                return userValidator.validationSuccessful();
        }

        public void initialize(URL location, ResourceBundle resources) {
                eventBus.register(this);

                labelAlert.setVisible(false);


        }

    }


