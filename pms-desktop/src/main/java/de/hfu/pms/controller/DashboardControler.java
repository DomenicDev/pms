package de.hfu.pms.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardControler implements Initializable {

    @FXML
    private Button btnDoctoralStudents;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnManageDoctoralStudents;

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleClicks(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDoctoralStudents) {
            lblStatus.setText("Doktoranten Übersicht");

        } else if (actionEvent.getSource() == btnAdd) {
            lblStatus.setText("Doktoranten Hinzufügen");

        } else if (actionEvent.getSource() == btnManageDoctoralStudents) {
            lblStatus.setText("Eingeschriebene Doktoranten ");

        } else if (actionEvent.getSource() == btnAccount) {
            lblStatus.setText("Account");

        } else ;

    }

    public void handleClose(javafx.scene.input.MouseEvent event) {
        if (event.getSource()==btnExit)
            Platform.exit();
    }
}
