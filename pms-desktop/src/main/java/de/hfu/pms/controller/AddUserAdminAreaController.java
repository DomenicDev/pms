package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestAddUserEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserAdminAreaController implements Initializable {

        private EventBus eventBus = EventBusSystem.getEventBus();

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
        private ComboBox<WrappedEntity<UserRole>> comboboxRole;

        @FXML
        private TextField TextFieldEmail;

        @FXML
        private TextField TextfieldLastname;

        @FXML
        private TextField TextfieldForname;

        private UserDTO user;
        private void initComboBox() {
                // personal role combo boxes
                comboboxRole.getItems().addAll(RepresentationWrapper.getWrappedRole());
        }

        public void edit(UserDTO user) {
                this.user = user;

                // fill text fields with admin area attributes
                TextfieldUsername.setText(user.getUsername());
                TextfieldForname.setText(user.getForename());
                TextfieldLastname.setText(user.getLastname());
                TextfieldPassword.setText(user.getPassword());
                TextFieldEmail.setText(user.getEmail());
                comboboxRole.getSelectionModel().select(RepresentationWrapper.find(user.getRole(), comboboxRole.getItems()));

        }

        @FXML
        void handleActionEventAddUserInformation(ActionEvent event) {
                if(user == null){
                        user =new UserDTO();

                }
                boolean validationSuccessful = writeToUserDTO();

                if (!validationSuccessful){
                        return;
                }else{
                        eventBus.post(new RequestAddUserEvent(user));
                        ((Button)event.getSource()).getScene().getWindow().hide();
                }

        }
        private boolean writeToUserDTO() {

                FormValidator userValidator = new FormValidator();

                String forename = TextfieldForname.getText();
                String lastname = TextfieldLastname.getText();
                String password = TextfieldPassword.getText();
                String email = TextFieldEmail.getText();

                String username = TextfieldUsername.getText();

                if (userValidator.textFieldNotEmpty(TextfieldForname)) {
                        user.setForename(forename);
                }
                if (userValidator.textFieldNotEmpty(TextfieldLastname)) {
                        user.setLastname(lastname);
                }
                if (userValidator.textFieldNotEmpty((TextfieldPassword))) {
                        user.setPassword(password);
                }
                if (userValidator.textFieldNotEmpty(TextfieldUsername)) {
                        user.setUsername(username);
                }
                if (userValidator.textFieldNotEmpty((TextFieldEmail))) {
                        user.setEmail(email);
                }
                if (userValidator.textFieldNotEmpty(TextfieldPasswordRewrite)){}
                return userValidator.validationSuccessful();
        }
        @FXML
        void handleActionEventCloseScene(ActionEvent event) {
                ((Button) event.getSource()).getScene().getWindow().hide();

        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                eventBus.register(this);
                initComboBox();
        }
}
