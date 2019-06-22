package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyUpdatedUserEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.GuiLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountInformationController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private ResourceBundle bundle;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label forenameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label userRoleLabel;

    private Logger logger = Logger.getLogger(AccountInformationController.class);

    private UserInfoDTO user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        this.bundle = resources;
    }

    @FXML
    void handleChangeUserInformationButton(ActionEvent event) {
        try {
            ChangeAccountInformationController controller = GuiLoader.createAndShow(
                    GuiLoader.EDIT_ACCOUNT_INFORMATION,
                    bundle.getString("ui.label.change_user_information"),
                    true);
            controller.edit(user);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change account Infomrationscreen ");
        }
    }

    private void initLabel() {
        usernameLabel.setText(user.getUsername());
        forenameLabel.setText(user.getForename()+" "+ user.getLastname());
        emailLabel.setText(user.getEmail());
        userRoleLabel.setText(user.getRole().name());
    }

    public void showUser(UserInfoDTO user) {
        this.user = user;
        initLabel();
    }

    @Subscribe
    public void handle(SuccessfullyUpdatedUserEvent event) throws BusinessException {
        UserInfoDTO infoDTO = event.getUserInfoDTO();
        String username = infoDTO.getUsername();
        if (username != null) {
            // look up the current logged in user and check
            // if the own user has been changed
            UserInfoDTO userInfoDTO = EntityPool.getInstance().getLoggedInUser();
            if (username.equals(userInfoDTO.getUsername())) {
                showUser(infoDTO);
            }
        }
    }


}

