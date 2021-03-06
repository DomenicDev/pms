package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.ShowDoctoralStudentEvent;
import de.hfu.pms.events.SwitchMainScreenEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static de.hfu.pms.shared.enums.UserRole.ADMIN;

public class DashboardController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    private ResourceBundle bundle;

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
    private Parent accountInformationParent;
    private Parent adminArea;
    private Logger logger = Logger.getLogger(DashboardController.class);

    // current screen
    private MainScreen currentShownScreen = null;

    // controller
    private DoctoralStudentMainContentController doctoralStudentMainContentController;

    // we store a reference to the last focused button of the dashboard
    private Button lastFocusedButton;

    /**
     * This enum contains all screens this controller can show.
     */
    public enum MainScreen {
        Home,
        DoctoralStudent,
        University,
        AccountInformation,
        AdminArea
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;

        eventBus.register(this);
        // for the main content pane we need to load
        // all the separated fxml files
        // to later dynamically switch between them
        try {
            homeParent = GuiLoader.loadFXML("/screens/startscreen.fxml");

            // prepare doctoral student controller
            FXMLLoader docLoader = new FXMLLoader(getClass().getResource("/screens/doctoral_students_content.fxml"));
            docLoader.setResources(GuiLoader.getResourceBundle());
            this.doctoralStudentsParent = docLoader.load();
            this.doctoralStudentMainContentController = docLoader.getController();

            //doctoralStudentsParent = GuiLoader.loadFXML("/screens/doctoral_students_content.fxml");
            universitiesParent = GuiLoader.loadFXML("/screens/university_screen.fxml");
            adminArea = GuiLoader.loadFXML("/screens/admin_Area.fxml");

            // account information
            // accountInformationParent = GuiLoader.loadFXML("/screens/account_infoscreen.fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GuiLoader.ACCOUNT_INFORMATION));
            loader.setResources(GuiLoader.getResourceBundle());
            this.accountInformationParent = loader.load();

            AccountInformationController accountInformationController = loader.getController();
            accountInformationController.showUser(EntityPool.getInstance().getLoggedInUser());


            logger.log(Level.DEBUG, "Finished initializing screens...");
        } catch (IOException | BusinessException e) {
            logger.log(Level.DEBUG, "Error while initializing: " + e);
        }

        // set home to be showed at first
        show(MainScreen.Home);

        try {
            UserInfoDTO user = EntityPool.getInstance().getLoggedInUser();
            adminAreaButton.setDisable(!user.getRole().equals(ADMIN));
        }catch (BusinessException e){
            e.printStackTrace();
        }
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

    private void setTitle(String title) {
        if (title == null) {
            return;
        }
        mainContentTitle.setText(title);
    }

    private void switchScreen(Button origin, String title, Parent parent) {
        if (origin == null || title == null || parent == null) {
            return;
        }
        setFocusedButton(origin);
        setTitle(title);
        switchMainContent(parent);
    }

    @FXML
    public void handleOnActionStartButton() {
        show(MainScreen.Home);
    }

    @FXML
    public void handleOnActionDoctoralStudentsButton() {
        show(MainScreen.DoctoralStudent);
    }

    @FXML
    public void handleUniversityButton() {
        show(MainScreen.University);
    }

    @FXML
    public void handleAccountInformationButton() {
        show(MainScreen.AccountInformation);
    }
    @FXML
    public void handleAdminArea() {
        show(MainScreen.AdminArea);
    }

    @FXML
    public void handleOnActionCloseMenuItem() {
        Platform.exit();
    }

    @FXML
    public void handleOnActionAboutMenuItem() throws IOException {
        GuiLoader.createAndShow(GuiLoader.ABOUT_SCREEN, bundle.getString("ui.label.about_title"), true, false);
    }

    private void show(MainScreen screen) {
        if (screen == null) {
            return;
        }

        // this is a hard coded check, if the doctoral student form mask
        // is currently opened. If so, we want to the user to ask if the screen
        // shall really be left with the hint that unsaved changes are not submitted.
        // This could be in a much cleaner way, e.g. by using a interface for common
        // methods which is implemented in the single controllers, so the controller
        // itself could be asked if a switch is okay... but time ran away ;-)
        if (this.currentShownScreen == MainScreen.DoctoralStudent) {
            if (doctoralStudentMainContentController.isInFormMask()) {
                GuiLoader.showYesAndNoAlert(Alert.AlertType.WARNING,
                        bundle.getString("ui.alert.title.switch_screen_hint"),
                        bundle.getString("ui.alert.content.switch_screen_hint"),
                        bundle.getString("ui.alert.button.yes_exit"),
                        bundle.getString("ui.alert.button.no_stay"),
                        () -> showReally(screen),
                        null);
            } else {
                showReally(screen);
            }
        } else {
            showReally(screen);
        }

    }

    private void showReally(MainScreen screen) {
        this.currentShownScreen = screen;
        if (screen == MainScreen.Home) {
            switchScreen(startButton, bundle.getString("ui.section.home"), homeParent);
        } else if (screen == MainScreen.DoctoralStudent) {
            switchScreen(doctoralStudentsButton, bundle.getString("ui.section.doctoral_students"), doctoralStudentsParent);
            doctoralStudentMainContentController.switchScreen(DoctoralStudentMainContentController.DoctoralStudentScreen.OVERVIEW);
        } else if (screen == MainScreen.University) {
            switchScreen(universityButton, bundle.getString("ui.section.universities"), universitiesParent);
        } else if (screen == MainScreen.AccountInformation) {
            switchScreen(accountSettingsButton, bundle.getString("ui.section.account_settings"), accountInformationParent);
        } else if (screen == MainScreen.AdminArea) {
            switchScreen(adminAreaButton, bundle.getString("ui.section.admin_area"), adminArea);
        }
    }

    @Subscribe
    public void onEdit(ShowDoctoralStudentEvent event) {
        // if the user wants to edit a doctoral student we need
        // to make sure to switch to the specific content screen.
        // This is important if the call of this event comes
        // from another screen than this one
        show(MainScreen.DoctoralStudent);
    }

    @Subscribe
    public void handle(SwitchMainScreenEvent event) {
        show(event.getScreen());
    }

}
