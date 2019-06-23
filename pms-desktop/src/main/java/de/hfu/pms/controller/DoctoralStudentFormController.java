package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.*;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.enums.*;
import de.hfu.pms.utils.*;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.tools.Borders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

public class DoctoralStudentFormController implements Initializable {

    private Logger logger = Logger.getLogger(DoctoralStudentFormController.class);
    private DoctoralStudentDTO doctoralStudent = null;
    private EventBus eventBus = EventBusSystem.getEventBus();
    private ResourceBundle bundle;

    private final Image vacantPhotoImage = new Image("/images/user-shape.png");

    @FXML
    private Button saveButton;

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
    @FXML
    private ImageView photoImageView;

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
    private ComboBox<WrappedEntity<FacultyDTO>> facultyHFUComboBox;
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
    private DatePicker cancelDateDatePicker;
    @FXML
    private TextField cancelReasonTextField;
    @FXML
    private VBox cancelChildrenBox;

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

    // Documents
    @FXML
    private ListView<DocumentInformationDTO> documentsListView;


    // ---------------------------- //
    //        SECTIONS              //
    // ---------------------------- //
    @FXML
    private Pane generalInformationNode;
    @FXML
    private Pane contactInformationPane;
    @FXML
    private Pane qualifiedGraduationInformationPane;
    @FXML
    private Pane targetGraduationInformationPane;
    @FXML
    private Pane moreInformationPane;
    @FXML
    private Pane cancelInformationPane;
    @FXML
    private Pane membershipPane;

    // ---------------------------- //
    //        CONTROL FLAGS         //
    // ---------------------------- //
    private boolean changedImage;
    private boolean deletedImage;
    private boolean personalDataChanged;
    private boolean qualificationChanged;
    private boolean promotionChanged;
    private boolean employmentChanged;
    private boolean supportChanged;
    private boolean alumniStateChanged;

    private boolean editMode = false;
    private boolean readOnly = false;

    private Map<DocumentInformationDTO, File> addedDocuments = new HashMap<>();
    private Collection<DocumentInformationDTO> deletedDocuments = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(this);
        this.bundle = resources;

        // setup table columns
        initEmploymentTable(resources);
        initSupportTables(resources);

        initDocumentsListView();
        initComboBoxes();
        initBorders(resources);
        initDatePicker();
        initPhotoView();

        refreshCheckBoxes();
    }

    private void initDatePicker() {
        dateOfBirthDatePicker.getEditor().setOnKeyTyped(event -> onPersonalDataChange());
        promotionBeginDatePicker.getEditor().setOnKeyTyped(event -> onPersonalDataChange());
        predictedGraduationDatePicker.getEditor().setOnKeyTyped(event -> onPersonalDataChange());
        promotionAgreementDatePicker.getEditor().setOnKeyTyped(event -> onPersonalDataChange());
    }

    private void initPhotoView() {
        photoImageView.setImage(vacantPhotoImage);
    }

    private void initBorders(ResourceBundle bundle) {
        // personal data
        createTitledBorder(generalInformationNode, bundle.getString("ui.label.general"));
        createTitledBorder(contactInformationPane, bundle.getString("ui.label.address_and_contact"));
        createTitledBorder(qualifiedGraduationInformationPane, bundle.getString("ui.label.qualified_graduation_information"));

        // target graduation
        createTitledBorder(targetGraduationInformationPane, bundle.getString("ui.label.target_graduation_information"));
        createTitledBorder(cancelInformationPane, bundle.getString("ui.label.cancel_of_promotion"));
        createTitledBorder(membershipPane, bundle.getString("ui.label.membership_college"));
        createTitledBorder(moreInformationPane, bundle.getString("ui.label.more_information_regarding_promotion"));
    }

    private void createTitledBorder(Node node, String title) {
        Pane parent = (Pane) node.getParent();
        Node n = Borders.wrap(node)
                .lineBorder()
                .title(title)
                .buildAll();
        parent.getChildren().add(0, n);
    }

    private void initEmploymentTable(ResourceBundle resources) {
        employmentLocationTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentLocation"));
        kindOfEmploymentTableColumn.setCellValueFactory(new PropertyValueFactory<>("kindOfEmployment"));
        employmentCampusTableColumn.setCellValueFactory(new PropertyValueFactory<>("campusOfDeployment"));
        employmentBeginTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentBegin"));
        employmentEndTableColumn.setCellValueFactory(new PropertyValueFactory<>("employmentEnd"));

        // listener for change flag
        employmentTableView.getItems().addListener((ListChangeListener<EmploymentEntryDTO>) c -> onEmploymentChanged());
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

        // add listener for control flag
        travelCostConferenceTableView.getItems().addListener((ListChangeListener<TravelCostConferenceDTO>) c -> onSupportChanged());
        travelCostUniversityTableView.getItems().addListener((ListChangeListener<TravelCostUniversityDTO>) c -> onSupportChanged());
        consultingSupportTableView.getItems().addListener((ListChangeListener<ConsultingSupportDTO>) c -> onSupportChanged());
        qualificationTableView.getItems().addListener((ListChangeListener<VisitedQualificationDTO>) c -> onSupportChanged());

    }

    private void initComboBoxes() {
        // personal data combo boxes
        familyStatusComboBox.getItems().addAll(RepresentationWrapper.getWrappedFamilyStatus());
        salutationComboBox.getItems().addAll(RepresentationWrapper.getWrappedSalutations());
        genderComboBox.getItems().addAll(RepresentationWrapper.getWrappedGenders());
        childrenCountComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        childrenCountComboBox.getSelectionModel().select(0);

        // graduation
        graduationComboBox.getItems().addAll(RepresentationWrapper.getWrappedGraduations());

        // add some common target degrees to show for auto completion
        TextFields.bindAutoCompletion(targetGraduationDegreeTextField, RepresentationWrapper.getTargetDegreeSuggestions());

        // faculty
        Collection<FacultyDTO> faculties = EntityPool.getInstance().getFaculties();

        // init combo boxes with wrapped entities of the specific type
        updateFacultyCombobox();
        ratingComboBox.getItems().addAll(RepresentationWrapper.getWrappedRatings());

        // university
        Collection<UniversityDTO> universities = EntityPool.getInstance().getUniversities();
        logger.log(Level.DEBUG, "Init university combo box with " + universities.size() + " item(s)");
        externalUniversityComboBox.getItems().addAll(RepresentationWrapper.getWrappedUniversities(universities));
        qualifiedGraduationUniversityComboBox.getItems().addAll(RepresentationWrapper.getWrappedUniversities(universities));
    }

    public void fillFormMask(DoctoralStudentDTO doctoralStudent) {
        if (doctoralStudent == null) {
            return;
        }

        if (doctoralStudent.isAnonymized()) {
            readOnly = true;
            saveButton.setDisable(true);
        }

        this.doctoralStudent = doctoralStudent;
        this.editMode = true;

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

        // photo
        byte[] photoData = doctoralStudent.getPhoto();
        if (photoData != null) {
            try {
                BufferedImage bufferedImage = ImageUtils.getImage(photoData);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                photoImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // personal data combo boxes
        salutationComboBox.getSelectionModel().select(RepresentationWrapper.find(personalData.getSalutation(), salutationComboBox.getItems()));
        genderComboBox.getSelectionModel().select(RepresentationWrapper.find(personalData.getGender(), genderComboBox.getItems()));
        childrenCountComboBox.getSelectionModel().select(personalData.getNumberOfChildren());
        familyStatusComboBox.getSelectionModel().select(RepresentationWrapper.find(personalData.getFamilyStatus(), familyStatusComboBox.getItems()));

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
        promotionCanceledCheckBox.setSelected(targetGraduationDTO.isPromotionCanceled());
        if (targetGraduationDTO.getCancelDate() != null || (targetGraduationDTO.getCancelReason() != null && !targetGraduationDTO.getCancelReason().isEmpty())) {
            promotionCanceledCheckBox.setSelected(true);
            cancelReasonTextField.setText(targetGraduationDTO.getCancelReason());
            cancelDateDatePicker.setValue(targetGraduationDTO.getCancelDate());
        } else {
            promotionCanceledCheckBox.setSelected(false);
            cancelReasonTextField.setText(null);
            cancelDateDatePicker.setValue(null);
        }

        // hfu membership
        hfuMemberCheckBox.setSelected(targetGraduationDTO.isMemberOfHFUKolleg());
        memberSinceDatePicker.setValue(targetGraduationDTO.getMembershipHFUKollegBegin());
        memberUntilDatePicker.setValue(targetGraduationDTO.getMembershipHFUKollegEnd());
        prolongTillDatePicker.setValue(targetGraduationDTO.getExtendedMembershipEnd());
        prolongMembershipCheckBox.setSelected(targetGraduationDTO.isMembershipExtended());

        // external membership
        externalMemberCheckBox.setSelected(targetGraduationDTO.isMemberOfExternalKolleg());
        externalCollegeNameTextField.setText(targetGraduationDTO.getExternalProgram());

        // cancel
        String cancelReason = targetGraduationDTO.getCancelReason();
        LocalDate cancelDate = targetGraduationDTO.getCancelDate();
        cancelReasonTextField.setText(cancelReason);
        cancelDateDatePicker.setValue(cancelDate);
        promotionCanceledCheckBox.setSelected(targetGraduationDTO.isPromotionCanceled());

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
        agreementEvaluationCheckBox.setSelected(alumniState.isAgreementEvaluation());

        // documents
        if (doctoralStudent.getDocuments() != null) {
            documentsListView.getItems().addAll(doctoralStudent.getDocuments());
        }

        // will enable/disable children based on their value
        refreshCheckBoxes();

    }

    @FXML
    public void handleOnActionCancelButton() {
        if (!readOnly && hasChanges()) {
            GuiLoader.showYesNoCancelAlert(
                    Alert.AlertType.INFORMATION,
                    bundle.getString("ui.alert.title.unsaved_changed"),
                    bundle.getString("ui.alert.content.unsaved_changes_really_cancel"),
                    this::handleOnActionSaveButton,
                    this::cancel,
                    null
            );
        } else {
            cancel();
        }
    }

    /**
     * Returns true if there are any changes, false otherwise
     *
     * @return true if there any changes in the form mask, false otherwise
     */
    private boolean hasChanges() {
        return changedImage
                || deletedImage
                || personalDataChanged
                || qualificationChanged
                || promotionChanged
                || employmentChanged
                || supportChanged
                || alumniStateChanged;
    }

    private void resetChangeControlFlags() {
        changedImage
                = deletedImage
                = personalDataChanged
                = qualificationChanged
                = promotionChanged
                = employmentChanged
                = supportChanged
                = alumniStateChanged = false;
    }

    private void postExitScreenEvent() {
        eventBus.post(new SwitchDoctoralStudentScreenEvent(DoctoralStudentMainContentController.DoctoralStudentScreen.OVERVIEW));
    }


    private void cancel() {
        postExitScreenEvent();
    }

    @FXML
    public void handleOnActionSaveButton() {
        if (readOnly) {
            return;
        }


        if (doctoralStudent == null) {
            // we are not editing an existing object but actually creating a new one
            this.doctoralStudent = new DoctoralStudentDTO();
            this.doctoralStudent.setPersonalData(new PersonalDataDTO());
            this.doctoralStudent.setSupport(new SupportDTO());
            // todo add missing parts....

            // write form data to java object


        } else {

            // if we are here, we edit an already existing student
            // we must not set the ID !!!


        }

        boolean validationSuccessful = writeToDoctoralStudentDTO();

        if (validationSuccessful) {

            if (editMode) {

                DoctoralStudentDTO doctoralStudentDTO = this.doctoralStudent; // maybe replace this one

                Long id = doctoralStudentDTO.getId();


                // we are updating an already created entity
                // to make granular updated we look up what has been changed
                // and patch the specific fields
                PatchDoctoralStudentDTO patchDTO = new PatchDoctoralStudentDTO(id);

                // see what has been changed
                if (changedImage) {
                    patchDTO.setChangedPhoto(true);
                    patchDTO.setPhoto(doctoralStudent.getPhoto());
                    logger.log(Level.DEBUG, "profile photo has been changed");
                }
                if (deletedImage) {
                    patchDTO.setChangedPhoto(true);
                    patchDTO.setPhoto(null);
                    logger.log(Level.DEBUG, "profile photo has been deleted");
                }
                if (personalDataChanged) {
                    patchDTO.setPatchedPersonalData(doctoralStudent.getPersonalData());
                    logger.log(Level.DEBUG, "personal data has been changed");
                }
                if (qualificationChanged) {
                    patchDTO.setPatchedQualifiedGraduation(doctoralStudent.getQualifiedGraduation());
                    logger.log(Level.DEBUG, "qualification data has been changed");
                }
                if (promotionChanged) {
                    patchDTO.setPatchedTargetGraduation(doctoralStudent.getTargetGraduation());
                    logger.log(Level.DEBUG, "promotion data has been changed");
                }
                if (employmentChanged) {
                    patchDTO.setPatchedEmployment(doctoralStudent.getEmployment());
                    logger.log(Level.DEBUG, "employment data has been changed");
                }
                if (supportChanged) {
                    patchDTO.setPatchedSupport(doctoralStudent.getSupport());
                    logger.log(Level.DEBUG, "support data has been changed");
                }
                if (alumniStateChanged) {
                    patchDTO.setPatchedAlumniState(doctoralStudent.getAlumniState());
                    logger.log(Level.DEBUG, "alumni state data has been changed");
                }

                // process documents
                if (!addedDocuments.isEmpty()) {
                    Collection<DocumentDTO> docsToAdd = createCollectionFromFiles(addedDocuments.values());
                    patchDTO.setDocumentsToAdd(docsToAdd);
                }

                if (!deletedDocuments.isEmpty()) {
                    Collection<Long> docsToRemove = new HashSet<>();
                    for (DocumentInformationDTO informationDTO : deletedDocuments) {
                        Long docId = informationDTO.getId();
                        if (docId != null) {
                            docsToRemove.add(docId);
                        }
                    }
                    patchDTO.setDocumentsToRemove(docsToRemove);
                }

                // post patch event
                RequestPatchDoctoralStudentEvent updateEvent = new RequestPatchDoctoralStudentEvent(patchDTO);
                eventBus.post(updateEvent);

                // reset control flags
                resetChangeControlFlags();
            } else {


                CreateDoctoralStudentDTO createDTO = new CreateDoctoralStudentDTO();
                createDTO.setPersonalData(doctoralStudent.getPersonalData());
                createDTO.setQualifiedGraduation(doctoralStudent.getQualifiedGraduation());
                createDTO.setTargetGraduation(doctoralStudent.getTargetGraduation());
                createDTO.setEmployment(doctoralStudent.getEmployment());
                createDTO.setSupport(doctoralStudent.getSupport());
                createDTO.setAlumniState(doctoralStudent.getAlumniState());
                createDTO.setPhoto(doctoralStudent.getPhoto());

                // documents
                Collection<DocumentDTO> documentsToAdd = createCollectionFromFiles(addedDocuments.values());
                logger.log(Level.DEBUG, "Adding " + documentsToAdd.size() + " documents for upload");

                createDTO.setDocuments(documentsToAdd);

                // post a new save event to notify subscribers about the save action
                // they should take care about the actual saving process
                eventBus.post(new RequestCreateDoctoralStudentEvent(createDTO));


            }

            postExitScreenEvent();
        }

    }

    private Collection<DocumentDTO> createCollectionFromFiles(Collection<File> files) {
        Collection<DocumentDTO> documents = new HashSet<>();
        for (File file : files) {
            DocumentDTO documentDTO = createDocumentDTO(file);
            if (documentDTO != null) {
                documents.add(documentDTO);
            }
        }
        return documents;
    }

    private DocumentDTO createDocumentDTO(File fileToLoad) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setFilename(fileToLoad.getName());
        // try to read document
        try {
            documentDTO.setData(Files.readAllBytes(fileToLoad.toPath()));
        } catch (IOException e) {
            logger.log(Level.DEBUG, "Failed to read document " + fileToLoad.getName() + ". Cancel saving process.");
            return null;
        }
        return documentDTO;
    }

    // EMPLOYMENT
    @FXML
    public void handleOnActionAddEmploymentEntryButton() throws IOException {
        GuiLoader.createModalWindow(GuiLoader.EMPLOYMENT, 400, 300, false);
    }

    @FXML
    public void handleOnActionDeleteEmployment() {
        JavaFxUtils.removeSelectedItems(employmentTableView);
    }

    @FXML
    public void handleOnActionDeleteTravelCostButton() {
        JavaFxUtils.removeSelectedItems(travelCostUniversityTableView);
    }

    @FXML
    public void handleOnActionDeleteTravelCostConferenceButton() {
        JavaFxUtils.removeSelectedItems(travelCostConferenceTableView);
    }

    @FXML
    public void handleOnActionDeleteConsultingButton() {
        JavaFxUtils.removeSelectedItems(consultingSupportTableView);
    }

    @FXML
    public void handleOnActionDeleteQualificationsButton() {
        JavaFxUtils.removeSelectedItems(qualificationTableView);
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


    // --------------------------------- //
    //          CHECK BOXES              //
    // --------------------------------- //
    @FXML
    public void handleHfuMemberCheckBox() {
        onPromotionChanged();
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionExtendMembershipCheckBox() {
        onPromotionChanged();
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionExternalMemberCheckBox() {
        onPromotionChanged();
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionPromotionCanceledCheckBox() {
        onPromotionChanged();
        refreshCheckBoxes();
    }

    @FXML
    public void handleOnActionAddUniversityButton() throws IOException {
        GuiLoader.createModalWindow(GuiLoader.UNIVERSITY_FORM_SCREEN, 250, 300, false);
    }

    @FXML
    public void handleOnActionEditFacultyButton() throws IOException {
        GuiLoader.createModalWindow(GuiLoader.FACULTY_FORM_SCREEN, 250, 300, false);
    }

    @FXML
    public void handleOnActionChangePhotoButton(ActionEvent event) {
        // create file chooser
        FileChooser fileChooser = new FileChooser();
        // fileChooser.setInitialDirectory(new File(System.getProperty("user.name")));

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // open dialog
        File imageFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (imageFile == null) {
            // no image has been selected, so we just return
            return;
        }

        // show image in image view
        String path = imageFile.toURI().toString();
        Image image = new Image(path);
        photoImageView.setImage(image);

        // set control flags
        changedImage = true;
        deletedImage = false;
    }

    @FXML
    public void handleOnActionDeletePhotoButton() {
        photoImageView.setImage(vacantPhotoImage);
        deletedImage = true;
        changedImage = false;
    }

    //----------------------------------//
    //      HANDLE CHANGE EVENTS        //
    //----------------------------------//
    @FXML
    public void onPersonalDataChange() {
        this.personalDataChanged = true;
    }

    @FXML
    public void onQualificationChanged() {
        this.qualificationChanged = true;
    }

    @FXML
    public void onPromotionChanged() {
        this.promotionChanged = true;
    }

    @FXML
    public void onEmploymentChanged() {
        this.employmentChanged = true;
    }

    @FXML
    public void onSupportChanged() {
        this.supportChanged = true;
    }

    @FXML
    public void onAlumniStateChanged() {
        this.alumniStateChanged = true;
    }


    private void refreshCheckBoxes() {
        hfuMembershipVBox.setDisable(!hfuMemberCheckBox.isSelected());
        extensionMembershipHBox.setDisable(!prolongMembershipCheckBox.isSelected());
        externalCollegeNameTextField.setDisable(!externalMemberCheckBox.isSelected());
        cancelChildrenBox.setDisable(!promotionCanceledCheckBox.isSelected());
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
            personalData.setFamilyStatus(familyStatusComboBox.getValue().getEntity());
        }

        if (validator.textFieldNotEmpty(lastNameTextField)) {
            personalData.setLastName(lastNameTextField.getText());
        }

        if (validator.textFieldNotEmpty(foreNameTextField)) {
            personalData.setForename((foreNameTextField.getText()));
        }

        if (validator.hasSetValue(dateOfBirthDatePicker)) {
            personalData.setDateOfBirth(dateOfBirthDatePicker.getValue());
        }

        if (validator.textFieldNotEmpty(emailTextField)) {
            personalData.setEmail((emailTextField.getText()));
        }

        if (validator.comboBoxHasSelectedItem(salutationComboBox)) {
            personalData.setSalutation(salutationComboBox.getValue().getEntity());
        }

        if (validator.comboBoxHasSelectedItem(genderComboBox)) {
            personalData.setGender(genderComboBox.getValue().getEntity());
        }

        if (validator.comboBoxHasSelectedItem(childrenCountComboBox)) {
            personalData.setNumberOfChildren(childrenCountComboBox.getValue());
        }

        personalData.setFormerLastName(formerLastNameTextField.getText());
        personalData.setTelephone(phoneTextField.getText());
        personalData.setTitle(titleTextField.getText());

        if (validator.textFieldNotEmpty(plzTextField)) {
            personalAddress.setPlz((plzTextField.getText()));
        }

        if (validator.textFieldNotEmpty(countryTextField)) {
            personalAddress.setCountry((countryTextField.getText()));
        }

        if (validator.textFieldNotEmpty(locationTextField)) {
            personalAddress.setLocation((locationTextField.getText()));
        }

        if (validator.textFieldNotEmpty(streetTextField)) {
            personalAddress.setStreet((streetTextField.getText()));
        }

        // check if photo has changed
        if (changedImage) {
            // we changed the picture so entity
            Image image = photoImageView.getImage();

            try {
                byte[] imageBytes;
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                imageBytes = ImageUtils.getImageData(bufferedImage);
                doctoralStudent.setPhoto(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // set photo to null if deleted
        if (deletedImage) {
            doctoralStudent.setPhoto(null);
        }

        // process graduation
        if (validator.comboBoxHasSelectedItem(graduationComboBox)) {
            qualifiedGraduation.setGraduation(graduationComboBox.getValue().getEntity());
        }

        qualifiedGraduation.setSubjectArea(subjectAreaTextField.getText());


        if (validator.isValidGrade(gradeTextField)) {
            qualifiedGraduation.setGrade(gradeTextField.getText());
        }

        if (validator.comboBoxHasSelectedItem(qualifiedGraduationUniversityComboBox)) {
            qualifiedGraduation.setGradedUniversity(qualifiedGraduationUniversityComboBox.getValue().getEntity());
        }

        // target graduation
        targetGraduationDTO.setTargetGraduationDegree(targetGraduationDegreeTextField.getText());


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
        if (validator.hasSetValue(promotionBeginDatePicker)) {
            targetGraduationDTO.setPromotionAdmissionDate(promotionBeginDatePicker.getValue());
        }
        if (validator.hasSetValue(predictedGraduationDatePicker)) {
            targetGraduationDTO.setPrognosticatedPromotionDate(predictedGraduationDatePicker.getValue());
        }
        if (validator.hasSetValue(promotionAgreementDatePicker)) {
            targetGraduationDTO.setPromotionAgreement(promotionAgreementDatePicker.getValue());
        }

        if (promotionCanceledCheckBox.isSelected()) {
            targetGraduationDTO.setCancelDate(cancelDateDatePicker.getValue());
            targetGraduationDTO.setCancelReason(cancelReasonTextField.getText());
        } else {
            targetGraduationDTO.setCancelReason(null);
        }

        // hfu membership
        if (hfuMemberCheckBox.isSelected()) {
            targetGraduationDTO.setMemberOfHFUKolleg(true);
            targetGraduationDTO.setMembershipHFUKollegBegin(memberSinceDatePicker.getValue());
            targetGraduationDTO.setMembershipHFUKollegEnd(memberUntilDatePicker.getValue());

            if (prolongMembershipCheckBox.isSelected()) {
                targetGraduationDTO.setMembershipExtended(true);
                targetGraduationDTO.setExtendedMembershipEnd(prolongTillDatePicker.getValue());
            } else {
                targetGraduationDTO.setMembershipExtended(false);
                targetGraduationDTO.setExtendedMembershipEnd(null);
            }

        } else {
            // check box is disabled, so we make sure all subfields are equal to null
            targetGraduationDTO.setMemberOfHFUKolleg(false); // checkbox
            targetGraduationDTO.setMembershipHFUKollegBegin(null);
            targetGraduationDTO.setMembershipHFUKollegEnd(null);
            targetGraduationDTO.setMembershipExtended(false); // checkbox
            targetGraduationDTO.setExtendedMembershipEnd(null);
        }

        // external membership
        targetGraduationDTO.setMemberOfExternalKolleg(externalMemberCheckBox.isSelected());
        targetGraduationDTO.setExternalProgram(externalMemberCheckBox.isSelected() ? externalCollegeNameTextField.getText() : null);

        // cancel reason and cancel date
        targetGraduationDTO.setPromotionCanceled(promotionCanceledCheckBox.isSelected());
        if (promotionCanceledCheckBox.isSelected()) {
            targetGraduationDTO.setCancelReason(cancelReasonTextField.getText());
            targetGraduationDTO.setCancelDate(cancelDateDatePicker.getValue());
        } else {
            targetGraduationDTO.setCancelReason(null);
            targetGraduationDTO.setCancelDate(null);
        }


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


    private void initDocumentsListView() {
        documentsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // todo: load documents if editing an entry
    }

    @FXML
    private void handleOnActionBrowseDocuments(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("ui.label.selection_of_documents"));
        Collection<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles == null) {
            // return if user did not select any files
            return;
        }

        // convert files to DocumentInformationDTO
        for (File f : selectedFiles) {
            DocumentInformationDTO docInfo = new DocumentInformationDTO();
            docInfo.setFilename(f.getName());
            documentsListView.getItems().add(docInfo);
            addedDocuments.put(docInfo, f);
        }

    }

    @FXML
    private void handleOnActionDeleteDocument(ActionEvent actionEvent) {
        if (documentsListView.getSelectionModel().getSelectedItems().size() < 1) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.deleteDocument_selected")));
            return;
        }

        //confirm dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("ui.alert.title.deleteDocuments"));
        alert.setHeaderText(bundle.getString("ui.alert.header.deleteDocuments"));

        String documentNames = "";
        for (DocumentInformationDTO file : documentsListView.getSelectionModel().getSelectedItems()) {
            documentNames += file.getFilename() + "\n";
        }
        alert.setContentText(documentNames);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (DocumentInformationDTO dto : documentsListView.getSelectionModel().getSelectedItems()) {
                // if the user wants to remove a not yet uploaded document
                // we need to remove it from the addedDocuments map
                addedDocuments.remove(dto);

                // for documents which have already been uploaded (we know that if id is set)
                // we need to make sure to that we save which documents shall be removed on save
                if (dto.getId() != null) {
                    deletedDocuments.add(dto);
                }
            }

            // in any way we want to remove it from the list view
            documentsListView.getItems().remove(documentsListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void handleOnActionDownloadDocuments(ActionEvent actionEvent) {
        Collection<DocumentInformationDTO> selectedFiles = documentsListView.getSelectionModel().getSelectedItems();
        if (selectedFiles.size() < 1) {
            eventBus.post(new AlertNotificationEvent(AlertNotificationEvent.INFO, bundle.getString("ui.alert.download_selected")));
            return;
        }
        Collection<DocumentInformationDTO> downloadable = new HashSet<>();
        Collection<DocumentInformationDTO> notDownloadable = new HashSet<>();

        // ensure that only documents that have already been added to the doctoralStudent are downloaded
        for (DocumentInformationDTO doc : selectedFiles) {
            if (doctoralStudent.getDocuments().contains(doc)) {
                downloadable.add(doc);
            } else {
                notDownloadable.add(doc);
            }
        }

        if (notDownloadable.size() > 0) {
            //confirm dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(bundle.getString("ui.alert.documents_not_associated"));
            alert.setHeaderText(bundle.getString("ui.alert.documents_download_selection"));
            String documentNames = "";
            for (DocumentInformationDTO file : notDownloadable) {
                documentNames += file.getFilename() + "\n";
            }
            alert.setContentText(documentNames);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                // cancel
                return;
            }
        }
        eventBus.post(new RequestDocumentsEvent(downloadable));
        logger.log(Level.DEBUG, "requested the selected documents(" + downloadable.size() + ") from the server...");
    }

    private void updateFacultyCombobox() {
        WrappedEntity<FacultyDTO> facultyDTO = facultyHFUComboBox.getSelectionModel().getSelectedItem();
        facultyHFUComboBox.getItems().clear();
        facultyHFUComboBox.getItems().addAll(RepresentationWrapper.getWrappedFaculties(EntityPool.getInstance().getFaculties()));
        if (facultyDTO != null) {
            facultyHFUComboBox.getSelectionModel().select(facultyDTO);
        }
    }

    @Subscribe
    public void handleAddedFacultyEvent(SuccessfullyAddedFacultyEvent event) {
        updateFacultyCombobox();
    }

    @Subscribe
    public void handleDeletedFacultyEvent(SuccessfullyDeletedFacultyEvent event) {
        updateFacultyCombobox();
    }

    @Subscribe
    public void handleDeletedFacultyEvent(SuccessfullyUpdatedFacultyEvent event) {
        updateFacultyCombobox();
    }
}
