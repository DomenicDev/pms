package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.shared.dto.FacultyDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.enums.Gender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label doctoralCount;
    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<PreviewDoctoralStudentDTO> personen;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> forname;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> lastname;
    @FXML
    private TextField searchBox;

    ObservableList<PreviewDoctoralStudentDTO> masterData = FXCollections.observableArrayList();

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


        //Suchfunktion


        FacultyDTO faculty = new FacultyDTO((long) 1, "Informatik");
        // PreviewDoctoralStudentDTO student1 = new PreviewDoctoralStudentDTO(500L, "Jahnsen", "Jan", faculty, "jan.jahnsen@mail.com", "017322497814", Gender.Male);
        //PreviewDoctoralStudentDTO student2 = new PreviewDoctoralStudentDTO(501L, "Fröhlich", "Alina", faculty, "ali.fr@mail.com", "015121639248", Gender.Female);
        //PreviewDoctoralStudentDTO student3 = new PreviewDoctoralStudentDTO(500L, "test", "test", faculty, "jan.jahnsen@mail.com", "017322497814", Gender.Male);
        //  PreviewDoctoralStudentDTO student4 = new PreviewDoctoralStudentDTO("ilker","coban");
        // masterData.add(student1);
        // masterData.add(student2);
        // masterData.add(student3);
        // masterData.add(student4);

        personen.setItems(masterData);
        forname.setCellValueFactory(new PropertyValueFactory<>("foreName"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("name"));


    }


    @FXML
    public void handleOnActionAlertTestButton(ActionEvent event) {
        eventBus.post(new AlertNotificationEvent(1, "Ihre Mitgliedschaft endet bald!\r\nSie werden aus der Datenbank geloescht sofern sie innerhalb von XXX keinen Widerspruch einlegen"));
    }
/*
    @FXML
    public void searchRecord(KeyEvent ke) {
        FilteredList<PreviewDoctoralStudentDTO> filteredData = new FilteredList<>(masterData, p -> true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pers -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String input = newValue.toLowerCase();
                if (pers.getForeName().toLowerCase().indexOf(input) != -1) {
                    return true;
                }
                if (pers.getName().toLowerCase().indexOf(input) != -1) {
                    return true;
                }
                return false;
            });
            SortedList<PreviewDoctoralStudentDTO> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(personen.comparatorProperty());
            personen.setItems(sortedList);
        });

    }
*/
}
