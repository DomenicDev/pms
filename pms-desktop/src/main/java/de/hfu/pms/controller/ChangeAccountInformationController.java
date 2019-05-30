package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangeUserRoleEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.WrappedEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeAccountInformationController implements Initializable {
        private EventBus eventBus = EventBusSystem.getEventBus();




        @FXML
        private Button saveChangesButton;

        @FXML
        private Button ButtonExitScreen;

        @FXML
        private TextField textfieldChangeEmail;

        @FXML
        private TextField textfieldForname;

        @FXML
        private Label labelChangeUsername;

        @FXML
        private Label lableChangeRole;

        @FXML
        private TextField textfieldLastname;

        @FXML
        private PasswordField passwordfielPassword;


        @FXML
        private ComboBox<WrappedEntity<UserRole>> ComboboxRole;


        private UserDTO user;

        @FXML
        void handleChangeUserInformationbutton(ActionEvent event) {
                if (user == null){
                        return;
                }
                boolean validationSuccessful =writeToUserDTO();
                if (!validationSuccessful){
                        return;
                }
                if (validationSuccessful){
                        eventBus.post(new RequestChangeUserRoleEvent(user));

                }
                ((Button)event.getSource()).getScene().getWindow().hide();


        }



        public void edit (UserDTO user){
                this.user =user;
                textfieldChangeEmail.setText((user.getEmail()));
                textfieldForname.setText(user.getForename());
                labelChangeUsername.setText(user.getUsername());
                lableChangeRole.setText(user.getRole().name());
                textfieldLastname.setText(user.getLastname());
                passwordfielPassword.setText(user.getPassword());
        }

        @FXML
        void handleExitButton(ActionEvent event) {
            ((Button) event.getSource()).getScene().getWindow().hide();
        }

        private boolean writeToUserDTO(){
                FormValidator userValidator = new FormValidator();

                String benutzername =labelChangeUsername.getText();
                String vorname =textfieldForname.getText();
                String email= textfieldChangeEmail.getText();
                String nachname =textfieldLastname.getText();
                String password =passwordfielPassword.getText();



                if (userValidator.textFieldNotEmpty((textfieldForname))){
                        user.setForename(vorname);
                }
                if (userValidator.textFieldNotEmpty(textfieldLastname)){
                        user.setLastname(nachname);
                }
                if (userValidator.textFieldNotEmpty((textfieldChangeEmail))){
                        user.setEmail(email);
                }
                if (userValidator.comboBoxHasSelectedItem(ComboboxRole)) {
                        user.setRole(ComboboxRole.getValue().getEntity());
                }
                if (userValidator.textFieldNotEmpty(passwordfielPassword)){
                        user.setPassword(password);
                }


                user.setUsername(benutzername);

                return userValidator.validationSuccessful();
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                eventBus.register(this);
        }
}
