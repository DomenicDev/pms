package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.FacultyDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.RepresentationWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class StartScreenController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private Logger logger = Logger.getLogger(UniversityScreenController.class);
    private ResourceBundle bundle;
    private UserInfoDTO user;

    @FXML
    private Label welcomeLabel;

    @FXML
    private PieChart facultyPieChart;

    @FXML
    public void handleFelixHFUEvent() {

        try {
            Desktop.getDesktop().browse(new URI("https://felix.hs-furtwangen.de/dmz/"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleHFUWebmailEvent() {
        try {
            Desktop.getDesktop().browse(new URI("https://webmail.hs-furtwangen.de/SOGo/"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private Label DescriptionApplication;
    @FXML
    private Label headingTabelDescription;
    @FXML
    private Label DescriptionTable;
    @FXML
    private ListView<PreviewDoctoralStudentDTO> alertListView;
    @FXML
    private Label sddDoctoralStudentLabelStartscreen;

    @FXML
    private Label addUniversityLabelStartscreen;

    @FXML
    private Label changeAccountinfomrationLabelStartscreen;

    @FXML
    void handleAddDoctoralStudentButton(ActionEvent event) throws IOException{
        eventBus.post(new SwitchMainScreenEvent(DashboardController.MainScreen.DoctoralStudent));
        eventBus.post(new SwitchDoctoralStudentScreenEvent(DoctoralStudentMainContentController.DoctoralStudentScreen.FORM_MASK));
    }
    @FXML
    void handleAddUniversityButton()throws  IOException {
        GuiLoader.createModalWindow(GuiLoader.UNIVERSITY_FORM_SCREEN, 250, 300, false);

    }
    @FXML
    void handleChangeAccoutinformationButton(ActionEvent event)throws IOException {
        eventBus.post(new SwitchMainScreenEvent(DashboardController.MainScreen.AccountInformation));
    }

    ObservableList<PreviewDoctoralStudentDTO> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        eventBus.register(this);

        refreshGreetingLabel();

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



        facultyPieChart.setLabelsVisible(true);
        facultyPieChart.setLegendSide(Side.RIGHT);
        facultyPieChart.setLegendVisible(true);
        facultyPieChart.setTitle(bundle.getString("ui.label.title_faculty_pie_chart"));
        refreshPieChart();
    }

    private void refreshGreetingLabel() {
        try {
            String forename = EntityPool.getInstance().getLoggedInUser().getForename();
            String surname = EntityPool.getInstance().getLoggedInUser().getLastname();
            welcomeLabel.setText(bundle.getString("ui.label.welcome") + ", " + forename + " " + surname);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOnActionRefreshButton() {
        refreshPieChart();
    }

    private void refreshPieChart() {
        // fill pie chart data
        Collection<PreviewDoctoralStudentDTO> previews = EntityPool.getInstance().getPreviewStudents();
        Map<String, Integer> facultyRatio = new HashMap<>();
        for (PreviewDoctoralStudentDTO preview : previews) {
            FacultyDTO faculty = preview.getFaculty();
            if (faculty != null) {
                String name = faculty.getFacultyName();
                if (facultyRatio.containsKey(name)) {
                    facultyRatio.put(name, facultyRatio.get(name)+1);
                } else {
                    facultyRatio.put(name, 1);
                }
            }
        }

        facultyPieChart.getData().clear();
        for (Map.Entry<String, Integer> e : facultyRatio.entrySet()) {
            facultyPieChart.getData().add(new PieChart.Data(e.getKey(), e.getValue()));
        }
    }

    @Subscribe
    public void updateAlertListView(ShowAlertedDoctoralStudentsEvent event) {
        alertListView.getItems().clear();
        alertListView.getItems().addAll(event.getAlertedStudents());
    }

    @Subscribe
    public void handle(SuccessfullyUpdatedUserEvent event) {
        refreshGreetingLabel();
    }

    @FXML
    public void handleHomepage() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hs-furtwangen.de"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


