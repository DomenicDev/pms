package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SucessfullyAddedUserEvent;
import de.hfu.pms.shared.dto.UserDTO;
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

    @FXML
    private Label LableLastname;

    private Logger logger = Logger.getLogger(AccountInformationController.class);

    private UserDTO user;

    @FXML
    void handleChangeUserInformationButton(ActionEvent event) {
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/change_accountinformation_screen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
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

    private void initLable(ResourceBundle resources){

        LabelUsername.setText(user.getUsername());
        labelForname.setText(user.getForename());
        LableLastname.setText(user.getLastname());
        LableEmail.setText(user.getEmail());
        LableRole.setText(user.getRole().name());
    }

@Subscribe
public void handleUserChangeEvent(SucessfullyAddedUserEvent event){
        UserDTO user =event.getUser();
        LabelUsername.getText();
        LableLastname.getText();
        labelForname.getText();
        LableEmail.getText();
        LableRole.getText();
        //todo remove null pointer exception, i think in initLabel the init is wrong but dont know how to do like a labelProperty or so, you need to initialize the label with the keyword new, but i haven't a clue how
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        //initLable(resources);

    }
}

