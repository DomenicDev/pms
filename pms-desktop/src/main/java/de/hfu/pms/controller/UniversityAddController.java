package de.hfu.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class UniversityAddController {


    @FXML
    private TextField textFieldNameOfUniverity;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldCountry;

    @FXML
    private TextField textFieldShortForm;

    @FXML
    void handleAddButton(ActionEvent event) {
        String name = textFieldNameOfUniverity.getText();
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }
}
