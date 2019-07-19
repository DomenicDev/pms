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
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GuiEventHandler extends Thread {

    private EventBus eventBus;
    private Logger logger = Logger.getLogger(GuiEventHandler.class);
    private ApplicationServices applicationServices;
    private Stage primaryStage;
    private ResourceBundle bundle;
    private boolean closed = false;
    private Queue<Job> jobs = new ConcurrentLinkedDeque<>();
    private Stage loadingWindowStage = null;

    public GuiEventHandler(Stage primaryStage, ApplicationServices applicationServices, EventBus eventBus) {
        this.primaryStage = primaryStage;

        this.eventBus = eventBus;
        this.eventBus.register(this);

        this.bundle = GuiLoader.getResourceBundle();

        this.applicationServices = applicationServices;

        this.setDaemon(true);
        this.setName("[GuiEventHandler]-Thread");
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

    public void close() {
        this.closed = true;
    }


    private synchronized void addJob(Job job) {
        this.jobs.add(job);
    }

    @Subscribe
    public void handleLoginEvent(LoginRequestEvent loginRequestEvent) {

        // extract credentials from request
        String username = loginRequestEvent.getUsername();
        String password = loginRequestEvent.getPwHash();

        addJob(() -> {

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
                            newStage.setTitle(bundle.getString("app.name"));
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
                e.printStackTrace();
                Platform.runLater(() -> {
                    logger.log(Level.DEBUG, "Login failed..." + Thread.currentThread());
                    eventBus.post(new LoginFailedEvent(bundle.getString("ui.login.login_failed")));
                });
            }

        });
    }

    @Subscribe
    public void handleCreateDoctoralStudentEvent(RequestCreateDoctoralStudentEvent saveEvent) {
        CreateDoctoralStudentDTO doctoralStudent = saveEvent.getCreateDoctoralStudentDTO();

        addJob(() -> {
            showLoadingWindow();
            Platform.runLater(() -> {
                try {
                    PreviewDoctoralStudentDTO createdEntity = applicationServices.addDoctoralStudent(doctoralStudent);
                    eventBus.post(new SuccessfullyAddedDoctoralStudentEvent(createdEntity));
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            });
            closeLoadingWindow();
        });
    }

    @Subscribe
    public void handleAnonymizeDoctoralStudentEvent(RequestAnonymizeDoctoralStudentEvent requestAnonymizeDoctoralStudentEvent) {
        addJob(() -> {
            showLoadingWindow();
            try {
                AnonymizeResultDTO result = applicationServices.anonymize(requestAnonymizeDoctoralStudentEvent.getId());
                Platform.runLater(() -> {
                    eventBus.post(new SuccessfullyDeletedDoctoralStudentEvent(result.getDeletedId()));
                    eventBus.post(new SuccessfullyAddedDoctoralStudentEvent(result.getNewDoctoralStudent()));
                });

            } catch (BusinessException e) {
                e.printStackTrace();
            }
            closeLoadingWindow();
        });
    }

    @Subscribe
    public void handleAddUniversityEvent(RequestAddUniversityEvent requestSaveEvent) {
        try {
            UniversityDTO universityDTO = requestSaveEvent.getUniversity();
            UniversityDTO response = applicationServices.addUniversity(universityDTO);
            EntityPool.getInstance().addUniversity(response);
            eventBus.post(new SuccessfullyAddedUniversityEvent(response));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void handleUpdateUniversityEvent(RequestUpdateUniversityEvent requestUpdateUniversityEvent) {
        try {
            UniversityDTO universityDTO = requestUpdateUniversityEvent.getUniversity();
            UniversityDTO response = applicationServices.updateUniversity(universityDTO.getId(), universityDTO);
            EntityPool.getInstance().updateUniversity(response);
            eventBus.post(new SuccessfullyUpdatedUniversityEvent(response));
        } catch (BusinessException e) {
            show(e);
        }
    }

    @Subscribe
    public void handleDeleteUniversityEvent(RequestDeleteUniversityEvent event) {
        try {
            applicationServices.deleteUniversity(event.getId());
            eventBus.post(new SuccessfullyDeletedUniversityEvent(event.getId()));
        } catch (BusinessException e) {
            show(new BusinessException(bundle.getString("ui.alert.delete_failed_university")));
        }
    }

    @Subscribe
    public void handleAddFacultyEvent(RequestAddFacultyEvent requestSaveEvent) {
        try {
            FacultyDTO facultyDTO = requestSaveEvent.getFaculty();
            FacultyDTO response = applicationServices.addFaculty(facultyDTO);
            EntityPool.getInstance().addFaculty(response);
            eventBus.post(new SuccessfullyAddedFacultyEvent(response));
        } catch (BusinessException e) {
            show(e);
        }
    }

    @Subscribe
    public void handleUpdateFacultyEvent(RequestUpdateFacultyEvent requestUpdateFacultyEvent) {
        try {
            FacultyDTO facultyDTO = requestUpdateFacultyEvent.getFaculty();
            FacultyDTO response = applicationServices.updateFaculty(facultyDTO);
            eventBus.post(new SuccessfullyUpdatedFacultyEvent(response));
        } catch (BusinessException e) {
            show(e);
        }
    }

    @Subscribe
    public void handleDeleteFacultyEvent(RequestDeleteFacultyEvent requestDeleteEvent) {
        try {
            Long id = requestDeleteEvent.getFacultyId();
            applicationServices.deleteFaculty(id);
            eventBus.post(new SuccessfullyDeletedFacultyEvent(id));
        } catch (BusinessException e) {
            show(new BusinessException(bundle.getString("ui.alert.delete_failed_faculty")));
        }
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
            try {
                DoctoralStudentDTO doctoralStudentDTO = applicationServices.getDoctoralStudent(id);
                Platform.runLater(() -> eventBus.post(new ShowDoctoralStudentEvent(doctoralStudentDTO)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Subscribe
    public void handle(RequestPatchDoctoralStudentEvent event) {
        addJob(() -> {
            showLoadingWindow();
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
            }
            closeLoadingWindow();
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
            addJob(() -> {
                try {
                    Collection<PreviewDoctoralStudentDTO> previews = applicationServices.searchDoctoralStudents(searchText);
                    if (previews != null) {
                        Platform.runLater(() -> {
                            eventBus.post(new ResponseSearchRequestEvent(previews));
                        });
                    }
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            });

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
            try {
                documents.add(applicationServices.getDocument(doc));
            } catch (BusinessException e) {
                show(e);
            }
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

    private synchronized void showLoadingWindow() {
        Platform.runLater(() -> {

            if (loadingWindowStage != null) {
                return;
            }

            // show...
            try {
                loadingWindowStage = GuiLoader.createLoadingScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private synchronized void closeLoadingWindow() {
        Platform.runLater(() -> {
            if (loadingWindowStage == null) {
                return;
            }
            loadingWindowStage.hide();
            loadingWindowStage = null;
        });
    }
}
