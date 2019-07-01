package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.AlertNotificationEvent;
import de.hfu.pms.events.SuccessfullyAddedUniversityEvent;
import de.hfu.pms.events.SuccessfullyUpdatedUniversityEvent;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.utils.CollectionUtils;
import de.hfu.pms.utils.GuiLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static de.hfu.pms.shared.enums.UserRole.ADMIN;

public class UniversityScreenController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private Logger logger = Logger.getLogger(UniversityScreenController.class);
    private ResourceBundle bundle;

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
    private TableColumn<UniversityDTO, String> TableColumnContacttoUniversity;

    @FXML
    private Button universityDeleteButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        this.eventBus.register(this);

        initUniversityTable();
        tableViewUniversity.getItems().addAll(EntityPool.getInstance().getUniversities());
        tableViewUniversity.setEditable(true);

        try {
            UserInfoDTO user = EntityPool.getInstance().getLoggedInUser();
            universityDeleteButton.setDisable(!user.getRole().equals(ADMIN));

        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUniversityAddButton() {
        try {
            GuiLoader.createModalWindow(GuiLoader.UNIVERSITY_FORM_SCREEN, 250, 300, false);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Unable to load the University add screen" + e);
        }
    }

    @FXML
    public void handleChangeUniversityButton() {
        try {

            UniversityDTO university = tableViewUniversity.getSelectionModel().getSelectedItem();
            if (university == null) {
                showAlertSelectFirst();
                return;
            }

            UniversityAddController controller = GuiLoader.createAndShow(GuiLoader.UNIVERSITY_FORM_SCREEN, bundle.getString("ui.label.change_university"), true);
            controller.edit(university);

        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the University change screen");
        }
    }

    @FXML
    public void handleDeleteUniversityButton() {
        UniversityDTO university = tableViewUniversity.getSelectionModel().getSelectedItem();
        if (university == null) {
            showAlertSelectFirst();
            return;
        }
        // todo: confirm dialog + check for dependencies & delete if there are none
        // ...
    }

    private void showAlertSelectFirst() {
        eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.select_university")));
    }

    private void initUniversityTable() {
        TableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumnOrt.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumnLand.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumnKuerzel.setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
        TableColumnContacttoUniversity.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @Subscribe
    public void handleUniversityAddEvent(SuccessfullyAddedUniversityEvent event) {
        UniversityDTO university = event.getUniversity();
        tableViewUniversity.getItems().add(university);
    }

    @Subscribe
    public void handleUpdateEvent(SuccessfullyUpdatedUniversityEvent event) {
        UniversityDTO newUniversity = event.getUniversity();
        CollectionUtils.removeFromList(newUniversity, tableViewUniversity.getItems(), (original, collectionItem) -> original.getId().equals(collectionItem.getId()));
        tableViewUniversity.getItems().add(newUniversity);
    }


}