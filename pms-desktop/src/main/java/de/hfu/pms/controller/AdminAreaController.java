package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.events.SuccessfullyAddedUserEvent;
import de.hfu.pms.events.SuccessfullyChangedPasswordEvent;
import de.hfu.pms.events.SuccessfullyChangedUserRoleEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.utils.CollectionUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class AdminAreaController implements Initializable {

private EventBus eventBus = EventBusSystem.getEventBus();
private UserDTO user;
private Logger logger = Logger.getLogger(AdminAreaController.class);




    @FXML
    private TableView<UserDTO> tableAdminArea;

    @FXML
    private Button ButtonChangeUserAdmin;

    @FXML
    private Button ButtonAddUserAdmin;

    @FXML
    void handleChangeUserAdminEvent(ActionEvent event) {
        try {
            UserDTO user1 = tableAdminArea.getSelectionModel().getSelectedItem();
            if (user1 == null) {
                eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, "Bitte Uni auswählen"));
                return;
            }

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/changeUserAdminArea.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Bestehenden Benutzer bearbeiten");
            stage.setScene(new Scene(root));
            stage.show();

            //UniversityAddController controller = fxmlLoader.getController();
            //            controller.edit(university);
            ChangeUserAdminArea controller = fxmlLoader.getController();
            controller.edit(user1);

        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change User - AdminArea Screen ");
        }


    }

    @FXML
    private TableColumn<UserDTO, String> TableColumnForname;

    @FXML
    private TableColumn<UserDTO, String> TableColumnLastname;


    @FXML
        void handleAddUserAdminEvent(ActionEvent event){
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/AddUserAdminArea.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("neuen Benutzer Hinzufügen");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                logger.log(Level.ERROR, "Unable to load the Add User - AdminArea Screen " + e);
            }



        }
    private void initAdminTable (ResourceBundle resources){
        TableColumnForname.setCellValueFactory(new PropertyValueFactory<>("forename"));
        TableColumnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

    }


    @Subscribe
    public void handleAdminAddAccountEvent (SuccessfullyAddedUserEvent event){
       UserDTO user = event.getUser();
       tableAdminArea.getItems().add(user);
    }
   @Subscribe
    public void handleAdminUpdateEvent(SuccessfullyChangedPasswordEvent event){
        UserDTO user1 =event.getUser();
        CollectionUtils.removeFromList(user1, tableAdminArea.getItems(), (original, collectionItem) -> original.getUsername().equals(collectionItem.getUsername()));

        tableAdminArea.getItems().add(user1);
        //tableAdminArea.refresh();
    }


    @Subscribe
    public void handleAdminRoleUpdateEvent(SuccessfullyChangedUserRoleEvent event){
        UserDTO user2 = event.getUser();

        CollectionUtils.removeFromList(user2, tableAdminArea.getItems(), (original, collectionItem) -> original.getUsername().equals(collectionItem.getUsername()));
        tableAdminArea.getItems().add(user2);
        //tableAdminArea.refresh();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        initAdminTable(resources);
        tableAdminArea.getItems().addAll(EntityPool.getInstance().getUsers());
        tableAdminArea.setEditable(true);

    }
}
