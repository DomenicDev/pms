package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.CreateDocStudentPropertyEvent;
import de.hfu.pms.events.SaveDoctoralStudentEvent;
import de.hfu.pms.events.SuccessfullyAddedUniversityEvent;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.enums.*;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.GuiLoader;
import de.hfu.pms.utils.RepresentationWrapper;
import de.hfu.pms.utils.WrappedEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
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
    private ComboBox<WrappedEntity<Salutation>> salutationComboBox;
    @FXML
    private ComboBox<WrappedEntity<Gender>> genderComboBox;
    @FXML
    private ComboBox<WrappedEntity<FamilyStatus>> familyStatusComboBox;
    @FXML
    private ComboBox<Integer> childrenCountComboBox;

    /* *********** Graduation ***************** */
    // Qualified Graduation
    @FXML
    private ComboBox<WrappedEntity<Graduation>> graduationComboBox;
    @FXML
    private TextField subjectAreaTextField;
    @FXML
    private TextField gradeTextField;
    @FXML
    private ComboBox<WrappedEntity<UniversityDTO>> qualifiedGraduationUniversityComboBox;

    // Target Graduation
    @FXML
    private TextField targetGraduationDegreeTextField;
    @FXML
    private TextField nameOfDissertationTextField;
    @FXML
    private TextField internalSupervisorTextField;
    @FXML
    private ComboBox<WrappedEntity<FacultyHFU>> facultyHFUComboBox;
    @FXML
    private TextField externalSupervisorTextField;
    @FXML
    private TextField externalFacultyTextField;
    @FXML
    private ComboBox<WrappedEntity<UniversityDTO>> externalUniversityComboBox;
    @FXML
    private DatePicker promotionAcceptedDatePicker;
    @FXML
    private DatePicker procedureCompletedDatePicker;
    @FXML
    private ComboBox<WrappedEntity<Rating>> ratingComboBox;

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
    @FXML
    private TextField cancelReasonTextField;

    // Membership
    @FXML
    private CheckBox hfuMemberCheckBox;
    @FXML
    private VBox hfuMembershipVBox;
    @FXML
    private DatePicker memberUntilDatePicker;
    @FXML
    private DatePicker memberSinceDatePicker;
    @FXML
    private CheckBox prolongMembershipCheckBox;
    @FXML
    private HBox extensionMembershipHBox;
    @FXML
    private DatePicker prolongTillDatePicker;
    @FXML
    private CheckBox externalMemberCheckBox;
    @FXML
    private TextField externalCollegeNameTextField;

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
    private TableColumn<EmploymentEntryDTO, LocalDate> employmentBeginTableColumn;
    @FXML
    private TableColumn<EmploymentEntryDTO, LocalDate> employmentEndTableColumn;
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

        refreshCheckBoxes();
    }

    private void initEmploymentTable(ResourceBundle resources) {
        employmentLocationTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentLocation"));
        kindOfEmploymentTableColumn.setCellValueFactory(new PropertyValueFactory<>("kindOfEmployment"));
        employmentCampusTableColumn.setCellValueFactory(new PropertyValueFactory<>("campusOfDeployment"));
        employmentBeginTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentBegin"));
        employmentEndTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentEnd"));
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
        // personal data combo boxes
        familyStatusComboBox.getItems().addAll(RepresentationWrapper.getWrappedFamilyStatus());
        salutationComboBox.getItems().addAll(RepresentationWrapper.getWrappedSalutations());
        genderComboBox.getItems().addAll(RepresentationWrapper.getWrappedGenders());
        childrenCountComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6 ,7, 8 ,9);
        childrenCountComboBox.getSelectionModel().select(0);

        // graduation
        graduationComboBox.getItems().addAll(RepresentationWrapper.getWrappedGraduations());

        // add some common target degrees to show for auto completion
        TextFields.bindAutoCompletion(targetGraduationDegreeTextField, RepresentationWrapper.getTargetDegreeSuggestions());

        // init combo boxes with wrapped entities of the specific type
        facultyHFUComboBox.getItems().addAll(RepresentationWrapper.getWrappedHFUFaculties());
        ratingComboBox.getItems().addAll(RepresentationWrapper.getWrappedRatings());

        // university
        Collection<UniversityDTO> universities = EntityPool.getInstance().getUniversities();
        logger.log(Level.DEBUG, "Init university combo box with " + universities.size() + " item(s)");
        externalUniversityComboBox.getItems().addAll(RepresentationWrapper.getWrappedUniversities(universities));
        qualifiedGraduationUniversityComboBox.getItems().addAll(RepresentationWrapper.getWrappedUniversities(universities));
    }

    public void resetAllInputFields() {
        doctoralStudent = null;
        // todo
    }

    public void fillFormMask(DoctoralStudentDTO doctoralStudent) {
        this.doctoralStudent = doctoralStudent;

        PersonalDataDTO personalData = doctoralStudent.getPersonalData();
        AddressDTO personalAddress = personalData.getAddress();
        SupportDTO support = doctoralStudent.getSupport();
        AlumniStateDTO alumniState = doctoralStudent.getAlumniState();
        EmploymentDTO employment = doctoralStudent.getEmployment();
        QualifiedGraduationDTO qualifiedGraduation = doctoralStudent.getQualifiedGraduation();
        TargetGraduationDTO targetGraduationDTO = doctoralStudent.getTargetGraduation();

        // personal data
        lastNameTextField.setText(personalData.getLastName());
        foreNameTextField.setText(personalData.getForename());
        formerLastNameTextField.setText(personalData.getFormerLastName());
        phoneTextField.setText(personalData.getTelephone());
        titleTextField.setText(personalData.getTitle());
        dateOfBirthDatePicker.setValue(personalData.getDateOfBirth());
        emailTextField.setText(personalData.getEmail());

        // personal data combo boxes
        salutationComboBox.getSelectionModel().select(RepresentationWrapper.find(personalData.getSalutation(), salutationComboBox.getItems()));
        genderComboBox.getSelectionModel().select(RepresentationWrapper.find(personalData.getGender(), genderComboBox.getItems()));
        childrenCountComboBox.getSelectionModel().select(personalData.getNumberOfChildren());

        // address
        plzTextField.setText(personalAddress.getPlz());
        countryTextField.setText(personalAddress.getCountry());
        locationTextField.setText(personalAddress.getLocation());
        streetTextField.setText(personalAddress.getStreet());

        // graduation
        graduationComboBox.getSelectionModel().select(RepresentationWrapper.find(qualifiedGraduation.getGraduation(), graduationComboBox.getItems()));
        subjectAreaTextField.setText(qualifiedGraduation.getSubjectArea());
        gradeTextField.setText(qualifiedGraduation.getGrade());
        qualifiedGraduationUniversityComboBox.getSelectionModel().select(RepresentationWrapper.find(qualifiedGraduation.getGradedUniversity(), RepresentationWrapper.getWrappedUniversities(EntityPool.getInstance().getUniversities())));


        // target graduation
        targetGraduationDegreeTextField.setText(targetGraduationDTO.getTargetGraduationDegree());
        nameOfDissertationTextField.setText(targetGraduationDTO.getNameOfDissertation());
        internalSupervisorTextField.setText(targetGraduationDTO.getInternalSupervisor());
        facultyHFUComboBox.getSelectionModel().select(RepresentationWrapper.find(targetGraduationDTO.getFacultyHFU(), facultyHFUComboBox.getItems()));
        externalSupervisorTextField.setText(targetGraduationDTO.getExternalSupervisor());
        externalFacultyTextField.setText(targetGraduationDTO.getExternalFaculty());
        externalUniversityComboBox.getSelectionModel().select(RepresentationWrapper.find(targetGraduationDTO.getExternalUniversity(), externalUniversityComboBox.getItems()));
        promotionAcceptedDatePicker.setValue(targetGraduationDTO.getPromotionAccepted());
        procedureCompletedDatePicker.setValue(targetGraduationDTO.getProcedureCompleted());
        ratingComboBox.getSelectionModel().select(RepresentationWrapper.find(targetGraduationDTO.getRating(), ratingComboBox.getItems()));
        promotionBeginDatePicker.setValue(targetGraduationDTO.getPromotionAdmissionDate());
        predictedGraduationDatePicker.setValue(targetGraduationDTO.getPrognosticatedPromotionDate());
        promotionAgreementDatePicker.setValue(targetGraduationDTO.getPromotionAgreement());

        // cancel
        if (targetGraduationDTO.getCancelReason() != null && !targetGraduationDTO.getCancelReason().isEmpty()) {
            promotionCanceledCheckBox.setSelected(true);
            cancelReasonTextField.setText(targetGraduationDTO.getCancelReason());
        } else {
            promotionCanceledCheckBox.setSelected(false);
            cancelReasonTextField.setText(null);
        }

        // hfu membership
        memberSinceDatePicker.setValue(targetGraduationDTO.getMembershipHFUKollegBegin());
        memberUntilDatePicker.setValue(targetGraduationDTO.getMembershipHFUKollegEnd());
        prolongTillDatePicker.setValue(targetGraduationDTO.getExtendedMembershipEnd());

        if (memberSinceDatePicker.getValue() == null || memberUntilDatePicker.getValue() == null) {
            hfuMemberCheckBox.setSelected(true);
        } else {
            hfuMemberCheckBox.setSelected(false);
        }

        // external membership
        String externalProgram = externalCollegeNameTextField.getText();
        externalCollegeNameTextField.setText(externalProgram);
        externalMemberCheckBox.setSelected(externalProgram != null);

        // cancel
        String cancelReason = targetGraduationDTO.getCancelReason();
        cancelReasonTextField.setText(cancelReason);
        promotionCanceledCheckBox.setSelected(cancelReason != null);

        // employments
        Set<EmploymentEntryDTO> employmentEntries = employment.getEmploymentEntries();
        employmentTableView.getItems().addAll(employmentEntries);

        // support tables
        travelCostUniversityTableView.getItems().addAll(support.getTravelCostUniversities());
        travelCostConferenceTableView.getItems().addAll(support.getTravelCostConferences());
        consultingSupportTableView.getItems().addAll(support.getConsultingSupports());
        qualificationTableView.getItems().addAll(support.getVisitedQualifications());

        // support text fields
        scholarshipTextField.setText(support.getScholarship());
        awardsTextField.setText(support.getAwards());
        miscellaneousTextArea.setText(support.getAwards());

        // alumni state
        jobTitleTextField.setText(alumniState.getJobTitle());
        employerTextField.setText(alumniState.getEmployer());
        agreementNewsCheckBox.setSelected(alumniState.isAgreementNews());
        alumniState.setAgreementEvaluation(alumniState.isAgreementEvaluation());

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
        boolean validationSuccessful = writeToDoctoralStudentDTO();

        if (validationSuccessful) {
            // post a new save event to notify subscribers about the save action
            // they should take care about the actual saving process
            eventBus.post(new SaveDoctoralStudentEvent(doctoralStudent));

            // after saving, we can reset our input fields
            resetAllInputFields();
        }

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

    @FXML
    public void handleHfuMemberCheckBox(ActionEvent event) {
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionExtendMembershipCheckBox() {
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionExternalMemberCheckBox() {
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionPromotionCanceledCheckBox() {
        refreshCheckBoxes();
    }

    private void refreshCheckBoxes() {
        hfuMembershipVBox.setDisable(!hfuMemberCheckBox.isSelected());
        extensionMembershipHBox.setDisable(!prolongMembershipCheckBox.isSelected());
        externalCollegeNameTextField.setDisable(!externalMemberCheckBox.isSelected());
        cancelReasonTextField.setDisable(!promotionCanceledCheckBox.isSelected());
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

    @Subscribe
    public void handleAddUniversityEvent(SuccessfullyAddedUniversityEvent event) {
        UniversityDTO university = event.getUniversity();
        this.externalUniversityComboBox.getItems().add(RepresentationWrapper.getWrappedUniversity(university));
        this.qualifiedGraduationUniversityComboBox.getItems().add(RepresentationWrapper.getWrappedUniversity(university));
    }

    private boolean writeToDoctoralStudentDTO() {
        PersonalDataDTO personalData = doctoralStudent.getPersonalData();
        AddressDTO personalAddress = personalData.getAddress();
        SupportDTO support = doctoralStudent.getSupport();
        AlumniStateDTO alumniState = doctoralStudent.getAlumniState();
        EmploymentDTO employment = doctoralStudent.getEmployment();
        QualifiedGraduationDTO qualifiedGraduation = doctoralStudent.getQualifiedGraduation();
        TargetGraduationDTO targetGraduationDTO = doctoralStudent.getTargetGraduation();

        FormValidator validator = new FormValidator();

        // process personal data
        if (familyStatusComboBox.getValue() == null) {
            personalData.setFamilyStatus(null);
        } else {
            personalData.setFamilyStatus((familyStatusComboBox.getValue()).getEntity());
        }
        personalData.setLastName((lastNameTextField.getText()));
        personalData.setForename((foreNameTextField.getText()));
        personalData.setFormerLastName(formerLastNameTextField.getText());
        personalData.setTelephone(phoneTextField.getText());
        personalData.setTitle(titleTextField.getText());
        personalData.setDateOfBirth((dateOfBirthDatePicker.getValue()));
        personalData.setEmail((emailTextField.getText()));

        if (validator.comboBoxHasSelectedItem(salutationComboBox)) {
            personalData.setSalutation(salutationComboBox.getValue().getEntity());
        }

        if (validator.comboBoxHasSelectedItem(genderComboBox)) {
            personalData.setGender(genderComboBox.getValue().getEntity());
        }

        if (validator.comboBoxHasSelectedItem(childrenCountComboBox)) {
            personalData.setNumberOfChildren(childrenCountComboBox.getValue());
        }

        personalAddress.setPlz((plzTextField.getText()));
        personalAddress.setCountry((countryTextField.getText()));
        personalAddress.setLocation((locationTextField.getText()));
        personalAddress.setStreet((streetTextField.getText()));


        // process graduation
        if (validator.comboBoxHasSelectedItem(graduationComboBox)) {
            qualifiedGraduation.setGraduation(graduationComboBox.getValue().getEntity());
        }

        if (validator.textFieldNotEmpty(subjectAreaTextField)) {
            qualifiedGraduation.setSubjectArea(subjectAreaTextField.getText());
        }

        if (validator.isValidGrade(gradeTextField)) {
            qualifiedGraduation.setGrade(gradeTextField.getText());
        }

        // target graduation
        if (validator.textFieldNotEmpty(targetGraduationDegreeTextField)) {
            targetGraduationDTO.setTargetGraduationDegree(targetGraduationDegreeTextField.getText());
        }

        if (validator.textFieldNotEmpty(nameOfDissertationTextField)) {
            targetGraduationDTO.setNameOfDissertation(nameOfDissertationTextField.getText());
        }

        if (validator.textFieldNotEmpty(internalSupervisorTextField)) {
            targetGraduationDTO.setInternalSupervisor(internalSupervisorTextField.getText());
        }

        if (validator.comboBoxHasSelectedItem(facultyHFUComboBox)) {
            targetGraduationDTO.setFacultyHFU(facultyHFUComboBox.getValue().getEntity());
        }

        targetGraduationDTO.setExternalSupervisor(externalSupervisorTextField.getText());
        targetGraduationDTO.setExternalFaculty(externalFacultyTextField.getText());

        if (externalUniversityComboBox.getValue() != null) {
            targetGraduationDTO.setExternalUniversity(externalUniversityComboBox.getValue().getEntity());
        } else {
            targetGraduationDTO.setExternalUniversity(null);
        }

        targetGraduationDTO.setPromotionAccepted(promotionAcceptedDatePicker.getValue());
        targetGraduationDTO.setProcedureCompleted(procedureCompletedDatePicker.getValue());

        if (ratingComboBox.getValue() == null) {
            targetGraduationDTO.setRating(null);
        } else {
            targetGraduationDTO.setRating(ratingComboBox.getValue().getEntity());
        }

        // further information
        targetGraduationDTO.setPromotionAdmissionDate(promotionBeginDatePicker.getValue());
        targetGraduationDTO.setPrognosticatedPromotionDate(predictedGraduationDatePicker.getValue());
        targetGraduationDTO.setPromotionAgreement(promotionAgreementDatePicker.getValue());

        if (promotionCanceledCheckBox.isSelected()) {
            targetGraduationDTO.setCancelReason(cancelReasonTextField.getText());
        } else {
            targetGraduationDTO.setCancelReason(null);
        }

        // hfu membership
        if (hfuMemberCheckBox.isSelected()) {
            targetGraduationDTO.setMembershipHFUKollegBegin(memberSinceDatePicker.getValue());
            targetGraduationDTO.setMembershipHFUKollegEnd(memberUntilDatePicker.getValue());

            if (prolongMembershipCheckBox.isSelected()) {
                targetGraduationDTO.setExtendedMembershipEnd(prolongTillDatePicker.getValue());
            }

        } else {
            // check box is disabled, so we make sure all subfields are equal to null
            targetGraduationDTO.setMembershipHFUKollegBegin(null);
            targetGraduationDTO.setMembershipHFUKollegEnd(null);
            targetGraduationDTO.setExtendedMembershipEnd(null);
        }

        // external membership
        targetGraduationDTO.setExternalProgram(externalMemberCheckBox.isSelected() ? externalCollegeNameTextField.getText() : null);

        // cancel reason
        targetGraduationDTO.setCancelReason(promotionCanceledCheckBox.isSelected() ? cancelReasonTextField.getText() : null);


        // process employment relationship
        Set<EmploymentEntryDTO> employmentEntries = new HashSet<>(employmentTableView.getItems());
        employment.setEmploymentEntries(employmentEntries);


        // process Support
        support.setTravelCostUniversities(new HashSet<>(travelCostUniversityTableView.getItems()));
        support.setTravelCostConferences(new HashSet<>(travelCostConferenceTableView.getItems()));
        support.setConsultingSupports(new HashSet<>(consultingSupportTableView.getItems()));
        support.setVisitedQualifications(new HashSet<>(qualificationTableView.getItems()));
        support.setScholarship(scholarshipTextField.getText());
        support.setAwards(awardsTextField.getText());
        support.setMiscellaneous(miscellaneousTextArea.getText());


        // process alumni-state
        alumniState.setJobTitle((jobTitleTextField.getText()));
        alumniState.setEmployer((employerTextField.getText()));
        alumniState.setAgreementNews(agreementNewsCheckBox.isSelected());
        alumniState.setAgreementEvaluation(agreementEvaluationCheckBox.isSelected());

        return validator.validationSuccessful();
    }
}
