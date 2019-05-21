package de.hfu.pms.controller;

import de.hfu.pms.utils.GuiLoader;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;




public class HomeController extends Application {
    private Parent home;
    private Button AlertTestButton = new Button("Alert Test");


    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText("Ihre Mitgliedschaft endet bald!");
        alert.setContentText("Sie werden aus der Datenbank geloescht sofern sie innerhalb von XXX keinen Widerspruch einlegen");
        alert.showAndWait();
    }

    public void start(Stage stage){



    }



    @FXML
    public void initialize() throws IOException {
        home = GuiLoader.loadFXML("screens/home.fxml");

    }


}
