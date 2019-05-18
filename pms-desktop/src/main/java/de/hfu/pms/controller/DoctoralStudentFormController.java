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
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class DoctoralStudentFormController implements Initializable {

    private Logger logger = Logger.getLogger(DoctoralStudentFormController.class);

    private DoctoralStudentDTO doctoralStudent = null;

    private EventBus eventBus = EventBusSystem.getEventBus();

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    // Personal Data
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField foreNameTextField;
    @FXML
    private TextField formerLastNameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField plzTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private ComboBox salutationComboBox;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private ComboBox<WrappedEntity<FamilyStatus>> familyStatusComboBox;
    @FXML
    private ComboBox childrenCountComboBox;

    /* *********** Graduation ***************** */
    // Qualified Graduation
    @FXML
    private ComboBox graduationComboBox;
    @FXML
    private TextField subjectAreaTextField;
    @FXML
    private TextField gradeTextField;
    @FXML
    private ComboBox qualifiedGraduationUniversityComboBox;

    // Target Graduation
    @FXML
    private ComboBox targetGraduationDegreeTextField;
    @FXML
    private TextField nameOfDissertationTextField;
    @FXML
    private TextField internalSupervisorTextField;
    @FXML
    private ComboBox facultyHFUComboBox;
    @FXML
    private TextField externalSupervisorTextField;
    @FXML
    private TextField externalFacultyTextField;
    @FXML
    private ComboBox externalUniversityComboBox;
    @FXML
    private DatePicker promotionAcceptedDatePicker;
    @FXML
    private DatePicker procedureCompletedDatePicker;
    @FXML
    private ComboBox ratingComboBox;

    // Further Information
    @FXML
    private DatePicker promotionBeginDatePicker;
    @FXML
    private DatePicker predictedGraduationDatePicker;
    @FXML
    private DatePicker promotionAgreementDatePicker;

    // Cancel
    @FXML
    private CheckBox promotionCanceledCheckBox;
    private TextField cancelReason;

    // Membership
    @FXML
    private CheckBox hfuMemberCheckBox;
    @FXML
    private DatePicker memberUntilDatePicker;
    @FXML
    private DatePicker memberSinceDatePicker;
    @FXML
    private CheckBox prolongMembershipCheckBox;
    @FXML
    private DatePicker prolongTillDatePicker;
    @FXML
    private CheckBox externMemberCheckBox;
    @FXML
    private TextField externCollegeNameTextField;

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

    // Other
    @FXML
    private TextField scholarshipTextField;
    @FXML
    private TextField awardsTextField;
    @FXML
    private TextArea miscellaneousTextArea;

    // Alumni-Status
    @FXML
    private TextField jobTitleTextField;
    @FXML
    private TextField employerTextField;
    @FXML
    private CheckBox agreementNewsCheckBox;
    @FXML
    private CheckBox agreementEvaluationCheckBox;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);

        // setup table columns
        initEmploymentTable(resources);
        initSupportTables(resources);

        initComboBoxes();
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

    private void initComboBoxes() {
        // family status
        familyStatusComboBox.getItems().addAll(RepresentationWrapper.getWrappedFamilyStatus());
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
        PersonalDataDTO personalData = doctoralStudent.getPersonalData();
        AddressDTO personalAddress = personalData.getAddress();
        SupportDTO support = doctoralStudent.getSupport();
        AlumniStateDTO alumniState = doctoralStudent.getAlumniState();
        EmploymentDTO employment = doctoralStudent.getEmployment();


        // process personal data
        personalData.setFamilyStatus(checkForNull(familyStatusComboBox.getValue()).getEntity());
        personalData.setLastName(checkForNull(lastNameTextField.getText()));
        personalData.setForename(checkForNull(foreNameTextField.getText()));
        personalData.setFormerLastName(checkForNull(formerLastNameTextField.getText()));
        personalData.setTelephone(checkForNull(phoneTextField.getText()));
        personalData.setTitle(checkForNull(titleTextField.getText()));
        personalData.setDateOfBirth(checkForNull(dateOfBirthDatePicker.getValue()));
        personalData.setEmail(checkForNull(emailTextField.getText()));

        // todo: get selected item in combobox, enums in fxml (salutation, gender)
        // ObservableList<Integer> numberOfChildren = childrenCountComboBox.getItems();
        // personalData.setNumberOfChildren(numberOfChildren.get(childrenCountComboBox.getSelectedIndex()));
        // ComboBox childrenCount;
        // ComboBox salutationComboBox;
        // ComboBox genderComboBox;

        personalAddress.setPlz(checkForNull(plzTextField.getText()));
        personalAddress.setCountry(checkForNull(countryTextField.getText()));
        personalAddress.setLocation(checkForNull(locationTextField.getText()));
        personalAddress.setStreet(checkForNull(streetTextField.getText()));


        // process graduation
        // todo


        // process employment relationship
        Set<EmploymentEntryDTO> employmentEntries = new HashSet<>(employmentTableView.getItems());
        employment.setEmploymentEntries(employmentEntries);


        // process Support
        HashSet<TravelCostUniversityDTO> travelCostsUni = new HashSet<>(travelCostUniversityTableView.getItems());
        support.setTravelCostUniversities(travelCostsUni);
        support.setScholarship(checkForNull(scholarshipTextField.getText()));
        support.setAwards(checkForNull(awardsTextField.getText()));
        support.setMiscellaneous(checkForNull(miscellaneousTextArea.getText()));


        // process alumni-state
        alumniState.setJobTitle(checkForNull(jobTitleTextField.getText()));
        alumniState.setEmployer(checkForNull(emailTextField.getText()));
        alumniState.setAgreementNews(agreementNewsCheckBox.isSelected());
        alumniState.setAgreementEvaluation(agreementEvaluationCheckBox.isSelected());
    }
}
