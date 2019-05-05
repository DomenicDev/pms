package de.hfu.pms.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private Button btnPersonalData;

    @FXML
    private Button btnGraduation;

    @FXML
    private Button btnEmplyoment;

    @FXML
    private Button btnPromotion;

    @FXML
    private Button btnDocuments;

    @FXML
    private AnchorPane apPersonalData;

    @FXML
    private AnchorPane apGraduation;

    @FXML
    private AnchorPane apEmployment;

    @FXML
    private AnchorPane apPromotion;

    @FXML
    private AnchorPane apDocuments;

    @FXML
    private AnchorPane apAddDoctoralStudents;

    @FXML
    private AnchorPane apDoctoralStudents;

    @FXML
    private AnchorPane apManageEmployes;

    @FXML
    private AnchorPane apAccount;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleClicks(javafx.event.ActionEvent actionEvent) {
       if (actionEvent.getSource() == btnAdd) {
            lblStatus.setText("Doktoranten Hinzufügen");
            apAddDoctoralStudents.toFront();

        } else if (actionEvent.getSource() == btnManageDoctoralStudents) {
            lblStatus.setText("Eingeschriebene Doktoranten ");
            apManageEmployes.toFront();

        } else if (actionEvent.getSource() == btnAccount) {
            lblStatus.setText("Account");
            apAccount.toFront();


        }   if (actionEvent.getSource() == btnDoctoralStudents) {
            lblStatus.setText("Doktoranten Übersicht");
            apDoctoralStudents.toFront();

        } else
            if (actionEvent.getSource() == btnPersonalData) {
                lblStatus.setText("Hinzufügen/Persöhnliche Daten");
                apPersonalData.toFront();

            } else
            if (actionEvent.getSource() == btnGraduation) {
                lblStatus.setText("Hinzufügen/Abschluss");
                apGraduation.toFront();

            } else
            if (actionEvent.getSource() == btnEmplyoment) {
                lblStatus.setText("Hinzufügen/Beschäftigung");
                apEmployment.toFront();

            } else
            if (actionEvent.getSource() == btnPromotion) {
                lblStatus.setText("Hinzufügen/Förderung");
                apPromotion.toFront();

            } else
            if (actionEvent.getSource() == btnDocuments) {
                lblStatus.setText("Hinzufügen/Dokumente");
                apDocuments.toFront();

            } else;

    }

    public void handleClose(javafx.scene.input.MouseEvent event) {
        if (event.getSource()==btnExit)
            Platform.exit();
    }
}
