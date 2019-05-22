package de.hfu.pms.handler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.shared.dto.UserDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

public class GuiEventHandler {

    private EventBus eventBus;

    private ApplicationServices applicationServices;

    private Stage primaryStage;

    private HashMap<String, Node> scenes = new HashMap<>();

    public GuiEventHandler(Stage primaryStage, ApplicationServices applicationServices, EventBus eventBus) {
        this.primaryStage = primaryStage;

        this.eventBus = eventBus;
        this.eventBus.register(this);

        this.applicationServices = applicationServices;
    }

    @Subscribe
    public void handleLoginEvent(LoginRequestEvent loginRequestEvent) {
        // extract credentials from request
        String username = loginRequestEvent.getUsername();
        String password = loginRequestEvent.getPwHash();

        try {
            // try to login with specified username and password
            //applicationServices.login(username, password);

            // login was successful, so we can close the login screen and open the dashboard
            primaryStage.close();

            // todo: replace the following event with event
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/dashboard_final.fxml"));
            try {
                Stage newStage = new Stage(StageStyle.DECORATED);

                Parent dashboard = loader.load();
                Scene scene = new Scene(dashboard);
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (LoginFailedException e) {
            // todo: show prompt which shows error message failed login
            e.printStackTrace();
        }
    }

    @Subscribe
    public void handleSaveDoctoralStudentEvent(SaveDoctoralStudentEvent saveEvent) {
        DoctoralStudentDTO doctoralStudent = saveEvent.getDoctoralStudent();
        applicationServices.addDoctoralStudent(doctoralStudent);
    }

    @Subscribe
    public void handleSaveUniversityEvent(RequestAddUniversityEvent requestSaveEvent){
        UniversityDTO universityDTO = requestSaveEvent.getUniversity();
        applicationServices.addUniversity(universityDTO);
    }

    @Subscribe
    public void handleUpdateUniversityEvent(RequestUpdateUniversityEvent requestUpdateUniversityEvent){
        UniversityDTO universityDTO = requestUpdateUniversityEvent.getUniversity();
        applicationServices.updateUniversity(universityDTO.getId(), universityDTO);
    }

    public void handleSaveUserEvent(){
        //TODO: unfinished
        UserDTO userDTO = null;
        applicationServices.addUser(userDTO);
    }

    @Subscribe
    public void handleNotificationEvent(AlertNotificationEvent event) {
        Alert.AlertType type;

        // map event type to alert type
        switch (event.getType()) {
            case AlertNotificationEvent.INFO:
                type = Alert.AlertType.INFORMATION;
                break;
            case AlertNotificationEvent.ERROR:
                type = Alert.AlertType.ERROR;
                break;
            case AlertNotificationEvent.WARNING:
                type = Alert.AlertType.WARNING;
                break;
            default:
                type = Alert.AlertType.NONE;
        }

        Alert alert = new Alert(type, event.getMessage(), ButtonType.OK);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }

}
