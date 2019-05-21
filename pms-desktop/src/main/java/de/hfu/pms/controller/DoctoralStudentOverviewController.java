package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class DoctoralStudentOverviewController {

    @FXML
    private TableView<PreviewDoctoralStudentDTO> searchResultTableView;

    @FXML
    public void handleOnActionDeleteButton(ActionEvent event) {

    }

    @FXML
    public void handleOnActionEditButton(ActionEvent event) {

    }

    @FXML
    public void handleOnActionSearchButton(ActionEvent event) {
        //Object property = event.getProperty();
        //searchResultTableView.getItems().add((DoctoralStudentDTO) property);
    }

}
