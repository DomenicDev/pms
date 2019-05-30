package de.hfu.pms.controller;


import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangeEmailEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailAdminAreaController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    private UserDTO user;

    @FXML
    private Label lableUsername;

    @FXML
    private Label labelAlert;

    @FXML
    private TextField textFieldChangeEmail;

    @FXML
    void handleChangeEmailEvent(ActionEvent event) {
                if(user == null){
            user =new UserDTO();}

        boolean validationSuccessful = writeToUserDTO();

        if (!validationSuccessful){
            return;
        }else{
            eventBus.post(new RequestChangeEmailEvent(user));
            ((Button)event.getSource()).getScene().getWindow().hide();
        }
    }

    private boolean writeToUserDTO() {

        FormValidator userValidator = new FormValidator();

        String email = textFieldChangeEmail.getText();

        if (userValidator.textFieldNotEmpty((textFieldChangeEmail))) {
            user.setEmail(email);
        }
        return userValidator.validationSuccessful();
    }

    public void edit(UserDTO user) {
        this.user = user;

        textFieldChangeEmail.setText(user.getEmail());
        lableUsername.setText(user.getUsername());

    }
    @FXML
    void handleExitEvent(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        labelAlert.setVisible(false);


    }
}


