package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.events.RequestAlertedDoctoralStudentEvent;
import de.hfu.pms.events.ShowAlertedDoctoralStudentsEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.FacultyDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.utils.RepresentationWrapper;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
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
    @FXML
    private TableView<PreviewDoctoralStudentDTO> personen;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> forname;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> lastname;
    @FXML
    private TextField searchBox;
    @FXML
    private ListView<PreviewDoctoralStudentDTO> alertListView;

    ObservableList<PreviewDoctoralStudentDTO> masterData = FXCollections.observableArrayList();

    Hyperlink hyperlink = new Hyperlink("HFU-Homepage");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        try {
            String forname = EntityPool.getInstance().getLoggedInUser().getForename();
            String lastname = EntityPool.getInstance().getLoggedInUser().getLastname();
            welcomeLabel.setText(welcomeLabel.getText() + " " + forname + " " + lastname);
        } catch (BusinessException e) {
            e.printStackTrace();
        }


        int DoctoralSummary = EntityPool.getInstance().getDoctoralStudents().size();
        doctoralCount.setText(doctoralCount.getText() + DoctoralSummary);


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Data1", 2),
                new PieChart.Data("Data2", 5),
                new PieChart.Data("Data3", 3),
                new PieChart.Data("Data4", 10));
        pieChart.setData(pieChartData);


        // create cell factory for alert list view to customize representation
        alertListView.setCellFactory((param -> new ListCell<>() {

            @Override
            protected void updateItem(PreviewDoctoralStudentDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    return;
                }

                setText(RepresentationWrapper.getPreviewRepresentation(item));
            }
        }));

        // post event for receiving data for alert list view
        eventBus.post(new RequestAlertedDoctoralStudentEvent());


        //Suchfunktion

/*
        FacultyDTO faculty = new FacultyDTO((long) 1, "Informatik");
        // PreviewDoctoralStudentDTO student1 = new PreviewDoctoralStudentDTO(500L, "Jahnsen", "Jan", faculty, "jan.jahnsen@mail.com", "017322497814", Gender.Male);
        //PreviewDoctoralStudentDTO student2 = new PreviewDoctoralStudentDTO(501L, "Fröhlich", "Alina", faculty, "ali.fr@mail.com", "015121639248", Gender.Female);
        //PreviewDoctoralStudentDTO student3 = new PreviewDoctoralStudentDTO(500L, "test", "test", faculty, "jan.jahnsen@mail.com", "017322497814", Gender.Male);
        PreviewDoctoralStudentDTO student4 = new PreviewDoctoralStudentDTO("ilker", "coban");
        PreviewDoctoralStudentDTO student5 = new PreviewDoctoralStudentDTO("test", "test");
        // masterData.add(student1);
        // masterData.add(student2);
        // masterData.add(student3);
        masterData.add(student4);
        masterData.add(student5);

        personen.setItems(masterData);
        forname.setCellValueFactory(new PropertyValueFactory<>("foreName"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("name"));

*/
    }

    @Subscribe
    public void updateAlertListView(ShowAlertedDoctoralStudentsEvent event) {
        alertListView.getItems().clear();
        alertListView.getItems().addAll(event.getAlertedStudents());
    }


    @FXML
    public void handleOnActionAlertTestButton(ActionEvent event) {
        eventBus.post(new AlertNotificationEvent(1, "Ihre Mitgliedschaft endet bald!\r\nSie werden aus der Datenbank geloescht sofern sie innerhalb von XXX keinen Widerspruch einlegen"));
    }

    @FXML
    public void handleHomepage() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hs-furtwangen.de"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


/*
    @FXML
    public void searchRecord() {
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
