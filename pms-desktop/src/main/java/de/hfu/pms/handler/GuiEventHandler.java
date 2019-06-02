package de.hfu.pms.handler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.shared.dto.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

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
    public void handleCreateDoctoralStudentEvent(RequestCreateDoctoralStudentEvent saveEvent) {
        CreateDoctoralStudentDTO doctoralStudent = saveEvent.getCreateDoctoralStudentDTO();

        try {
            DoctoralStudentDTO createdEntity = applicationServices.addDoctoralStudent(doctoralStudent);
            eventBus.post(new SuccessfullyAddedDoctoralStudentEvent(createdEntity));
        } catch (BusinessException e) {
            e.printStackTrace();
        }


        /*
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

         */
    }

    @Subscribe
    public void handleAddUniversityEvent(RequestAddUniversityEvent requestSaveEvent){
        UniversityDTO universityDTO = requestSaveEvent.getUniversity();
        UniversityDTO response = applicationServices.addUniversity(universityDTO);
        eventBus.post(new SuccessfullyAddedUniversityEvent(response));
    }

    @Subscribe
    public void handleUpdateUniversityEvent(RequestUpdateUniversityEvent requestUpdateUniversityEvent){
        UniversityDTO universityDTO = requestUpdateUniversityEvent.getUniversity();
        UniversityDTO response = applicationServices.updateUniversity(universityDTO.getId(), universityDTO);
        eventBus.post(new SuccessfullyUpdatedUniversityEvent(response));
    }

    // todo: delete university

    @Subscribe
    public void handleAddFacultyEvent(RequestAddFacultyEvent requestSaveEvent){
        FacultyDTO facultyDTO = requestSaveEvent.getFaculty();
        FacultyDTO response = applicationServices.addFaculty(facultyDTO);
        EntityPool.getInstance().addFaculty(facultyDTO);
        eventBus.post(new SuccessfullyAddedFacultyEvent(response));
    }

    @Subscribe
    public void handleUpdateFacultyEvent(RequestUpdateFacultyEvent requestUpdateFacultyEvent){
        FacultyDTO facultyDTO = requestUpdateFacultyEvent.getFaculty();
        FacultyDTO response = applicationServices.updateFaculty(facultyDTO);
        eventBus.post(new SuccessfullyUpdatedFacultyEvent(response));
    }

    @Subscribe
    public void handleDeleteFacultyEvent(RequestDeleteFacultyEvent requestDeleteEvent){
        FacultyDTO facultyDTO = requestDeleteEvent.getFaculty();
        FacultyDTO response = applicationServices.deleteFaculty(facultyDTO);
        eventBus.post(new SuccessfullyDeletedFacultyEvent(response));
    }

    @Subscribe
    public void handleAddUserEvent(RequestAddUserEvent requestAddUserEvent){
        try {
            UserDTO userDTO = requestAddUserEvent.getUser();
            UserDTO response = applicationServices.addUser(userDTO);
            eventBus.post(new SuccessfullyAddedUserEvent(response));
        }catch (BusinessException e){
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR,"User konnte nicht hinzugefügt werden."));
        }
    }

    @Subscribe
    public void handlePasswordChange(RequestChangePasswordEvent requestChangePasswordEvent){
        try {
            UserDTO userDTO = requestChangePasswordEvent.getUser();
            String newPassword = requestChangePasswordEvent.getNewPassword();
            UserDTO response = null;
            response = applicationServices.changePassword(userDTO,newPassword);
            eventBus.post(new SuccessfullyChangedPasswordEvent(response));
        } catch (BusinessException e) {
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "Konnte Passwort nicht ändern..."));
        }

    }

    @Subscribe
    public void handleRoleChange(RequestChangeUserRoleEvent requestChangeUserRoleEvent){
        try {
            UserDTO userDTO = requestChangeUserRoleEvent.getUser();
            UserDTO response = null;
            response = applicationServices.changeUserPrivileges(userDTO.getUsername(),userDTO.getRole());
            eventBus.post(new SuccessfullyChangedUserRoleEvent(response));
        } catch (BusinessException e) {
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "Konnte Rolle nicht ändern"));
        }
    }

    @Subscribe
    public void handleEmailChange(RequestChangeEmailEvent requestChangeEmailEvent) {
        try {


            UserDTO userDTO = requestChangeEmailEvent.getUser();
            String newEmail = requestChangeEmailEvent.getNewEmail();
            UserDTO response = null;
            response = applicationServices.changeUserEmail(userDTO.getUsername(), newEmail);
            eventBus.post(new SuccessfullyChangedEmailEvent(response));
        }catch (BusinessException e){
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR,"Konnte Email nicht ändern"));
        }
    }

    @Subscribe void requestChangeUserInformation( RequestChangeUserInformationEvent requestChangeUserInformationEvent) throws BusinessException {
        UserDTO user = requestChangeUserInformationEvent.getUserDTO();
        String newForename = requestChangeUserInformationEvent.getNewForename();
        String newLastname =requestChangeUserInformationEvent.getNewLastname();
        String newEmail =requestChangeUserInformationEvent.getNewEmail();
        UserDTO response = null;
        response =applicationServices.changeAccountInformation(user.getForename(),user.getLastname(),user.getEmail());
        eventBus.post((new SuccessfullyChangedUserInformationEvent(response)));

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
    public void handle(RequestPatchDoctoralStudentEvent event) {
        try {
            applicationServices.patchDoctoralStudent(event.getPatchDoctoralStudentDTO());
            // successfully patched
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, "Successfully patched"));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void handleRequest(RequestAlertedDoctoralStudentEvent event) {
        try {
            Collection<PreviewDoctoralStudentDTO> alertedStudents = applicationServices.getAlertedDoctoralStudents();
            // send response event to everybody who is interested
            eventBus.post(new ShowAlertedDoctoralStudentsEvent(alertedStudents));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Subscribe
    public void handleDocumentRequestEvent(RequestDocumentsEvent event){
        Collection<DocumentDTO> documents = new HashSet<>();
        Collection<DocumentInformationDTO> requestedDocuments = event.getDocuments();
        for(DocumentInformationDTO doc : requestedDocuments){
            // todo: show some kind of progress window
            documents.add(applicationServices.getDocument(doc));
        }

        // select file storage location
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Ablageort");
        File defaultDirectory = new File(System.getProperty("user.home")+"/Downloads/");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null) {
            // return if user did not select a directory
            return;
        }
        try {
            for(DocumentDTO doc : documents){
                // rename file according to the Windows naming convention if a file with the same name already exists
                String ext = FilenameUtils.getExtension(doc.getFilename());
                String fileNameWithoutExt = FilenameUtils.removeExtension(doc.getFilename());
                File document = new File(selectedDirectory + "/" + fileNameWithoutExt + "." + ext);
                int i = 2;
                while(document.exists()){
                    document = new File(selectedDirectory + "/" + fileNameWithoutExt + "(" + i++ + ")." + ext);
                }

                // write file
                OutputStream os = new FileOutputStream(document);
                os.write(doc.getData());
                os.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
