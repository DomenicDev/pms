package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label doctoralCount;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        String name = "CurrentUsername";
        String DoctoralSummary = "CurrentDoctoralStudents";

        welcomeLabel.setText(welcomeLabel.getText() + name);
        doctoralCount.setText(doctoralCount.getText() + DoctoralSummary);

/*
        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("Test1", 2);
        PieChart.Data slice2 = new PieChart.Data("Test2", 4);
        PieChart.Data slice3 = new PieChart.Data("Test3", 3);
        PieChart.Data slice4 = new PieChart.Data("Test4", 6);
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);
        pieChart.getData().add(slice4);
        pieChart.setLegendSide(Side.BOTTOM);
        Stage stage = new Stage();

        stage.setTitle("Statistik");
        stage.setScene(new Scene(root);
*/

    }

    @FXML
    public void handleOnActionAlertTestButton(ActionEvent event) {
        eventBus.post(new AlertNotificationEvent(1, "Ihre Mitgliedschaft endet bald!\r\nSie werden aus der Datenbank geloescht sofern sie innerhalb von XXX keinen Widerspruch einlegen"));
    }
}
