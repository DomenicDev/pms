package de.hfu.pms.handler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
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
        // we check if a new entity has been created or if an existing one has been edited
        if (doctoralStudent.getId() == null) {
            DoctoralStudentDTO createdStudent = applicationServices.addDoctoralStudent(doctoralStudent);
            if (createdStudent != null) {
                eventBus.post(new SuccessfullyAddedDoctoralStudentEvent(createdStudent));
            }
        } else {
            // we are editing an already existing entity
            try {
                DoctoralStudentDTO updatedStudent = applicationServices.editDoctoralStudent(doctoralStudent);
                // we inform about the successful update
                eventBus.post(new SuccessfullyUpdatedDoctoralStudentEvent(updatedStudent));
            } catch (BusinessException e) {
                e.printStackTrace();
            }

        }

    }

    @Subscribe
    public void handleAddUniversityEvent(RequestAddUniversityEvent requestSaveEvent){
        UniversityDTO universityDTO = requestSaveEvent.getUniversity();
        applicationServices.addUniversity(universityDTO);
    }

    @Subscribe
    public void handleUpdateUniversityEvent(RequestUpdateUniversityEvent requestUpdateUniversityEvent){
        UniversityDTO universityDTO = requestUpdateUniversityEvent.getUniversity();
        applicationServices.updateUniversity(universityDTO.getId(), universityDTO);
    }

    @Subscribe
    public void handleAddUserEvent(RequestAddUserEvent requestAddUserEvent){
        UserDTO userDTO = requestAddUserEvent.getUser();
        applicationServices.addUser(userDTO);
    }

    @Subscribe
    public void handlePasswordChange(RequestChangePasswordEvent requestChangePasswordEvent){
        UserDTO userDTO = requestChangePasswordEvent.getUser();
        String newPassword = requestChangePasswordEvent.getNewPassword();
        applicationServices.changePassword(userDTO,newPassword);
    }

    @Subscribe
    public void handleRoleChange(RequestChangeUserRoleEvent requestChangeUserRoleEvent){
        UserDTO userDTO = requestChangeUserRoleEvent.getUser();
        applicationServices.changeUserPrivileges(userDTO.getUsername(),userDTO.getRole());
    }

    @Subscribe
    public void handleEvent(OnClickEditDoctoralStudentEvent event) {
        Long id = event.getId();
        DoctoralStudentDTO doctoralStudentDTO = null;
        try {
            doctoralStudentDTO = applicationServices.getDoctoralStudent(id);
        } catch (IOException e) {
            // todo handle properly
            e.printStackTrace();
        }

        // load and start doc student form mask and fill with data
        eventBus.post(new ShowDoctoralStudentEvent(doctoralStudentDTO));
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
