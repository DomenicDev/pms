package de.hfu.pms.controller;

import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DoctoralStudentMainContentController {

    private Logger logger = Logger.getLogger(DoctoralStudentMainContentController.class);

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
        this.overview = GuiLoader.loadFXML("/screens/doctoral_student_form_mask.fxml");

        // todo add more...

        JavaFxUtils.setAllAnchorPaneConstraints(overview, 0);

        doctoralStudentContentPane.getChildren().add(overview);

    }



}
