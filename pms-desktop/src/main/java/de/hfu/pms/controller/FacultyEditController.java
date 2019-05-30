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
import java.util.*;

public class FacultyEditController implements Initializable {
    private EventBus eventBus = EventBusSystem.getEventBus();
    private static Logger logger = Logger.getLogger(EntityPool.class);

    private FacultyDTO faculty;

    @FXML
    private Label title;

    @FXML
    private ListView<FacultyDTO> facultiesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        // init list view with available faculties
        updateListView();
    }

    @FXML
    void handleAddButton(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Fakultät hinzufügen");
        dialog.setHeaderText("Sie sind dabei eine neue Fakultät anzulegen.");
        dialog.setContentText("Name der neuen Fakultät:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            if(!(result.get().isBlank() || result.get().isEmpty())){
                // add the new faculty
                FacultyDTO newFaculty = new FacultyDTO(result.get());
                EventBusSystem.getEventBus().post(new RequestAddFacultyEvent(newFaculty));
                return;
            }
            eventBus.post(new AlertNotificationEvent(1, "Bitte geben sie einen gültigen Fakultätsnamen ein"));
        }
    }

    @FXML
    void handleEditButton(ActionEvent event){
        if(facultiesListView.getSelectionModel().getSelectedItem() == null){
            eventBus.post(new AlertNotificationEvent(1, "Bitte wählen Sie die zu editierende Fakultät aus."));
            return;
        }

        TextInputDialog dialog = new TextInputDialog(facultiesListView.getSelectionModel().getSelectedItem().getFacultyName());
        dialog.setTitle("Fakultät bearbeiten");
        dialog.setHeaderText("Sie sind dabei eine Fakultät umzubenennen.");
        dialog.setContentText("Neuer Name der Fakultät:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            if(!(result.get().isBlank() || result.get().isEmpty())){
                // rename
                FacultyDTO faculty = facultiesListView.getSelectionModel().getSelectedItem();
                faculty.setFacultyName(result.get());

                EventBusSystem.getEventBus().post(new RequestUpdateFacultyEvent(faculty));
                return;
            }
            eventBus.post(new AlertNotificationEvent(1, "Bitte geben sie einen gültigen Fakultätsnamen ein"));
        }
        //updateListView();
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        FacultyDTO toBeDeleted = facultiesListView.getSelectionModel().getSelectedItem();

        if(facultiesListView.getSelectionModel().getSelectedItem() == null){
            eventBus.post(new AlertNotificationEvent(1, "Bitte wählen Sie die zu löschende Fakultät aus."));
            return;
        }

        //confirm dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fakultät löschen");
        alert.setHeaderText("Sie sind dabei folgende Fakultät zu löschen.");
        alert.setContentText(toBeDeleted.getFacultyName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // todo: delete selected Entry
            //EventBusSystem.getEventBus().post(new RequestDeleteFacultyEvent(toBeDeleted));
        }
        // else do nothing, closes automatically on cancel button;
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    private void updateListView() {
        facultiesListView.getItems().clear();
        facultiesListView.getItems().addAll(EntityPool.getInstance().getFaculties());

        logger.log(Level.DEBUG, "Currently known faculties:");
        for(FacultyDTO faculty : EntityPool.getInstance().getFaculties()){
            logger.log(Level.DEBUG, "-> " + faculty.getId() + ")" + faculty.getFacultyName());
        }
    }

    @Subscribe
    public void handleAddedFacultyEvent(SuccessfullyAddedFacultyEvent event){
        EntityPool.getInstance().addFaculty(event.getFaculty());
        updateListView();
    }

    @Subscribe
    public void handleDeletedFacultyEvent(SuccessfullyDeletedFacultyEvent event){
        EntityPool.getInstance().deleteFaculty(event.getFaculty());
        updateListView();
    }

    @Subscribe
    public void handleDeletedFacultyEvent(SuccessfullyUpdatedFacultyEvent event){
        // update list after the name was changed
        updateListView();
    }
}
