package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.FacultyDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.shared.enums.Gender;
import de.hfu.pms.shared.enums.UserRole;
import de.hfu.pms.shared.utils.Converter;
import de.hfu.pms.utils.CollectionUtils;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;


public class DoctoralStudentOverviewController implements Initializable {

    private Logger logger = Logger.getLogger(DoctoralStudentOverviewController.class.getName());
    private EventBus eventBus = EventBusSystem.getEventBus();
    private ResourceBundle bundle;

    @FXML
    private Label labelDoctoralInList;
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
    private TableColumn<PreviewDoctoralStudentDTO, String> searchResultGenderTableColumn;

    /* ************ */
    /* Check Boxes  */
    /* ************ */
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private CheckBox inactiveCheckBox;
    @FXML
    private CheckBox anonymizedCheckBox;
    @FXML
    private CheckBox memberCheckBox;

    /* ******* */
    /* Buttons */
    /* ******* */
    @FXML
    private Button anonymizeButton;
    @FXML
    private Button deleteButton;

    private ObservableList<PreviewDoctoralStudentDTO> masterData = FXCollections.observableArrayList();
    private Collection<PreviewDoctoralStudentDTO> filteredMasterData = new HashSet<>(); // filtered by check boxes
    private Collection<PreviewDoctoralStudentDTO> searchResult = new HashSet<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        this.bundle = resources;

        // setup table columns
        initPreviewTables();

        // init with previews
        Collection<PreviewDoctoralStudentDTO> previews = EntityPool.getInstance().getPreviewStudents();
        masterData.addAll(previews);
        searchResultTableView.getItems().addAll(masterData);

        // open edit window on double mouse click
        searchResultTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                PreviewDoctoralStudentDTO preview = searchResultTableView.getSelectionModel().getSelectedItem();
                if (preview != null) {
                    eventBus.post(new OnClickEditDoctoralStudentEvent(preview.getId()));
                }
            }
        });

        searchResultTableView.getItems().addListener((ListChangeListener<PreviewDoctoralStudentDTO>) c -> labelDoctoralInList.setText(bundle.getString("ui.label.content.doctoral_in_list") + " " + searchResultTableView.getItems().size()));

        initCheckBoxes();

        setDeleteButtonsDisabledForNonAdmins();
    }

    private void setDeleteButtonsDisabledForNonAdmins() {
        try {
            UserInfoDTO user = EntityPool.getInstance().getLoggedInUser();
            UserRole role = user.getRole();
            if (!role.equals(UserRole.ADMIN)) {
                deleteButton.setDisable(true);
                anonymizeButton.setDisable(true);
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    private void initCheckBoxes() {
        activeCheckBox.setSelected(true);

        onCheckBoxSelectionChanged();
    }

    @FXML
    private void onCheckBoxSelectionChanged() {
        refreshTable();
    }

    private Collection<PreviewDoctoralStudentDTO> getForActivity(boolean active) {
        Collection<PreviewDoctoralStudentDTO> actives = new HashSet<>();
        for (PreviewDoctoralStudentDTO preview : masterData) {
            if (preview.isActive() == active) {
                if (!active && preview.isAnonymized()) {
                    continue;
                }
                actives.add(preview);
            }
        }
        return actives;
    }

    private Collection<PreviewDoctoralStudentDTO> getMembers(Collection<PreviewDoctoralStudentDTO> listToFilter) {
        Collection<PreviewDoctoralStudentDTO> actives = new HashSet<>();
        for (PreviewDoctoralStudentDTO preview : listToFilter) {
            if (preview.isMemberHFUCollege()) {
                actives.add(preview);
            }
        }
        return actives;
    }

    private Collection<PreviewDoctoralStudentDTO> getAnonymizedPreviews() {
        Collection<PreviewDoctoralStudentDTO> actives = new HashSet<>();
        for (PreviewDoctoralStudentDTO preview : masterData) {
            if (preview.isAnonymized()) {
                actives.add(preview);
            }
        }
        return actives;
    }

    @FXML
    public void handleOnActionAddButton() {
        eventBus.post(new OnClickAddNewDoctoralStudentEvent());
    }

    @FXML
    public void handleOnActionDeleteButton() {
        if (searchResultTableView.getSelectionModel().getSelectedItem() != null) {

            Long id = searchResultTableView.getSelectionModel().getSelectedItem().getId();
            if (id == null) {
                eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.ERROR, bundle.getString("ui.alert.canceled_process.id_is_not_set")));
                return;
            }

            GuiLoader.showYesAndNoAlert(Alert.AlertType.WARNING,
                    bundle.getString("ui.alert.title.delete"),
                    bundle.getString("ui.alert.content.delete"),
                    bundle.getString("ui.alert.label.yes_delete"),
                    bundle.getString("ui.alert.label.no_delete"),
                    () -> eventBus.post(new RequestDeleteDoctoralStudentEvent(id)),
                    null
            );
        } else {
            eventBus.post(new AlertNotificationEvent(1, bundle.getString("ui.alert.select_item_to_delete")));
        }
    }

    @FXML
    public void handleOnActionEditButton() {
        int selectedCount = searchResultTableView.getSelectionModel().getSelectedItems().size();
        if (selectedCount == 1) {
            PreviewDoctoralStudentDTO selectedItem = searchResultTableView.getSelectionModel().getSelectedItem();
            Long id = selectedItem.getId();
            eventBus.post(new OnClickEditDoctoralStudentEvent(id));
        } else if (selectedCount > 1) {
            eventBus.post(new AlertNotificationEvent(1, bundle.getString("ui.alert.only_one_item_can_be_edited")));
        } else {
            eventBus.post(new AlertNotificationEvent(1, bundle.getString("ui.alert.select_item_to_edit")));
        }
    }

    @FXML
    public void handleOnActionAnonymizeButton() {
        PreviewDoctoralStudentDTO selected = searchResultTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.select_item_to_edit")));
            return;
        }

        Long id = selected.getId();
        if (id == null) {
            return;
        }

        // ask user if he really wants to anonymize this entity
        GuiLoader.showYesAndNoAlert(Alert.AlertType.WARNING,
                bundle.getString("ui.alert.title.confirm_anonymize"),
                bundle.getString("ui.alert.content.anonymize"),
                bundle.getString("ui.alert.label.yes_anonymize"),
                bundle.getString("ui.alert.label.no_anonymize"),
                () -> eventBus.post(new RequestAnonymizeDoctoralStudentEvent(id)),
                null);
    }

    @FXML
    public void handleOnActionSearch() {
        String searchText = searchTextField.getText();
        if (searchText != null && !searchText.trim().isEmpty()) {
            eventBus.post(new RequestSearchDoctoralStudentEvent(searchText));
        }
    }

    private void initPreviewTables() {
        // define property representation
        searchResultTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchResultNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        searchResultForeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("foreName"));
        searchResultFacultyTableColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        searchResultEmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        searchResultPhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // for the gender we want to show the definition in the appropriate language
        searchResultGenderTableColumn.setCellValueFactory(param -> {
            PreviewDoctoralStudentDTO preview = param.getValue();
            if (preview != null) {
                if (preview.getGender() != null) {
                    WrappedEntity<Gender> gender = RepresentationWrapper.find(param.getValue().getGender(), RepresentationWrapper.getWrappedGenders());
                    if (gender != null) {
                        return new SimpleObjectProperty<>(gender.getRepresentation());
                    }
                }
            }
            return new SimpleObjectProperty<>("");
        });

    }

    private void refreshTable() {
        // first we want to check which filter check boxes has been checked
        Collection<PreviewDoctoralStudentDTO> filteredPreviews = new HashSet<>();
        if (activeCheckBox.isSelected()) {
            filteredPreviews.addAll(getForActivity(true));
        }
        if (inactiveCheckBox.isSelected()) {
            filteredPreviews.addAll(getForActivity(false));
        }
        if (memberCheckBox.isSelected()) {
            if (activeCheckBox.isSelected() || inactiveCheckBox.isSelected()) {
                Collection<PreviewDoctoralStudentDTO> newFiltered = getMembers(filteredPreviews);
                filteredPreviews.clear();
                filteredPreviews.addAll(newFiltered);
            } else {
                Collection<PreviewDoctoralStudentDTO> newFiltered = getMembers(masterData);
                filteredPreviews.clear();
                filteredPreviews.addAll(newFiltered);
            }
        }
        if (anonymizedCheckBox.isSelected()) {
            filteredPreviews.addAll(getAnonymizedPreviews());
        }
        // reset the whole table
        searchResultTableView.getItems().clear();
        searchResultTableView.getItems().addAll(filteredPreviews);

        this.filteredMasterData.clear();
        this.filteredMasterData = filteredPreviews;

        // if exists, filter the already filtered entities for the specified search text
        if (!searchTextField.getText().trim().isEmpty()) {
            filterForSearchResult();
        }
    }

    private void addToTable(DoctoralStudentDTO doctoralStudentDTO) {
        if (doctoralStudentDTO == null) {
            logger.log(Level.DEBUG, "null values cannot be added to the preview table");
            return;
        }

        PreviewDoctoralStudentDTO preview = Converter.convert(doctoralStudentDTO);
        this.masterData.add(preview);
        refreshTable();
    }

    private void updateTable(PreviewDoctoralStudentDTO preview) {
        CollectionUtils.removeFromList(preview, masterData, (original, collectionItem) -> original.getId().equals(collectionItem.getId()));
        masterData.add(preview);
        refreshTable();
    }

    private void deleteFromTable(Long id) {
        CollectionUtils.removeFromList(masterData, collectionItem -> id.equals(collectionItem.getId()));
        refreshTable();
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

    @Subscribe
    public void handle(SuccessfullyDeletedDoctoralStudentEvent event) {
        deleteFromTable(event.getId());
    }

    @Subscribe
    public void handle(ResponseSearchRequestEvent event) {
        this.searchResult = event.getPreviews();
        filterForSearchResult();
    }

    /**
     * This method will make only the items visible that
     * match the search text.
     */
    private void filterForSearchResult() {
        // look up the (already) filtered master data for matching items
        Collection<PreviewDoctoralStudentDTO> newFilter = new HashSet<>();
        for (PreviewDoctoralStudentDTO shownPreview : filteredMasterData) {
            if (searchResult.contains(shownPreview)) {
                newFilter.add(shownPreview);
            }
        }

        searchResultTableView.getItems().clear();
        searchResultTableView.getItems().addAll(newFilter);

    }
}