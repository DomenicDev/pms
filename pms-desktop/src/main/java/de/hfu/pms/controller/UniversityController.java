package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.events.SuccessfullyAddedUniversityEvent;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.utils.GuiLoader;
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


public class UniversityController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();

    private Logger logger = Logger.getLogger(UniversityController.class);

    @FXML
    private TableView<UniversityDTO> tableViewUniversity;
    @FXML
    private TableColumn<UniversityDTO, String> TableColumnName;

    @FXML
    private TableColumn<UniversityDTO, String> TableColumnOrt;

    @FXML
    private TableColumn<UniversityDTO, String> TableColumnLand;

    @FXML
    private TableColumn<UniversityDTO, String> TableColumnKuerzel;

    @FXML
    private Button universityAddButton;

    @FXML
    void handleUniversityAddButton(ActionEvent event) {
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/Univerity_add_screen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Universität Hinzufügen");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the University add screen");
        }
    }
    @FXML
    void handleChangeUniversityButton(ActionEvent event) {
        try {

            UniversityDTO university = tableViewUniversity.getSelectionModel().getSelectedItem();
            if (university == null) {
                eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, "Bitte Uni auswählen"));
                return;
            }

            ResourceBundle bundle = GuiLoader.getResourceBundle();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/Univerity_add_screen.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Universität Ändern");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));



            stage.show();

            UniversityAddController controller = fxmlLoader.getController();



            controller.edit(university);


        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the University change screen");
        }
    }


    private void initUniversityTable (ResourceBundle resources){
        TableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumnOrt.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumnLand.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumnKuerzel.setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
    }

    @Subscribe
    public void handleUniversityAddEvent(SuccessfullyAddedUniversityEvent event){
        UniversityDTO university = event.getUniversity();
        tableViewUniversity.getItems().add(university);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        initUniversityTable(resources);

    }
}
