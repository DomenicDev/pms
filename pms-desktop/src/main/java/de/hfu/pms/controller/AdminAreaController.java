package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class AdminAreaController implements Initializable {
    private EventBus eventBus = EventBusSystem.getEventBus();

       private Logger logger = Logger.getLogger(AdminAreaController.class);

        @FXML
        private Button ButtonTryChange;

        @FXML
        void handleChangeButton(ActionEvent event) {
            try {

                ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/AddUserAdminArea.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("BenutzerinformationenÄndern - Admin");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                logger.log(Level.ERROR, "Unable to load the Change User - AdminAread Screen ");
            }



        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

    }
}
