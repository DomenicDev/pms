package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyAddedUserEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UserDTO;
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

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ChangeUserAdminArea.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Bestehenden Benutzer bearbeiten");
            stage.setScene(new Scene(root));
            stage.show();

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
                logger.log(Level.ERROR, "until here it works1");
                ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
                logger.log(Level.ERROR, "until here it works2 ");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/AddUserAdminArea.fxml"));
                logger.log(Level.ERROR, "until here it works3 ");
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
    /*@Subscribe
    public void handleAdminUpdateEvent(SuccessfullyChangedPasswordEvent event){
        UserDTO user1 =event.getUser;

    }*/

    //@Subscribe
    //    public void handleUniversityAddEvent(SuccessfullyAddedUniversityEvent event){
    //        UniversityDTO university = event.getUniversity();
    //        tableViewUniversity.getItems().add(university);
    //    }
   // @Subscribe
   // public void
    //    @Subscribe
    //    public void handleUpdateEvent(SuccessfullyUpdatedUniversityEvent event){
    //        UniversityDTO newUniversity = event.getUniversity();
    //        LinkedList<UniversityDTO> itemsToRemove = new LinkedList<UniversityDTO>();
    //
    //        for ( UniversityDTO universityDTO : tableViewUniversity.getItems()){
    //            if(universityDTO.getId().equals(newUniversity.getId())) {
    //                itemsToRemove.add(universityDTO);
    //            }
    //        }
    //        tableViewUniversity.getItems().removeAll(itemsToRemove);
    //        tableViewUniversity.getItems().add(newUniversity);
    //    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        initAdminTable(resources);
        tableAdminArea.getItems().addAll(EntityPool.getInstance().getUsers());
        tableAdminArea.setEditable(true);

    }
}
