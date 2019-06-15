package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyAddedUserEvent;
import de.hfu.pms.events.SuccessfullyChangedUserInformationEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountInformationController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

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
    }

    @FXML
    void handleChangeUserInformationButton(ActionEvent event) {
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/change_accountinformation_screen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setWidth(600);
            stage.setHeight(400);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Benutzerinformationen Ändern");
            stage.setScene(new Scene(root));
            stage.show();


            ChangeAccountInformationController controller = fxmlLoader.getController();
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
    public void handleUserChangeEvent(SuccessfullyAddedUserEvent event) {
        UserDTO user = event.getUser();
        usernameLabel.setText(user.getUsername());
        forenameLabel.setText(user.getForename()+" "+ user.getLastname());
        emailLabel.setText(user.getEmail());
        userRoleLabel.setText(user.getRole().toString());
        //todo remove null pointer exception, i think in initLabel the init is wrong but dont know how to do like a labelProperty or so, you need to initialize the label with the keyword new, but i haven't a clue how
    }

    @Subscribe
    public void handle(SuccessfullyChangedUserInformationEvent event) throws BusinessException {
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

