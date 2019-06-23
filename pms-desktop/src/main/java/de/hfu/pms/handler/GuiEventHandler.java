package de.hfu.pms.handler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.utils.GuiLoader;
import javafx.application.Platform;
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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GuiEventHandler extends Thread {

    private EventBus eventBus;

    private Logger logger = Logger.getLogger(GuiEventHandler.class);

    private ApplicationServices applicationServices;

    private Stage primaryStage;

    private HashMap<String, Node> scenes = new HashMap<>();
    private ResourceBundle bundle;

    private boolean closed = false;
    private Queue<Job> jobs = new ConcurrentLinkedDeque<>();

    private volatile Stage loadingWindow;

    public GuiEventHandler(Stage primaryStage, ApplicationServices applicationServices, EventBus eventBus) throws IOException {
        this.primaryStage = primaryStage;

        this.eventBus = eventBus;
        this.eventBus.register(this);

        this.bundle = GuiLoader.getResourceBundle();

        this.applicationServices = applicationServices;

        this.setName("[GuiEventHandler]-Thread");
        this.start();
    }

    private interface Job {

        void callback();

    }


    @Override
    public void run() {

        while (!closed) {

            if (!jobs.isEmpty()) {
                Job job = jobs.poll();
                job.callback();
            }

        }

    }


    private void addJob(Job job) {
        this.jobs.add(job);
    }

    @Subscribe
    public void handleLoginEvent(LoginRequestEvent loginRequestEvent) {

        // extract credentials from request
        String username = loginRequestEvent.getUsername();
        String password = loginRequestEvent.getPwHash();

        addJob(() -> {

            showLoadingScreen();

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // try to login with specified username and password
                applicationServices.login(username, password);

                // prepare entities
                applicationServices.initEntityPool();
                EntityPool.getInstance().setApplicationServices(applicationServices);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // login was successful, so we can close the login screen and open the dashboard
                        primaryStage.close();
                        try {
                            Stage newStage = new Stage(StageStyle.DECORATED);
                            Parent dashboard = GuiLoader.loadFXML("/screens/dashboard_final.fxml");
                            Scene scene = new Scene(dashboard);
                            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
                            newStage.setScene(scene);
                            newStage.setMaximized(true);
                            newStage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (LoginFailedException e) {
                Platform.runLater(() -> {
                    logger.log(Level.DEBUG, "Login failed..." + Thread.currentThread());
                    eventBus.post(new LoginFailedEvent(bundle.getString("ui.login.login_failed")));
                });
            }

            closeLoadingScreen();
        });



    }

    @Subscribe
    public void handleCreateDoctoralStudentEvent(RequestCreateDoctoralStudentEvent saveEvent) {
        CreateDoctoralStudentDTO doctoralStudent = saveEvent.getCreateDoctoralStudentDTO();

        addJob(() -> {
            showLoadingScreen();
            try {
                DoctoralStudentDTO createdEntity = applicationServices.addDoctoralStudent(doctoralStudent);
                eventBus.post(new SuccessfullyAddedDoctoralStudentEvent(createdEntity));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            closeLoadingScreen();
        });
    }

    @Subscribe
    public void handleAnonymizeDoctoralStudentEvent(RequestAnonymizeDoctoralStudentEvent requestAnonymizeDoctoralStudentEvent) {
        try {
            AnonymizeResultDTO result = applicationServices.anonymize(requestAnonymizeDoctoralStudentEvent.getId());
            eventBus.post(new SuccessfullyDeletedDoctoralStudentEvent(result.getDeletedId()));
            eventBus.post(new SuccessfullyAddedDoctoralStudentEvent(result.getNewDoctoralStudent()));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void handleAddUniversityEvent(RequestAddUniversityEvent requestSaveEvent) {
        UniversityDTO universityDTO = requestSaveEvent.getUniversity();
        UniversityDTO response = applicationServices.addUniversity(universityDTO);
        EntityPool.getInstance().addUniversity(response);
        eventBus.post(new SuccessfullyAddedUniversityEvent(response));
    }

    @Subscribe
    public void handleUpdateUniversityEvent(RequestUpdateUniversityEvent requestUpdateUniversityEvent) {
        UniversityDTO universityDTO = requestUpdateUniversityEvent.getUniversity();
        UniversityDTO response = applicationServices.updateUniversity(universityDTO.getId(), universityDTO);
        EntityPool.getInstance().updateUniversity(response);
        eventBus.post(new SuccessfullyUpdatedUniversityEvent(response));
    }

    // todo: delete university

    @Subscribe
    public void handleAddFacultyEvent(RequestAddFacultyEvent requestSaveEvent) {
        FacultyDTO facultyDTO = requestSaveEvent.getFaculty();
        FacultyDTO response = applicationServices.addFaculty(facultyDTO);
        EntityPool.getInstance().addFaculty(response);
        eventBus.post(new SuccessfullyAddedFacultyEvent(response));
    }

    @Subscribe
    public void handleUpdateFacultyEvent(RequestUpdateFacultyEvent requestUpdateFacultyEvent) {
        FacultyDTO facultyDTO = requestUpdateFacultyEvent.getFaculty();
        FacultyDTO response = applicationServices.updateFaculty(facultyDTO);
        eventBus.post(new SuccessfullyUpdatedFacultyEvent(response));
    }

    @Subscribe
    public void handleDeleteFacultyEvent(RequestDeleteFacultyEvent requestDeleteEvent) {
        FacultyDTO facultyDTO = requestDeleteEvent.getFaculty();
        FacultyDTO response = applicationServices.deleteFaculty(facultyDTO);
        eventBus.post(new SuccessfullyDeletedFacultyEvent(response));
    }

    @Subscribe
    public void handleAddUserEvent(RequestAddUserEvent requestAddUserEvent) {
        try {
            UserDTO userDTO = requestAddUserEvent.getUser();
            UserInfoDTO response = applicationServices.addUser(userDTO);
            eventBus.post(new SuccessfullyAddedUserEvent(response));
        } catch (BusinessException e) {
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "User konnte nicht hinzugefügt werden."));
        }
    }

    @Subscribe
    public void handlePatch(RequestPatchUserEvent event) {
        try {
            UserInfoDTO updatedUser = applicationServices.updateUser(event.getUpdateUserDTO());
            eventBus.post(new SuccessfullyUpdatedUserEvent(updatedUser));
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.successfully_updated_user")));
        } catch (BusinessException e) {
            show(e);
        }
    }

    @Subscribe
    public void handleDeleteUser(RequestDeleteUserEvent event) {
        try {
            applicationServices.deleteUser(event.getUsername());
            eventBus.post(new SuccessfullyDeletedUserEvent(event.getUsername()));
        } catch (BusinessException e) {
            show(e);
        }
    }

    @Subscribe
    public void handlePasswordChange(RequestChangePasswordEvent requestChangePasswordEvent) {
        try {
            String username = requestChangePasswordEvent.getUsername();
            String newPassword = requestChangePasswordEvent.getNewPassword();
            applicationServices.changePassword(username, newPassword);
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.successfully_changed_password")));
        } catch (BusinessException e) {
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "Konnte Passwort nicht ändern..."));
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "Konnte Passwort nicht ändern..."));
        }

    }

    @Subscribe
    public void handleRoleChange(RequestChangeUserRoleEvent requestChangeUserRoleEvent) {
        try {
            UserDTO userDTO = requestChangeUserRoleEvent.getUser();
            UserInfoDTO response = null;
            response = applicationServices.changeUserPrivileges(userDTO.getUsername(), userDTO.getRole());
            //eventBus.post(new SuccessfullyChangedUserRoleEvent(response));
            eventBus.post(new SuccessfullyUpdatedUserEvent(response));
        } catch (BusinessException e) {
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "Konnte Rolle nicht ändern"));
        }
    }

    @Subscribe
    public void handleEmailChange(RequestChangeEmailEvent requestChangeEmailEvent) {
        try {
            String username = requestChangeEmailEvent.getUsername();
            String newEmail = requestChangeEmailEvent.getNewEmail();
            UserInfoDTO response = applicationServices.changeUserEmail(username, newEmail);
            //  eventBus.post(new SuccessfullyChangedEmailEvent(response));
            eventBus.post(new SuccessfullyUpdatedUserEvent(response));
        } catch (BusinessException e) {
            e.printStackTrace();
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, "Konnte Email nicht ändern"));
        }
    }

    @Subscribe
    public void requestChangeUserInformation(RequestChangeUserInformationEvent event) throws BusinessException {
        String username = event.getUsername();
        String newForename = event.getForename();
        String newSurname = event.getSurname();
        String newEmail = event.getEmail();
        UserInfoDTO response = applicationServices.changeAccountInformation(username, newForename, newSurname, newEmail);
        eventBus.post(new SuccessfullyUpdatedUserEvent(response));
    }

    @Subscribe
    public void handleEvent(OnClickEditDoctoralStudentEvent event) {
        Long id = event.getId();

        addJob(() -> {
            showLoadingScreen();

            try {
                DoctoralStudentDTO doctoralStudentDTO = applicationServices.getDoctoralStudent(id);
                Platform.runLater(() -> eventBus.post(new ShowDoctoralStudentEvent(doctoralStudentDTO)));
            } catch (IOException e) {
                show(new BusinessException(e.getLocalizedMessage()));
            }

            closeLoadingScreen();
        });

    }

    @Subscribe
    public void handle(RequestPatchDoctoralStudentEvent event) {
        addJob(() -> {
            showLoadingScreen();
            try {
                applicationServices.patchDoctoralStudent(event.getPatchDoctoralStudentDTO());
                PreviewDoctoralStudentDTO preview = applicationServices.getPreview(event.getPatchDoctoralStudentDTO().getId());

                // successfully patched
                Platform.runLater(() -> {
                    eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.successfully_patched_entry")));
                    eventBus.post(new SuccessfullyUpdatedDoctoralStudentEvent(preview));
                });

            } catch (BusinessException e) {
                e.printStackTrace();
            } finally {
                closeLoadingScreen();
            }

        });

    }

    @Subscribe
    public void handle(RequestDeleteDoctoralStudentEvent event) {
        Long id = event.getId();
        if (id == null) {
            return;
        }
        try {
            applicationServices.deleteDoctoralStudent(id);
            eventBus.post(new SuccessfullyDeletedDoctoralStudentEvent(id));
        } catch (BusinessException e) {
            show(e);
        }
    }

    @Subscribe
    public void handle(RequestSearchDoctoralStudentEvent event) {
        String searchText = event.getSearchText();
        if (searchText != null) {
            try {
                Collection<PreviewDoctoralStudentDTO> previews = applicationServices.searchDoctoralStudents(searchText);
                if (previews != null) {
                    eventBus.post(new ResponseSearchRequestEvent(previews));
                }
            } catch (BusinessException e) {
                e.printStackTrace();
            }
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
    public void handleDocumentRequestEvent(RequestDocumentsEvent event) {
        Collection<DocumentDTO> documents = new HashSet<>();
        Collection<DocumentInformationDTO> requestedDocuments = event.getDocuments();
        for (DocumentInformationDTO doc : requestedDocuments) {
            // todo: show some kind of progress window
            documents.add(applicationServices.getDocument(doc));
        }

        // select file storage location
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Ablageort");
        File defaultDirectory = new File(System.getProperty("user.home") + "/Downloads/");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null) {
            // return if user did not select a directory
            return;
        }
        try {
            for (DocumentDTO doc : documents) {
                // rename file according to the Windows naming convention if a file with the same name already exists
                String ext = FilenameUtils.getExtension(doc.getFilename());
                String fileNameWithoutExt = FilenameUtils.removeExtension(doc.getFilename());
                File document = new File(selectedDirectory + "/" + fileNameWithoutExt + "." + ext);
                int i = 2;
                while (document.exists()) {
                    document = new File(selectedDirectory + "/" + fileNameWithoutExt + "(" + i++ + ")." + ext);
                }

                // write file
                OutputStream os = new FileOutputStream(document);
                os.write(doc.getData());
                os.close();
            }

            // show success notification
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.successfully_saved_documents")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show(BusinessException exception) {
        eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, exception.getLocalizedMessage()));
    }

    private void showLoadingScreen() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("create");
                try {
                   loadingWindow = GuiLoader.createLoadingScreen();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void closeLoadingScreen() {
        if (loadingWindow != null) {
            Platform.runLater(() -> {
                System.out.println("close");
                loadingWindow.close();
                });
        }
    }
}
