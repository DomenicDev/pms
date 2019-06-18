package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.FacultyDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.enums.Gender;
import de.hfu.pms.shared.utils.Converter;
import de.hfu.pms.utils.CollectionUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;


public class DoctoralStudentOverviewController implements Initializable {

    private Logger logger = Logger.getLogger(DoctoralStudentOverviewController.class.getName());

    //private Logger logger = Logger.getLogger(DoctoralStudentOverviewController.class);
    private EventBus eventBus = EventBusSystem.getEventBus();
    private ObjectProperty<TableRow<PreviewDoctoralStudentDTO>> selectedTableRow = new SimpleObjectProperty<>();

    private EntityPool entityPool = EntityPool.getInstance();
    private ResourceBundle bundle;

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<PreviewDoctoralStudentDTO> searchResultTableView;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultNameTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultForeNameTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, FacultyDTO> searchResultFacultyTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultEmailTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultPhoneNumberTableColumn;
    @FXML
    private TableColumn<PreviewDoctoralStudentDTO, Gender> searchResultGenderTableColumn;

    ObservableList<PreviewDoctoralStudentDTO> masterData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        this.bundle = resources;

        // setup table columns
        initPreviewTables();

        // init with previews
        Collection<PreviewDoctoralStudentDTO> previews = EntityPool.getInstance().getPreviewStudents();
        searchResultTableView.getItems().addAll(previews);

        // open edit window on double mouse click
        searchResultTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    PreviewDoctoralStudentDTO preview = searchResultTableView.getSelectionModel().getSelectedItem();
                    if (preview != null) {
                        eventBus.post(new OnClickEditDoctoralStudentEvent(preview.getId()));
                    }
                }
            }
        });

        // Data for search
        FacultyDTO faculty = new FacultyDTO((long) 1, "Informatik");

        PreviewDoctoralStudentDTO student4 = new PreviewDoctoralStudentDTO("ilker", "coban");
        PreviewDoctoralStudentDTO student5 = new PreviewDoctoralStudentDTO("test", "test");

        masterData.add(student4);
        masterData.add(student5);

        searchResultTableView.setItems(masterData);
        searchResultForeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("foreName"));
        searchResultNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    @FXML
    public void handleOnActionAddButton() {
        eventBus.post(new OnClickAddNewDoctoralStudentEvent());
    }

    @FXML
    public void handleOnActionDeleteButton(ActionEvent event) {
        if(searchResultTableView.getSelectionModel().getSelectedItem() != null){
            // todo
            // ConfirmDialog (yes/no)
            // server request: delete(Collection<Int>) (Id's of the DB-Entries to be deleted)
        }
        else{
            eventBus.post(new AlertNotificationEvent(1, bundle.getString("ui.alert.select_item_to_delete")));
        }
    }

    @FXML
    public void handleOnActionEditButton(ActionEvent event) {
        int selectedCount = searchResultTableView.getSelectionModel().getSelectedItems().size();
        if(selectedCount == 1){
            PreviewDoctoralStudentDTO selectedItem = searchResultTableView.getSelectionModel().getSelectedItem();
            Long id = selectedItem.getId();
            eventBus.post(new OnClickEditDoctoralStudentEvent(id));
        }
        else if(selectedCount > 1){
            eventBus.post(new AlertNotificationEvent(1, bundle.getString("ui.alert.only_one_item_can_be_edited")));
        }
        else {
            eventBus.post(new AlertNotificationEvent(1, bundle.getString("ui.alert.select_item_to_edit")));
        }
    }

    @FXML
    public void handleOnActionSearch() {
        //Suchfunktion

        FilteredList<PreviewDoctoralStudentDTO> filteredData = new FilteredList<>(masterData, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pers -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String input = newValue.toLowerCase();
                if (pers.getForeName().toLowerCase().indexOf(input) != -1) {
                    return true;
                }
                if (pers.getName().toLowerCase().indexOf(input) != -1) {
                    return true;
                }
                return false;
            });

            SortedList<PreviewDoctoralStudentDTO> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(searchResultTableView.comparatorProperty());
            searchResultTableView.setItems(sortedList);
        });




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

    private void addToTable(DoctoralStudentDTO doctoralStudentDTO) {
        if (doctoralStudentDTO == null) {
            logger.log(Level.DEBUG, "null values cannot be added to the preview table");
            return;
        }

        PreviewDoctoralStudentDTO preview = Converter.convert(doctoralStudentDTO);
        this.searchResultTableView.getItems().add(preview);
    }

    private void updateTable(DoctoralStudentDTO updatedDoctoralStudent) {
        PreviewDoctoralStudentDTO preview = Converter.convert(updatedDoctoralStudent);
        CollectionUtils.removeFromList(preview, searchResultTableView.getItems(), (original, collectionItem) -> original.getId().equals(collectionItem.getId()));
        searchResultTableView.getItems().add(preview);
    }

    @Subscribe
    public void handle(SuccessfullyAddedDoctoralStudentEvent event) {
        DoctoralStudentDTO doctoralStudentDTO = event.getDoctoralStudentDTO();
        addToTable(doctoralStudentDTO);
    }

    @Subscribe
    public void handle(SuccessfullyUpdatedDoctoralStudentEvent event) {
        updateTable(event.getUpdatedStudent());
    }
}