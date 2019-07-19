package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.FacultyDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for adding, editing, and deleting faculties.
 */
public class FacultyEditController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private static Logger logger = Logger.getLogger(FacultyEditController.class);
    private ResourceBundle bundle;

    @FXML
    private ListView<FacultyDTO> facultiesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        this.bundle = resources;

        // init list view with available faculties
        updateListView();
    }

    @FXML
    public void handleAddButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("ui.label.header.addFaculty"));
        dialog.setHeaderText(bundle.getString("ui.label.content.addFaculty"));
        dialog.setContentText(bundle.getString("ui.label.name.addFaculty"));
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (!(result.get().isBlank() || result.get().isEmpty())) {
                // add the new faculty
                FacultyDTO newFaculty = new FacultyDTO(result.get());
                EventBusSystem.getEventBus().post(new RequestAddFacultyEvent(newFaculty));
                return;
            }
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.addFaculty")));
        }
    }

    @FXML
    public void handleEditButton() {
        if (facultiesListView.getSelectionModel().getSelectedItem() == null) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.editFaculty")));
            return;
        }

        TextInputDialog dialog = new TextInputDialog(facultiesListView.getSelectionModel().getSelectedItem().getFacultyName());
        dialog.setTitle(bundle.getString("ui.label.header.editFaculty"));
        dialog.setHeaderText(bundle.getString("ui.label.content.editFaculty"));
        dialog.setContentText(bundle.getString("ui.label.name.editFaculty"));
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (!(result.get().isBlank() || result.get().isEmpty())) {
                // rename
                FacultyDTO faculty = facultiesListView.getSelectionModel().getSelectedItem();
                faculty.setFacultyName(result.get());

                EventBusSystem.getEventBus().post(new RequestUpdateFacultyEvent(faculty));
                return;
            }
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.addFaculty")));
        }
        //updateListView();
    }

    @FXML
    public void handleDeleteButton() {
        FacultyDTO toBeDeleted = facultiesListView.getSelectionModel().getSelectedItem();

        if (facultiesListView.getSelectionModel().getSelectedItem() == null) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.deleteFaculty_selected")));
            return;
        }

        //confirm dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("ui.alert.deleteFaculty"));
        alert.setHeaderText(bundle.getString("ui.alert.delete_finally_faculty"));
        alert.setContentText(toBeDeleted.getFacultyName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // todo: delete selected Entry
            eventBus.post(new RequestDeleteFacultyEvent(toBeDeleted.getId()));
            //EventBusSystem.getEventBus().post(new RequestDeleteFacultyEvent(toBeDeleted));
        }
        // else do nothing, closes automatically on cancel button;
    }

    @FXML
    public void handleCancelButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    private void updateListView() {
        facultiesListView.getItems().clear();
        facultiesListView.getItems().addAll(EntityPool.getInstance().getFaculties());

        StringBuilder logMessage = new StringBuilder("Currently known faculties: ");
        for (FacultyDTO faculty : EntityPool.getInstance().getFaculties()) {
            logMessage.append("[").append(faculty.getId()).append(":").append(faculty.getFacultyName()).append("]");
        }
        logger.log(Level.DEBUG, logMessage.toString());
    }

    @Subscribe
    public void handleAddedFacultyEvent(SuccessfullyAddedFacultyEvent event) {
        updateListView();
    }

    @Subscribe
    public void handleDeletedFacultyEvent(SuccessfullyDeletedFacultyEvent event) {
        updateListView();
    }

    @Subscribe
    public void handleDeletedFacultyEvent(SuccessfullyUpdatedFacultyEvent event) {
        updateListView();
    }
}
