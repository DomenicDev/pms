package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.enums.FacultyHFU;
import de.hfu.pms.shared.enums.Gender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;


public class DoctoralStudentOverviewController  implements Initializable {

    //private Logger logger = Logger.getLogger(DoctoralStudentOverviewController.class);
    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<PreviewDoctoralStudentDTO> searchResultTableView;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultNameTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultForeNameTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, FacultyHFU> searchResultFacultyTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultEmailTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultPhoneNumberTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, Gender> searchResultGenderTableColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        // setup table columns
        initPreviewTables();

        // init with previews
        Collection<PreviewDoctoralStudentDTO> previews = EntityPool.getInstance().getPreviewStudents();
        searchResultTableView.getItems().addAll(previews);
    }

    @FXML
    public void handleOnActionDeleteButton(ActionEvent event) {
        if(searchResultTableView.getSelectionModel().getSelectedItems().size() == 1){
            // todo
            // ConfirmDialog (yes/no)
            // server request: delete(Collection<Int>) (Id's to be deleted)
        }
        else{
            eventBus.post(new AlertNotificationEvent(1, "Bitte wählen Sie einen Eintrag aus, welcher gelöscht werden soll."));
        }
    }

    @FXML
    public void handleOnActionEditButton(ActionEvent event) {
         if(searchResultTableView.getSelectionModel().getSelectedItems().size() == 1){
             // get full DoctoalStudent
             // Switch to edit window
         }
         else{
             eventBus.post(new AlertNotificationEvent(1, "Bitte wählen Sie einen Eintrag aus, welcher editiert werden soll."));
         }

    }

    @FXML
    public void handleOnActionSearchButton(ActionEvent event) {
        // todo
        // request a Search
        //
        // print result
        String searchterm = searchTextField.getText();

        PreviewDoctoralStudentDTO student1 = new PreviewDoctoralStudentDTO(500L, "Jahnsen", "Jan", FacultyHFU.Medical_and_Life_Sciences, "jan.jahnsen@mail.com", "017322497814", Gender.Male);
        PreviewDoctoralStudentDTO student2 = new PreviewDoctoralStudentDTO(501L, "Fröhlich", "Alina", FacultyHFU.Medical_and_Life_Sciences, "ali.fr@mail.com", "015121639248", Gender.Female);

        Collection<PreviewDoctoralStudentDTO> students = new HashSet<PreviewDoctoralStudentDTO>(Arrays.asList(student1, student2));

        searchResultTableView.getItems().addAll(students);
    }

    private void initPreviewTables() {
        searchResultNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        searchResultForeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("foreName"));
        searchResultFacultyTableColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        searchResultEmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        searchResultPhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        searchResultGenderTableColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }
}
