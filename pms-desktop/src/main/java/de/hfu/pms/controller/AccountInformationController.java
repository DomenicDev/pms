package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
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



    @FXML
    void handleChangeUserInformationButton(ActionEvent event) {
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/change_accountinformation_screen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Benutzerinformationen Ändern");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change account Infomrationscreen ");
        }


    }
    private void initLable(ResourceBundle resource){
        LabelUsername.setText("Username");
        labelForname.setText("Name");
        LableLastname.setText("Lastname");
        LableEmail.setText("Email");
        LableRole.setText("Role");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

    }
}

