package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.events.RequestAddFacultyEvent;
import de.hfu.pms.events.SuccessfullyAddedFacultyEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.FacultyDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class FacultyEditController implements Initializable {
    private EventBus eventBus = EventBusSystem.getEventBus();

    private FacultyDTO faculty;

    @FXML
    private Label title;

    @FXML
    private ListView<FacultyDTO> facultiesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        // show all existing faculties in list

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
        }
        // else do nothing, closes automatically on cancel button;
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    private void updateListView() {
        facultiesListView.getItems().clear();

        Collection<FacultyDTO> faculties = new HashSet<>();
        faculties.addAll(EntityPool.getInstance().getFaculties());
        facultiesListView.getItems().addAll(faculties);

        for(FacultyDTO faculty : faculties){
            System.out.println("id: " + faculty.getId() + " name: " + faculty.getFacultyName());
        }
    }

    @Subscribe
    public void handleAddedFacultyEvent(SuccessfullyAddedFacultyEvent event){
        System.out.println("caught SuccessfullyAddedFacultyEvent in controller");
        System.out.println("(" + event.getFaculty().getId() + ")" + event.getFaculty().getFacultyName());
        EntityPool.getInstance().addFaculty(event.getFaculty());
        updateListView();
    }
}
