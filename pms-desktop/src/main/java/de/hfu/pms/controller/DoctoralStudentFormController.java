package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.EventBusSystem;
import de.hfu.pms.events.CreateDocStudentPropertyEvent;
import de.hfu.pms.events.SaveDoctoralStudentEvent;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;
import de.hfu.pms.shared.enums.FamilyStatus;
import de.hfu.pms.utils.GuiLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

public class DoctoralStudentFormController implements Initializable {

    private Logger logger = Logger.getLogger(DoctoralStudentFormController.class);

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

    /* ***********Support ***************** */
    // travel cost university
    @FXML
    private TableView<TravelCostUniversityDTO> travelCostUniversityTableView;
    @FXML
    private TableColumn<TravelCostUniversityDTO, LocalDate> travelCostUniversityDateTableColumn;
    @FXML
    private TableColumn<TravelCostUniversityDTO, BigDecimal> travelCostUniversitySupportTableColumn;

    // travel cost conference
    @FXML
    private TableView<TravelCostConferenceDTO> travelCostConferenceTableView;
    @FXML
    private TableColumn<TravelCostConferenceDTO, LocalDate> travelCostConferenceDateTableColumn;
    @FXML
    private TableColumn<TravelCostConferenceDTO, String> travelCostConferenceTitleTableColumn;
    @FXML
    private TableColumn<TravelCostConferenceDTO, String> travelCostConferenceLocationTableColumn;
    @FXML
    private TableColumn<TravelCostConferenceDTO, String> travelCostConferenceSumSupportTableColumn;

    // Consulting Support
    @FXML
    private TableView<ConsultingSupportDTO> consultingSupportTableView;
    @FXML
    private TableColumn<ConsultingSupportDTO, LocalDate> consultingSupportDateTableColumn;
    @FXML
    private TableColumn<ConsultingSupportDTO, String> consultingSupportTypeTableColumn;
    @FXML
    private TableColumn<ConsultingSupportDTO, String> consultingSupportDurationTableColumn;

    // Qualifications
    @FXML
    private TableView<VisitedQualificationDTO> qualificationTableView;
    @FXML
    private TableColumn<VisitedQualificationDTO, LocalDate> qualificationDateTableColumn;
    @FXML
    private TableColumn<VisitedQualificationDTO, String> qualificationNameTableColumn;


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

        // Travel Cost Conference
        travelCostConferenceDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        travelCostConferenceTitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("conferenceTitle"));
        travelCostConferenceLocationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        travelCostConferenceSumSupportTableColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

        // Consulting Support
        consultingSupportDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("consultingDate"));
        consultingSupportTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("consultingType"));
        consultingSupportDurationTableColumn.setCellValueFactory(new PropertyValueFactory<>("consultingDuration"));

        // Qualifications
        qualificationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("qualificationDate"));
        qualificationNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfQualification"));

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

    @FXML
    public void handleOnActionAddConsultingButton(ActionEvent actionEvent) throws IOException {
        GuiLoader.createModalWindow(GuiLoader.CONSULTING_SUPPORT, 450, 250, false);
    }

    @FXML
    public void handleOnActionAddQualificationButton(ActionEvent actionEvent) throws IOException {
        GuiLoader.createModalWindow(GuiLoader.QUALIFICATION, 450, 200, false);
    }

    @Subscribe
    public void handleCreateTravelCostUniversityEvent(CreateDocStudentPropertyEvent event) {
        Object property = event.getProperty();
        if (property instanceof TravelCostUniversityDTO) {
            travelCostUniversityTableView.getItems().add((TravelCostUniversityDTO) property);
        } else if (property instanceof TravelCostConferenceDTO) {
            travelCostConferenceTableView.getItems().add((TravelCostConferenceDTO) property);
        } else if (property instanceof ConsultingSupportDTO) {
            consultingSupportTableView.getItems().add((ConsultingSupportDTO) property);
        } else if (property instanceof VisitedQualificationDTO) {
            qualificationTableView.getItems().add((VisitedQualificationDTO) property);
        } else if (property instanceof EmploymentEntryDTO) {
            employmentTableView.getItems().add((EmploymentEntryDTO) property);
        } else {
            logger.log(Level.WARN, "No defined handling for specified property: " + property.getClass().getName());
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
