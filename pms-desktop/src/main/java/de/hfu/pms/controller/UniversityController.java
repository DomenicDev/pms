package de.hfu.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.net.URL;
import java.util.ResourceBundle;


public class UniversityController implements Initializable {

    private Logger logger = Logger.getLogger(UniversityController.class);

    @FXML
    private TableView<?> tableViewUniversity;

    @FXML
    private Button universityAddButton;

    @FXML
    void handleUniversityAddButton(ActionEvent event) {
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/Univerity_add_screen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Universität Hinzufügen");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the University add screen");
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
