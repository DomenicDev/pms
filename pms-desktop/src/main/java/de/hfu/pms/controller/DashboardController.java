package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.ShowDoctoralStudentEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

    // we store a reference to the last focused button of the dashboard
    private Button lastFocusedButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;

        eventBus.register(this);
        // for the main content pane we need to load
        // all the separated fxml files
        // to later dynamically switch between them
        try {
            homeParent = GuiLoader.loadFXML("/screens/home.fxml");
            doctoralStudentsParent = GuiLoader.loadFXML("/screens/doctoral_students_content.fxml");
            universitiesParent = GuiLoader.loadFXML("/screens/university_screen.fxml");
            adminArea = GuiLoader.loadFXML("/screens/admin_Area.fxml");

            // account information
            // accountInformationParent = GuiLoader.loadFXML("/screens/account_infoscreen.fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GuiLoader.ACCOUNT_INFORMATION));
            loader.setResources(GuiLoader.getResourceBundle());
            this.accountInformationParent = loader.load();

            AccountInformationController accountInformationController = loader.getController();
            accountInformationController.showUser(EntityPool.getInstance().getLoggedInUser());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        // set home to be showed at first
        switchScreen(startButton, bundle.getString("ui.section.home"), homeParent);

        try {
            UserInfoDTO user = EntityPool.getInstance().getLoggedInUser();
            if (user.getRole().equals(ADMIN)) {
                adminAreaButton.setDisable(false);
            } else {
                adminAreaButton.setDisable(true);
            }
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
        switchScreen(startButton, bundle.getString("ui.section.home"), homeParent);
    }

    @FXML
    public void handleOnActionDoctoralStudentsButton() {
        switchScreen(doctoralStudentsButton, bundle.getString("ui.section.doctoral_students"), doctoralStudentsParent);
    }

    @FXML
    public void handleUniversityButton() {
        switchScreen(universityButton, bundle.getString("ui.section.universities"), universitiesParent);
    }

    @FXML
    public void handleAccountInformationButton() {
        switchScreen(accountSettingsButton, bundle.getString("ui.section.account_settings"), accountInformationParent);
    }
    @FXML
    public void handleAdminArea() {
        switchScreen(adminAreaButton, bundle.getString("ui.section.admin_area"), adminArea);
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
