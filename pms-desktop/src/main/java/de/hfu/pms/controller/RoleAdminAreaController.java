package de.hfu.pms.controller;
import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestChangeUserRoleEvent;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RoleAdminAreaController implements Initializable {
    private EventBus eventBus = EventBusSystem.getEventBus();

    private UserDTO user;

    @FXML
    private Label lableUsername;

    @FXML
    private Label labelAlert;

    @FXML
    private ComboBox<WrappedEntity<UserRole>> comboBoxRole;

    @FXML
    void handleChangeRoleEvent(ActionEvent event) {
        if (user == null) {
            user = new UserDTO();
        }

        boolean validationSuccessful = writeToUserDTO();

        if (!validationSuccessful) {
            return;
        } else {
            eventBus.post(new RequestChangeUserRoleEvent(user));
            ((Button) event.getSource()).getScene().getWindow().hide();
        }
    }

    public void edit(UserDTO user) {
        this.user = user;
        comboBoxRole.getSelectionModel().select(RepresentationWrapper.find(user.getRole(), comboBoxRole.getItems()));

        lableUsername.setText(user.getUsername());
    }


    @FXML
    void handleExitEvent(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }
    private boolean writeToUserDTO() {

        FormValidator userValidator = new FormValidator();



        if (userValidator.comboBoxHasSelectedItem((comboBoxRole))) {
            user.setRole(comboBoxRole.getValue().getEntity());
        }
        return userValidator.validationSuccessful();


    }
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        labelAlert.setVisible(false);
        comboBoxRole.getItems().addAll(RepresentationWrapper.getWrappedRole());


    }


}
