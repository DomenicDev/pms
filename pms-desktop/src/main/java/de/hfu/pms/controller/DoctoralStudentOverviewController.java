package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.enums.FacultyHFU;
import de.hfu.pms.shared.enums.Gender;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;


public class DoctoralStudentOverviewController implements Initializable {

    //private Logger logger = Logger.getLogger(DoctoralStudentOverviewController.class);
    private EventBus eventBus = EventBusSystem.getEventBus();
    private ObjectProperty<TableRow<PreviewDoctoralStudentDTO>> selectedTableRow = new SimpleObjectProperty<>();


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
        if(searchResultTableView.getSelectionModel().getSelectedItem() != null){
            // todo
            // ConfirmDialog (yes/no)
            // server request: delete(Collection<Int>) (Id's of the DB-Entries to be deleted)
        }
        else{
            eventBus.post(new AlertNotificationEvent(1, "Bitte wählen Sie den zu löschenden Eintrag aus."));
        }
    }

    @FXML
    public void handleOnActionEditButton(ActionEvent event) {
        int selectedCount = searchResultTableView.getSelectionModel().getSelectedItems().size();
        if(selectedCount == 1){
            // todo
            // get full DoctoalStudent
            // Switch to edit window
        }
        else if(selectedCount > 1){
            eventBus.post(new AlertNotificationEvent(1, "Es kann immer nur ein Eintrag editiert werden."));
        }
        else {
            eventBus.post(new AlertNotificationEvent(1, "Bitte wählen Sie den zu editierenden Eintrag aus."));
        }
    }

    @FXML
    public void handleOnActionSearchButton(ActionEvent event) {
        // todo
        // request a Search
        // print result

        String searchterm = searchTextField.getText();

        PreviewDoctoralStudentDTO student1 = new PreviewDoctoralStudentDTO(500L, "Jahnsen", "Jan", FacultyHFU.Medical_and_Life_Sciences, "jan.jahnsen@mail.com", "017322497814", Gender.Male);
        PreviewDoctoralStudentDTO student2 = new PreviewDoctoralStudentDTO(501L, "Fröhlich", "Alina", FacultyHFU.Medical_and_Life_Sciences, "ali.fr@mail.com", "015121639248", Gender.Female);

        Collection<PreviewDoctoralStudentDTO> students = new HashSet<>(Arrays.asList(student1, student2));

        searchResultTableView.getItems().addAll(students);
    }

    private void initPreviewTables() {
        searchResultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        searchResultNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        searchResultForeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("foreName"));
        searchResultFacultyTableColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        searchResultEmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        searchResultPhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        searchResultGenderTableColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        searchResultTableView.setRowFactory(new Callback<TableView<PreviewDoctoralStudentDTO>, TableRow<PreviewDoctoralStudentDTO>>() {
            @Override
            public TableRow<PreviewDoctoralStudentDTO> call(TableView<PreviewDoctoralStudentDTO> tableView2) {
                final TableRow<PreviewDoctoralStudentDTO> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < searchResultTableView.getItems().size() && searchResultTableView.getSelectionModel().isSelected(index)) {
                            searchResultTableView.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
    }
}
