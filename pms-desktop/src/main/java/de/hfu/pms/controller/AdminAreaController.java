package de.hfu.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;


public class AdminAreaController {


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



}
