package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyChangedEmailEvent;
import de.hfu.pms.events.SuccessfullyChangedPasswordEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import de.hfu.pms.utils.WrappedEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class ChangeUserAdminArea  implements Initializable {


    private EventBus eventBus = EventBusSystem.getEventBus();


    @FXML
    private Button ButtonChange;

    @FXML
    private Button ButtonExitScene;

    @FXML
    private Label labelFornameLastname;

    @FXML
    private Button ButtonDeleteUser;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelRole;

    @FXML
    private Button buttonChangePassword;

    @FXML
    private Button buttonChangeRole;

    @FXML
    private Button buttonChangeEmail;

    @FXML
    private Label labelEmail;

    private Logger logger = Logger.getLogger(ChangeUserAdminArea.class);

    @FXML
    void handleButtonChangeEmail(ActionEvent event) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/changeEmailAdminArea.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Email von " + user.getUsername()+ " ändern" );
            stage.setScene(new Scene(root));
            stage.show();

            EmailAdminAreaController controller = fxmlLoader.getController();
            controller.edit(user);

        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change E-mail - AdminArea Screen ");
        }

    }


    @FXML
    void handleButtonChangePassword(ActionEvent event) {

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ChangePasswordAdminArea.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Passwort von " +user.getUsername()+ " ändern" );
            stage.setScene(new Scene(root));
            stage.show();

            PasswordAdminAreaController controller = fxmlLoader.getController();
            controller.edit(user);


        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change Password - AdminArea Screen ");
        }
    }



    @FXML
            void handleButtonChangeRole (ActionEvent event) {

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/changeRoleAdminArea.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rolle von " +user.getUsername()+ " ändern");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change Role - AdminArea Screen ");
        }
    }

    @FXML
    private ComboBox<WrappedEntity<UserRole>> ComboboxRole;


    private UserDTO user;

    public void edit(UserDTO user) {
        this.user = user;

        labelFornameLastname.setText(user.getUsername()+ " " +user.getLastname());
        labelUsername.setText(user.getUsername());
        //labelRole.setText(user.getRole().);
        labelEmail.setText(user.getEmail());
        /*ComboboxRole.getSelectionModel().select(RepresentationWrapper.find(user.getRole(), ComboboxRole.getItems()));
        TextfieldPassword.setText(user.getPassword());
        TextFieldEmail.setText(user.getEmail());
*/
    }
    @Subscribe
    public void handleEmailChangeRequestEvent(SuccessfullyChangedEmailEvent event){

        labelEmail.setText(user.getEmail());
    }
    @Subscribe
    public void  handlePasswordChangeButton(SuccessfullyChangedPasswordEvent event){

    }

    @FXML
    void handleActionEventChangeUserInformation(ActionEvent event) {
        /*if(user == null){
            user =new UserDTO();

        }
        boolean validationSuccessful = writeToUserDTO();

        if (!validationSuccessful){
            return;
        }else{
            eventBus.post(new RequestChangePasswordEvent(user, user.getPassword()));
            eventBus.post((new RequestChangeUserRoleEvent(user)));

            ((Button)event.getSource()).getScene().getWindow().hide();

        }*/

    }

   /*private boolean writeToUserDTO() {

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
    }*/

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




