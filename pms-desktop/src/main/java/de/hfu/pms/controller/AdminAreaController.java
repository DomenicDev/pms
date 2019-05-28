package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyAddedUserEvent;
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
        private Button ButtonTryChange;

        @FXML
        private TableView<UserDTO> tableAdminArea;

    @FXML
    private TableColumn<UserDTO, String> TableColumnForname;

    @FXML
    private TableColumn<UserDTO, String> TableColumnLastname;


    @FXML
        void handleChangeButton(ActionEvent event) {
            try {

                ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/AddUserAdminArea.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("BenutzerinformationenÄndern - Admin");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                logger.log(Level.ERROR, "Unable to load the Change User - AdminArea Screen ");
            }



        }
    private void initUniversityTable (ResourceBundle resources){
        TableColumnForname.setCellValueFactory(new PropertyValueFactory<>("forname"));
        TableColumnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

    }


    @Subscribe
    public void handleAdminAccountEvent (SuccessfullyAddedUserEvent event){
       UserDTO user = event.getUser();
       tableAdminArea.getItems().add(user);
    }
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

        initUniversityTable(resources);

    }
}
