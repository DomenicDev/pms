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

/**
 * Controller for adding a new user.
 */
public class AddUserAdminAreaController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private TextField TextfieldUsername;

    @FXML
    private PasswordField passwordfielPassword;

    @FXML
    private PasswordField TextfieldPasswordRewrite;

    @FXML
    private Label LabelAreBothPasswortsSimilar;

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
        passwordfielPassword.setText(user.getPassword());
        TextFieldEmail.setText(user.getEmail());
        comboboxRole.getSelectionModel().select(RepresentationWrapper.find(user.getRole(), comboboxRole.getItems()));
    }

    @FXML
    public void handleActionEventAddUserInformation(ActionEvent event) {
        if (user == null) {
            user = new UserDTO();

        }
        boolean validationSuccessful = writeToUserDTO();

        if (validationSuccessful) {
            eventBus.post(new RequestAddUserEvent(user));
            ((Button) event.getSource()).getScene().getWindow().hide();
        }
    }

    private boolean writeToUserDTO() {

        FormValidator userValidator = new FormValidator();

        String forename = TextfieldForname.getText();
        String lastname = TextfieldLastname.getText();
        String password = passwordfielPassword.getText();
        String email = TextFieldEmail.getText();
        String username = TextfieldUsername.getText();

        if (userValidator.comboBoxHasSelectedItem(comboboxRole)) {
            user.setRole(comboboxRole.getValue().getEntity());
        }

        if (userValidator.textFieldNotEmpty(TextfieldForname)) {
            user.setForename(forename);
        }
        if (userValidator.textFieldNotEmpty(TextfieldLastname)) {
            user.setLastname(lastname);
        }
        if (userValidator.textFieldNotEmpty((passwordfielPassword))) {
            user.setPassword(password);
        }
        if (userValidator.textFieldNotEmpty(TextfieldUsername)) {
            user.setUsername(username);
        }
        if (userValidator.textFieldNotEmpty((TextFieldEmail))) {
            user.setEmail(email);
        }
        if (!userValidator.passwordFieldsAreSimilar(passwordfielPassword, TextfieldPasswordRewrite))
            LabelAreBothPasswortsSimilar.setVisible(true);

        if (userValidator.textFieldNotEmpty(TextfieldPasswordRewrite)) {
        }
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
        LabelAreBothPasswortsSimilar.setVisible(false);
    }
}
