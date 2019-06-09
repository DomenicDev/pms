package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyAddedUserEvent;
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
    private Label LabelUsername;

    @FXML
    private Label labelForname;

    @FXML
    private Label LableEmail;

    @FXML
    private Label LableRole;

    private Logger logger = Logger.getLogger(AccountInformationController.class);

    private UserInfoDTO user;

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

    private void initLable() {

        LabelUsername.setText(user.getUsername());
        labelForname.setText(user.getForename()+" "+ user.getLastname());
        LableEmail.setText(user.getEmail());
        LableRole.setText(user.getRole().name());
    }

    public void showUser(UserInfoDTO user) {
        this.user = user;
        initLable();
    }

    @Subscribe
    public void handleUserChangeEvent(SuccessfullyAddedUserEvent event) {
        UserDTO user = event.getUser();
        LabelUsername.setText(user.getUsername());
        labelForname.setText(user.getForename()+" "+ user.getLastname());
        LableEmail.setText(user.getEmail());
        LableRole.setText(user.getRole().toString());
        //todo remove null pointer exception, i think in initLabel the init is wrong but dont know how to do like a labelProperty or so, you need to initialize the label with the keyword new, but i haven't a clue how
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
    }
}

