package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.events.OnClickAddNewDoctoralStudentEvent;
import de.hfu.pms.events.ShowDoctoralStudentEvent;
import de.hfu.pms.events.SwitchDoctoralStudentScreenEvent;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller can dynamically switch between the overview screen
 * and the form mask.
 */
public class DoctoralStudentMainContentController implements Initializable {

    private Logger logger = Logger.getLogger(DoctoralStudentMainContentController.class);

    private static final String OVERVIEW_SCREEN = "/screens/doctoral_students_overview.fxml";
    private static final String FORM_MASK_SCREEN = "/screens/doctoral_student_form_mask.fxml";

    @FXML
    private Pane doctoralStudentContentPane;

    // custom loaded files
    private Parent overview;
    private Parent formMask;

    private EventBus eventBus = EventBusSystem.getEventBus();

    private ResourceBundle resourceBundle;

    public enum DoctoralStudentScreen {

        OVERVIEW,
        FORM_MASK

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.log(Level.INFO, "Initializing DoctoralStudentMainContentController");

        // register to eventBus
        eventBus.register(this);

        this.resourceBundle = resources;

        // load single content files
        // and add them to the stack pane
        // to later dynamically switch between them
        try {
            this.overview = GuiLoader.loadFXML(OVERVIEW_SCREEN);
            this.formMask = GuiLoader.loadFXML(FORM_MASK_SCREEN);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Could not load all screens: " + e);
        }


        // todo add more...

        JavaFxUtils.setAllAnchorPaneConstraints(overview, 0);

        doctoralStudentContentPane.getChildren().add(overview);

    }

    private void switchMainContent(Parent parentToSwitchTo) {
        doctoralStudentContentPane.getChildren().clear();
        doctoralStudentContentPane.getChildren().add(parentToSwitchTo);
        JavaFxUtils.setAllAnchorPaneConstraints(parentToSwitchTo, 0);
    }


    private void editDoctoralStudent(DoctoralStudentDTO doctoralStudentDTO) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_MASK_SCREEN));
        loader.setResources(resourceBundle);
        Parent parent = loader.load();
        switchMainContent(parent);

        // get controller
        DoctoralStudentFormController formController = loader.getController();
        formController.fillFormMask(doctoralStudentDTO);
    }

    @Subscribe
    public void handleEditRequest(ShowDoctoralStudentEvent event) throws IOException {
        DoctoralStudentDTO studentToShow = event.getDoctoralStudentDTO();
        editDoctoralStudent(studentToShow);
    }

    @Subscribe
    public void handle(OnClickAddNewDoctoralStudentEvent event) {
        try {
            switchMainContent(GuiLoader.loadFXML(GuiLoader.DOCTORAL_STUDENT_FORM_MASK));
        } catch (IOException e) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, resourceBundle.getString("ui.alert.screen_could_not_be_opened")));
        }
    }

    @Subscribe
    public void handle(SwitchDoctoralStudentScreenEvent event) {
        DoctoralStudentScreen screen = event.getScreen();
        if (screen == DoctoralStudentScreen.OVERVIEW) {
            switchMainContent(overview);
        } else if (screen == DoctoralStudentScreen.FORM_MASK) {
            switchMainContent(formMask);
        }
    }

}
