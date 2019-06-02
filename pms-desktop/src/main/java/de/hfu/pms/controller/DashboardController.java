package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.ShowDoctoralStudentEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DashboardController {

    private EventBus eventBus = EventBusSystem.getEventBus();

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

    @FXML
    private Button changeUserInformationButton;

    @FXML
    private Button universityAddButton;

    // custom fields
    private Parent homeParent;
    private Parent doctoralStudentsParent;
    private Parent universitiesParent;
    private Parent accountInformationParent;
    private Parent adminArea;
    private Logger logger = Logger.getLogger(DashboardController.class);

    // we store a reference to the last focused button of the dashboard
    private Button lastFocusedButton;

    @FXML
    public void initialize() throws IOException {
        eventBus.register(this);
        // for the main content pane we need to load
        // all the separated fxml files
        // to later dynamically switch between them
        homeParent = GuiLoader.loadFXML("/screens/home.fxml");
        doctoralStudentsParent = GuiLoader.loadFXML("/screens/doctoral_students_content.fxml");
        universitiesParent = GuiLoader.loadFXML("/screens/university_screen.fxml");


        // account information
        // accountInformationParent = GuiLoader.loadFXML("/screens/account_infoscreen.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GuiLoader.ACCOUNT_INFORMATION));
        loader.setResources(GuiLoader.getResourceBundle());
        this.accountInformationParent = loader.load();

        AccountInformationController accountInformationController = loader.getController();
        accountInformationController.showUser(EntityPool.getInstance().getLoggedInUser());


        adminArea =GuiLoader.loadFXML("/screens/admin_Area.fxml");

        // todo add more


        // set home to be showed at first
        this.lastFocusedButton = startButton;
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

    private void setFocusedButton(Button button) {
        if (button == null) {
            return;
        }
        if (lastFocusedButton != null) {
            lastFocusedButton.getStyleClass().remove("selected");
        }
        button.getStyleClass().add("selected");
        this.lastFocusedButton = button;
    }

    @FXML
    public void handleOnActionStartButton() {
        setFocusedButton(startButton);
        switchMainContent(homeParent);
    }

    @FXML
    public void handleOnActionDoctoralStudentsButton() {
        setFocusedButton(doctoralStudentsButton);
        switchMainContent(doctoralStudentsParent);
    }

    @FXML
    public void handleUniversityButton() {
        setFocusedButton(universityButton);
        switchMainContent(universitiesParent);
    }

    @FXML
    public void handleAccountnformationButton() {
        setFocusedButton(accountSettingsButton);
        switchMainContent(accountInformationParent);
    }
    @FXML
    public void handleAdminArea() {
        setFocusedButton(adminAreaButton);
        switchMainContent(adminArea);

    }

    @Subscribe
    public void onEdit(ShowDoctoralStudentEvent event) {
        // if the user wants to edit a doctoral student we need
        // to make sure to switch to the specific content screen.
        // This is important if the call of this event comes
        // from another screen than this one
        switchMainContent(doctoralStudentsParent);
    }
}
