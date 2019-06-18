package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.RequestAlertedDoctoralStudentEvent;
import de.hfu.pms.events.ShowAlertedDoctoralStudentsEvent;
import de.hfu.pms.events.SwitchDoctoralStudentScreenEvent;
import de.hfu.pms.events.SwitchMainScreenEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.JavaFxUtils;
import de.hfu.pms.utils.RepresentationWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;


public class StartscreenControlerApplication implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private Logger logger = Logger.getLogger(UniversityScreenController.class);
    private ResourceBundle bundle;
    private UserInfoDTO user;
    @FXML
    private Label welcomeLabel;

    @FXML
    public void handleFelixHFUEvent() {

        try {
            Desktop.getDesktop().browse(new URI("https://felix.hs-furtwangen.de/dmz/"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleHFUWebmailEvent() {
        try {
            Desktop.getDesktop().browse(new URI("https://webmail.hs-furtwangen.de/SOGo/"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private Label DescriptionApplication;
    @FXML
    private Label headingTabelDescription;
    @FXML
    private Label DescriptionTable;
    @FXML
    private ListView<PreviewDoctoralStudentDTO> alertListView;
    @FXML
    private Label sddDoctoralStudentLabelStartscreen;

    @FXML
    private Label addUniversityLabelStartscreen;

    @FXML
    private Label changeAccountinfomrationLabelStartscreen;

    private void switchMainContent(Parent parentToSwitchTo) {
        JavaFxUtils.setAllAnchorPaneConstraints(parentToSwitchTo, 0);
    }

    @FXML
    void handleAddDoctoralStudentButton(ActionEvent event) throws IOException{
        eventBus.post(new SwitchMainScreenEvent(DashboardController.MainScreen.DoctoralStudent));
        eventBus.post(new SwitchDoctoralStudentScreenEvent(DoctoralStudentMainContentController.DoctoralStudentScreen.FORM_MASK));
    }
    @FXML
    void handleAddUniversityButton()throws  IOException {
        GuiLoader.createModalWindow(GuiLoader.UNIVERSITY_FORM_SCREEN, 250, 300, false);

    }
    @FXML
    void handleChangeAccoutinformationButton(ActionEvent event)throws IOException {
        eventBus.post(new SwitchMainScreenEvent(DashboardController.MainScreen.AccountInformation));
    }

    ObservableList<PreviewDoctoralStudentDTO> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        try {
            String forname = EntityPool.getInstance().getLoggedInUser().getForename();
            String lastname = EntityPool.getInstance().getLoggedInUser().getLastname();
            welcomeLabel.setText(welcomeLabel.getText() + " " + forname + " " + lastname);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        // create cell factory for alert list view to customize representation
        alertListView.setCellFactory((param -> new ListCell<>() {

            @Override
            protected void updateItem(PreviewDoctoralStudentDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    return;
                }

                setText(RepresentationWrapper.getPreviewRepresentation(item));
            }
        }));

        // post event for receiving data for alert list view
        eventBus.post(new RequestAlertedDoctoralStudentEvent());

    }
    @Subscribe
    public void updateAlertListView(ShowAlertedDoctoralStudentsEvent event) {
        alertListView.getItems().clear();
        alertListView.getItems().addAll(event.getAlertedStudents());
    }


    @FXML
    public void handleHomepage() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hs-furtwangen.de"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


