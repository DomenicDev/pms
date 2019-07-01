package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.UpdateUserDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import de.hfu.pms.shared.enums.UserRole;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The controller for the admin area screen.
 */
public class AdminAreaController implements Initializable {

    private EventBus eventBus = EventBusSystem.getEventBus();
    private UserInfoDTO user;
    private Logger logger = Logger.getLogger(AdminAreaController.class);
    private ResourceBundle bundle;

    @FXML
    private TableView<UserInfoDTO> tableAdminArea;
    @FXML
    private TableColumn<UserInfoDTO, String> TableColumnForname;
    @FXML
    private TableColumn<UserInfoDTO, String> TableColumnLastname;
    @FXML
    private Button deleteButton;
    @FXML
    private Pane infoBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField forenameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private ComboBox<WrappedEntity<UserRole>> roleComboBox;
    @FXML
    private TextField emailTextField;

    private Map<String, UserInfoDTO> masterData = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        this.bundle = resources;

        roleComboBox.getItems().clear();
        roleComboBox.getItems().addAll(RepresentationWrapper.getWrappedRole());

        initAdminTable(resources);

        // fill master data
        for (UserInfoDTO infoDTO : EntityPool.getInstance().getUsers()) {
            masterData.put(infoDTO.getUsername(), infoDTO);
        }

        // change info box on selection change
        tableAdminArea.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    String username = newValue.getUsername();
                    if (username != null) {
                        UserInfoDTO loggedInUser = EntityPool.getInstance().getLoggedInUser();
                        boolean ownUser = username.equals(loggedInUser.getUsername());
                        // disable delete button and role combo box
                        // to prevent user from deleting himself and changing to user
                        deleteButton.setDisable(ownUser);
                        roleComboBox.setDisable(ownUser);
                    }
                }
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            setInfoBox(newValue);
            this.user = newValue;
        });

        setInfoBox(null);
        // finally set table items
        refreshTable();
    }

    private void setInfoBox(UserInfoDTO user) {
        if (user == null) {
            usernameLabel.setText("");
            forenameTextField.setText("");
            surnameTextField.setText("");
            roleComboBox.setValue(null);
            emailTextField.setText("");
            infoBox.setDisable(true);
        } else {
            usernameLabel.setText(user.getUsername());
            forenameTextField.setText(user.getForename());
            surnameTextField.setText(user.getLastname());
            roleComboBox.setValue(RepresentationWrapper.find(user.getRole(), roleComboBox.getItems()));
            emailTextField.setText(user.getEmail());
            infoBox.setDisable(false);
        }
    }

    private void initAdminTable(ResourceBundle resources) {
        TableColumnForname.setCellValueFactory(new PropertyValueFactory<>("forename"));
        TableColumnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    }

    @FXML
    public void handleAddUserAdminEvent(ActionEvent event) {
        try {
            GuiLoader.createAndShow(GuiLoader.USER_FORM_MASK_ADMIN_AREA, bundle.getString("ui.label.create_new_user"), true);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Add User - AdminArea Screen " + e);
        }
    }

    @FXML
    public void handleOnActionSaveButton() {
        if (user == null) {
            return;
        }
        FormValidator validator = new FormValidator();
        validator.textFieldNotEmpty(forenameTextField);
        validator.textFieldNotEmpty(surnameTextField);
        validator.textFieldNotEmpty(emailTextField);
        validator.comboBoxHasSelectedItem(roleComboBox);

        if (!validator.validationSuccessful()) {
            return;
        }

        String username = this.user.getUsername();
        String forename = forenameTextField.getText();
        String surname = surnameTextField.getText();
        UserRole role = roleComboBox.getSelectionModel().getSelectedItem().getEntity();
        String email = emailTextField.getText();

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(username, forename, surname, role, email);
        eventBus.post(new RequestPatchUserEvent(updateUserDTO));
    }

    @FXML
    public void handleOnActionChangePasswordButton() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ChangePasswordAdminArea.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(bundle.getString("ui.label.password_from") + user.getUsername() + " " + bundle.getString("ui.label.change"));
            stage.setScene(new Scene(root));
            stage.show();

            PasswordAdminAreaController controller = fxmlLoader.getController();
            controller.edit(user);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Unable to load the Change Password - AdminArea Screen ");
        }
    }

    @FXML
    public void handleOnActionDeleteButton() {
        UserInfoDTO selectedUser = tableAdminArea.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.select_item_to_delete")));
            return;
        }

        GuiLoader.showYesAndNoAlert(Alert.AlertType.WARNING,
                bundle.getString("ui.alert.title.delete_user"),
                bundle.getString("ui.alert.content.delete_user"),
                bundle.getString("ui.alert.label.yes_delete"),
                bundle.getString("ui.alert.label.no_delete"),
                () -> eventBus.post(new RequestDeleteUserEvent(selectedUser.getUsername())),
                null
        );
    }

    private void refreshTable() {
        tableAdminArea.getItems().clear();
        tableAdminArea.getItems().addAll(masterData.values());
    }

    @Subscribe
    public void handleAdminAddAccountEvent(SuccessfullyAddedUserEvent event) {
        UserInfoDTO user = event.getUser();
        masterData.put(user.getUsername(), user);
        refreshTable();
    }

    @Subscribe
    public void handle(SuccessfullyUpdatedUserEvent event) {
        UserInfoDTO updatedUser = event.getUserInfoDTO();
        masterData.put(updatedUser.getUsername(), updatedUser);
        refreshTable();
    }

    @Subscribe
    public void handle(SuccessfullyDeletedUserEvent event) {
        String username = event.getUsername();
        masterData.remove(username);
        refreshTable();
    }

}
