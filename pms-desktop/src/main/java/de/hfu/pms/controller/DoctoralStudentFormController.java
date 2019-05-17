package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.EventBusSystem;
import de.hfu.pms.events.CreateDocStudentPropertyEvent;
import de.hfu.pms.events.CreateEmploymentEntryEvent;
import de.hfu.pms.events.SaveDoctoralStudentEvent;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;
import de.hfu.pms.shared.enums.FamilyStatus;
import de.hfu.pms.utils.GuiLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
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

    // Support
    // Tables
    @FXML
    private TableView<TravelCostUniversityDTO> travelCostUniversityTableView;
    @FXML
    private TableColumn<TravelCostUniversityDTO, LocalDate> travelCostUniversityDateTableColumn;
    @FXML
    private TableColumn<TravelCostUniversityDTO, BigDecimal> travelCostUniversitySupportTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        // setup employment table columns
        initEmploymentTable(resources);

        initSupportTables(resources);
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

    private void initSupportTables(ResourceBundle resources) {
        // Travel Cost University
        travelCostUniversityDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        travelCostUniversitySupportTableColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
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
            this.doctoralStudent.setSupport(new SupportDTO());
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
        GuiLoader.createModalWindow(GuiLoader.EMPLOYMENT, 400, 300, false);
    }

    // SUPPORT
    @FXML
    public void handleOnActionAddTravelCostUniversityButton() throws IOException {
        GuiLoader.createModalWindow(GuiLoader.TRAVEL_COST_UNIVERSITY, 400, 300, false);
    }

    @FXML
    public void handleOnActionAddTravelCostConferenceButton() throws IOException {
        GuiLoader.createModalWindow(GuiLoader.TRAVEL_COST_CONFERENCE, 400, 300, false);
    }

    @Subscribe
    public void handleCreateEmploymentEntryEvent(CreateEmploymentEntryEvent event) {
        EmploymentEntryDTO entry = event.getEmploymentEntryDTO();
        employmentTableView.getItems().add(entry);
    }

    @Subscribe
    public void handleCreateTravelCostUniversityEvent(CreateDocStudentPropertyEvent event) {
        Object property = event.getProperty();
        if (property instanceof TravelCostUniversityDTO) {
            travelCostUniversityTableView.getItems().add((TravelCostUniversityDTO) property);
        } else if (property instanceof TravelCostConferenceDTO) {
            // todo
        }
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

        SupportDTO support = doctoralStudent.getSupport();
        HashSet<TravelCostUniversityDTO> travelCostsUni = new HashSet<>(travelCostUniversityTableView.getItems());
        support.setTravelCostUniversities(travelCostsUni);
    }


}
