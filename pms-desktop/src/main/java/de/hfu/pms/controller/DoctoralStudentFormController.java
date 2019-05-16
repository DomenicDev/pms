package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.EventBusSystem;
import de.hfu.pms.events.CreateEmploymentEntryEvent;
import de.hfu.pms.events.SaveDoctoralStudentEvent;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.EmploymentEntryDTO;
import de.hfu.pms.shared.dto.PersonalDataDTO;
import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;
import de.hfu.pms.shared.enums.FamilyStatus;
import de.hfu.pms.utils.GuiLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctoralStudentFormController implements Initializable {

    private DoctoralStudentDTO doctoralStudent = null;

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    // PERSONAL DATA
    @FXML private TextField lastNameTextField;

    // Employment
    @FXML
    private TableView<EmploymentEntryDTO> employmentTableView;
    @FXML
    private TableColumn<EmploymentEntryDTO, EmploymentLocation> employmentLocationTableColumn;
    @FXML
    private TableColumn<EmploymentEntryDTO, String> kindOfEmploymentTableColumn;
    @FXML
    private TableColumn<EmploymentEntryDTO, Campus> employmentCampusTableColumn;
    @FXML
    private TableColumn<EmploymentEntryDTO, String> preTimesTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        // setup employment table columns
        initEmploymentTable(resources);
    }

    private void initEmploymentTable(ResourceBundle resources) {
        employmentLocationTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentLocation"));
        kindOfEmploymentTableColumn.setCellValueFactory(new PropertyValueFactory<>("kindOfEmployment"));
        employmentCampusTableColumn.setCellValueFactory(new PropertyValueFactory<>("campusOfDeployment"));
        preTimesTableColumn.setCellValueFactory(param -> {
            String text = (param.getValue().isPreEmploymentTimeToBeCharged()) ? resources.getString("yes") : resources.getString("no");
            return new SimpleStringProperty(text);
        });
    }

    public void resetAllInputFields() {
        doctoralStudent = null;
        // todo
    }

    public void fillFormMask(DoctoralStudentDTO doctoralStudent) {
        this.doctoralStudent = doctoralStudent;
        // todo.. fill text fields
    }

    @FXML
    public void handleOnActionCancelButton() {
        cancel();
    }

    private void cancel() {
        // todo
    }

    @FXML
    public void handleOnActionSaveButton() {
        if (doctoralStudent == null) {
            // we are not editing an existing object but actually creating a new one
            this.doctoralStudent = new DoctoralStudentDTO();
            this.doctoralStudent.setPersonalData(new PersonalDataDTO());
            // todo add missing parts....
        } else {
            // if we are here, we edit an already existing student
            // we must not set the ID !!!
        }
        // write form data to java object
        writeToDoctoralStudentDTO();

        // post a new save event to notify subscribers about the save action
        // they should take care about the actual saving process
        eventBus.post(new SaveDoctoralStudentEvent(doctoralStudent));

        // after saving, we can reset our input fields
        resetAllInputFields();
    }

    // DEPLOYMENT
    @FXML
    public void handleOnActionAddEmploymentEntryButton() throws IOException {
        Parent parent = GuiLoader.loadFXML(GuiLoader.EMPLOYMENT);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Subscribe
    public void handleCreateEmploymentEntryEvent(CreateEmploymentEntryEvent event) {
        EmploymentEntryDTO entry = event.getEmploymentEntryDTO();
        employmentTableView.getItems().add(entry);
    }

    private <T> T checkForNull(T t) throws IllegalArgumentException {
        if (t == null) {
            throw new IllegalArgumentException("null is not a valid value");
        }
        return t;
    }

    private void writeToDoctoralStudentDTO() {
        // we first check personal data
        PersonalDataDTO personalData = doctoralStudent.getPersonalData();

        String lastName = checkForNull(lastNameTextField.getText());
        // todo ... for now we insert some test data
        personalData.setLastName(lastName);
        personalData.setForename("Bernd");
        personalData.setFamilyStatus(FamilyStatus.Married);
    }


}
