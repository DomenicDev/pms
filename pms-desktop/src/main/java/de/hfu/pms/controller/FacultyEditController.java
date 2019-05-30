package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.shared.dto.FacultyDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FacultyEditController {
    private EventBus eventBus = EventBusSystem.getEventBus();

    private FacultyDTO faculty;

    @FXML
    private Label title;


    @FXML
    void handleAddButton(ActionEvent event) {
        // todo: open new scene  with the option to enter a text and press confirm/cancel
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        // todo: delete selected faculty
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
