package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label doctoralCount;
    @FXML
    private PieChart pieChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        String name = "CurrentUsername";
        String DoctoralSummary = "CurrentDoctoralStudents";

        welcomeLabel.setText(welcomeLabel.getText() + name);
        doctoralCount.setText(doctoralCount.getText() + DoctoralSummary);


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Data1", 2),
                new PieChart.Data("Data2", 5),
                new PieChart.Data("Data3", 3),
                new PieChart.Data("Data4", 10));
        pieChart.setData(pieChartData);



    }

    @FXML
    public void handleOnActionAlertTestButton(ActionEvent event) {
        eventBus.post(new AlertNotificationEvent(1, "Ihre Mitgliedschaft endet bald!\r\nSie werden aus der Datenbank geloescht sofern sie innerhalb von XXX keinen Widerspruch einlegen"));
    }
}
