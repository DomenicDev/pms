package de.hfu.pms.controller;

import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DoctoralStudentMainContentController {

    private Logger logger = Logger.getLogger(DoctoralStudentMainContentController.class);

    private static final String OVERVIEW_SCREEN = "/screens/doctoral_students_overview.fxml";
    private static final String FORM_MASK_SCREEN = "/screens/doctoral_student_form_mask.fxml";

    @FXML
    private Pane doctoralStudentContentPane;

    // custom loaded files
    private Parent overview;
    private Parent formMask;


    @FXML
    public void initialize() throws IOException {
        logger.log(Level.INFO, "Initializing DoctoralStudentMainContentController");

        // load single content files
        // and add them to the stack pane
        // to later dynamically switch between them
        this.overview = GuiLoader.loadFXML(OVERVIEW_SCREEN);
        this.formMask = GuiLoader.loadFXML(FORM_MASK_SCREEN);

        // todo add more...

        JavaFxUtils.setAllAnchorPaneConstraints(overview, 0);

        doctoralStudentContentPane.getChildren().add(overview);

    }

    private void switchMainContent(Parent parentToSwitchTo) {
        doctoralStudentContentPane.getChildren().clear();
        doctoralStudentContentPane.getChildren().add(parentToSwitchTo);
        JavaFxUtils.setAllAnchorPaneConstraints(parentToSwitchTo, 0);
    }

    @FXML
    public void handleOnActionOverviewButton(ActionEvent event) {
        switchMainContent(overview);
    }

    @FXML
    public void handleOnActionAddButton(ActionEvent event) {
        switchMainContent(formMask);
    }

    @FXML
    public void handleOnActionEditButton(ActionEvent event) {

    }



}
