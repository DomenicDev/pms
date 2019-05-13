package de.hfu.pms.controller;

import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UniversityController {

    private Parent universityAddParent;
    @FXML
    private AnchorPane mainContentPane;

    public void initialize() throws IOException {
        universityAddParent = GuiLoader.loadFXML("/screens/Univerity_add_screen.fxml");
    }

    @FXML
    public void handleUniversityAddButton() {
        switchMainContent(universityAddParent);

    }

    private void switchMainContent(Parent parentToShow) {
        // don't do anything if parent is already visible
        if (mainContentPane.getChildren().contains(parentToShow)) {
            return;
        }
        mainContentPane.getChildren().clear();
        mainContentPane.getChildren().add(parentToShow);

    }
}
