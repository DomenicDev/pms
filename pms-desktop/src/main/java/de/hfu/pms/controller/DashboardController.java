package de.hfu.pms.controller;

import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button startButton;

    @FXML
    private Button doctoralStudentsButton;

    @FXML
    private Button universityButton;

    @FXML
    private Button accountSettingsButton;

    @FXML
    private Button adminAreaButton;

    @FXML
    private Label mainContentTitle;

    @FXML
    private AnchorPane mainContentPane;

    // custom fields
    private Parent homeParent;
    private Parent doctoralStudentsParent;
    private Parent universitiesParent;

    @FXML
    public void initialize() throws IOException {
        // for the main content pane we need to load
        // all the separated fxml files
        // to later dynamically switch between them
        homeParent              = GuiLoader.loadFXML("/screens/home.fxml");
        doctoralStudentsParent  = GuiLoader.loadFXML("/screens/doctoral_students_content.fxml");
        // todo add more


        // set home to be showed at first
        switchMainContent(homeParent);
    }

    private void switchMainContent(Parent parentToShow) {
        // don't do anything if parent is already visible
        if (mainContentPane.getChildren().contains(parentToShow)) {
            return;
        }

        // clear children list and make the specified parent visible in the main content pane
        mainContentPane.getChildren().clear();
        mainContentPane.getChildren().add(parentToShow);

        // next we set all anchor pane constraint to zero
        // so that all the available width and height is being used
        JavaFxUtils.setAllAnchorPaneConstraints(parentToShow, 0);
    }

    @FXML
    public void handleOnActionStartButton() {
        switchMainContent(homeParent);
    }

    @FXML
    public void handleOnActionDoctoralStudentsButton() {
        switchMainContent(doctoralStudentsParent);
    }

}
