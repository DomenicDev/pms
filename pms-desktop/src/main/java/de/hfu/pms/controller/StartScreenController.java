package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.FacultyDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.RepresentationWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This controller handles the start screen and its features
 * such as the pie chart (statistic) and the alert list.
 */
public class StartScreenController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private ResourceBundle bundle;

    @FXML
    private Label welcomeLabel;
    @FXML
    private PieChart facultyPieChart;
    @FXML
    private ListView<PreviewDoctoralStudentDTO> alertListView;

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
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(RepresentationWrapper.getPreviewRepresentation(item));
                }
            }
        }));

        refreshAlertTable();

        facultyPieChart.setLabelsVisible(true);
        facultyPieChart.setLegendSide(Side.RIGHT);
        facultyPieChart.setLegendVisible(true);
        facultyPieChart.setTitle(bundle.getString("ui.label.title_faculty_pie_chart"));
        refreshPieChart();
    }

    @FXML
    public void handleAddDoctoralStudentButton() {
        eventBus.post(new SwitchMainScreenEvent(DashboardController.MainScreen.DoctoralStudent));
        eventBus.post(new SwitchDoctoralStudentScreenEvent(DoctoralStudentMainContentController.DoctoralStudentScreen.FORM_MASK));
    }

    @FXML
    public void handleAddUniversityButton()throws  IOException {
        GuiLoader.createModalWindow(GuiLoader.UNIVERSITY_FORM_SCREEN, 250, 300, false);
    }

    @FXML
    public void handleChangeAccountInformationButton() {
        eventBus.post(new SwitchMainScreenEvent(DashboardController.MainScreen.AccountInformation));
    }

    @FXML
    public void handleOnActionRefreshAlertTableButton() {
        refreshAlertTable();
    }

    @FXML
    public void handleOnActionRefreshButton() {
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

    private void refreshPieChart() {
        // fill pie chart data
        Collection<PreviewDoctoralStudentDTO> previews = EntityPool.getInstance().getPreviewStudents();
        Map<String, Integer> facultyRatio = new HashMap<>();
        for (PreviewDoctoralStudentDTO preview : previews) {
            FacultyDTO faculty = preview.getFaculty();
            if (!preview.isActive() || preview.isAnonymized()) continue;
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

    private void refreshAlertTable() {
        eventBus.post(new RequestAlertedDoctoralStudentEvent());
    }

    @Subscribe
    public void updateAlertListView(ShowAlertedDoctoralStudentsEvent event) {
        alertListView.getItems().clear();
        alertListView.getItems().addAll(event.getAlertedStudents());
        alertListView.refresh();
    }

    @Subscribe
    public void handle(SuccessfullyUpdatedUserEvent event) {
        refreshGreetingLabel();
    }

}
