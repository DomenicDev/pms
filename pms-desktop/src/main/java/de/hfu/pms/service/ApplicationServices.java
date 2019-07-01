package de.hfu.pms.service;

import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.shared.dto.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * This interface is a collection of all service methods this application offers.
 */
public interface ApplicationServices {

    /**
     * Creates a new a new doctoral student.
     *
     * @param student the create dto object
     * @return the new created doctoral student
     * @throws BusinessException if entity could not be created
     */
    PreviewDoctoralStudentDTO addDoctoralStudent(CreateDoctoralStudentDTO student) throws BusinessException;

    /**
     * Patches an already created student
     *
     * @param patchDoctoralStudentDTO the patch dto
     * @throws BusinessException if student could not be patched
     */
    void patchDoctoralStudent(PatchDoctoralStudentDTO patchDoctoralStudentDTO) throws BusinessException;

    /**
     * Deletes the specified doctoral student by its id
     *
     * @param studentID the id of the doctoral student to delete
     * @throws BusinessException if entity could not be deleted
     */
    void deleteDoctoralStudent(Long studentID) throws BusinessException;

    /**
     * This will anonymize the specified doctoral student and will return
     * a complete new entity containing only non-personal attribute.
     *
     * @param studentID the id of the doctoral student to anonymize
     * @return the anonymize entity
     * @throws BusinessException if entity could not be anonymized
     */
    AnonymizeResultDTO anonymize(Long studentID) throws BusinessException;

    /**
     * Receive a preview with pre-defined attributes for the specified
     * doctoral student.
     *
     * @param id the doctoral student to be received as preview
     * @return the preview object for the specified id
     * @throws BusinessException if preview could not be created
     */
    PreviewDoctoralStudentDTO getPreview(Long id) throws BusinessException;

    /**
     * Return a list of all previews
     *
     * @return collection of all previews
     */
    Collection<PreviewDoctoralStudentDTO> getPreviews();

    /**
     * Returns the full doctoral student object for the specified id
     *
     * @param studentID the id of the doctoral student
     * @return the doctoral student object with all its data
     * @throws IOException if doctoral student could not be received
     */
    DoctoralStudentDTO getDoctoralStudent(Long studentID) throws IOException;

    /**
     * This method makes a full-text search using the specified string.
     * For this search all doctoral students attributes will be searched
     * for a match with the specified search text
     *
     * @param keyword the text to search for
     * @return a list containing all matched entities
     * @throws BusinessException if search could not be executed
     */
    Collection<PreviewDoctoralStudentDTO> searchDoctoralStudents(String keyword) throws BusinessException;

    /**
     * Returns a list of doctoral students that might exceed their promotion time.
     *
     * @return Returns a list of doctoral students that might exceed their promotion time.
     * @throws IOException if request could not be completed
     */
    Collection<PreviewDoctoralStudentDTO> getAlertedDoctoralStudents() throws IOException;

    /**
     * Login with the specified credentials.
     *
     * @param username the username to login with
     * @param pwHash   the password to login with
     * @throws LoginFailedException if login authentication failed
     */
    void login(String username, String pwHash) throws LoginFailedException;

    /**
     * @return the current logged in user
     */
    UserInfoDTO getCurrentUser() throws BusinessException;

    /**
     * @return a list containing all universities.
     */
    Collection<UniversityDTO> getAllUniversities();

    /**
     * Adds the specified university to the system.
     *
     * @param universityDTO the new university
     * @return the newly created university object containing the id
     */
    UniversityDTO addUniversity(UniversityDTO universityDTO) throws BusinessException;

    /**
     * Updates the specified university by applying the parameters
     * of the specified UniversityDTO to the already existing entity.
     *
     * @param id            the id of the university to change
     * @param universityDTO the university information
     * @return the updated university
     */
    UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO) throws BusinessException;

    /**
     * @return a collection containing all faculties.
     */
    Collection<FacultyDTO> getAllFaculties();

    /**
     * Adds the specified the specified faculty
     *
     * @param facultyDTO the faculty to add
     * @return the new created faculty containing the id
     */
    FacultyDTO addFaculty(FacultyDTO facultyDTO) throws BusinessException;

    /**
     * Deletes the specified faculty by its id
     *
     * @param facultyId the id to delete
     * @throws BusinessException if faculty could not be deleted
     */
    void deleteFaculty(Long facultyId) throws BusinessException;

    /**
     * Updates the specified faculty.
     *
     * @param facultyDTO the faculty object containing the updated properties
     * @return the updated faculty
     */
    FacultyDTO updateFaculty(FacultyDTO facultyDTO) throws BusinessException;

    /**
     * Will log out the user from this application.
     */
    void logout();

    /**
     * Changes the password of the specified username to the specified password
     *
     * @param username    the username to change the password from
     * @param newPassword the new password for that user
     * @throws BusinessException if password could not be changed
     */
    void changePassword(String username, String newPassword) throws BusinessException;

    /**
     * Adds the specified user to the system.
     *
     * @param userDTO the user to add
     * @return the new created user containing the id
     * @throws BusinessException if user could not be created
     */
    UserInfoDTO addUser(UserDTO userDTO) throws BusinessException;

    /**
     * Can only be used by admin
     *
     * @param updateUserDTO the updated user object containing the new values
     * @return the updated user object
     * @throws BusinessException if user could not be updated
     */
    UserInfoDTO updateUser(UpdateUserDTO updateUserDTO) throws BusinessException;

    /**
     * Returns the user information for the requested user
     * @param username the username of the username (used as identifier)
     * @return the information object for the requested user
     * @throws BusinessException if user could not be received
     */
    UserInfoDTO getUser(String username) throws BusinessException;

    /**
     * Deletes the specified user by its username.
     *
     * @param username the name of the user to delete
     * @throws BusinessException if user could not be deleted
     */
    void deleteUser(String username) throws BusinessException;

    /**
     * Changes the account information for the specified user.
     * This method can also be called by non-admins.
     *
     * @param username the name of the user to update
     * @param forename the new forename
     * @param surname  the new surname
     * @param email    the new email
     * @return the updated user object
     * @throws BusinessException if user could not be updated
     */
    UserInfoDTO changeAccountInformation(String username, String forename, String surname, String email) throws BusinessException;

    /**
     * @return a list of all registered users
     */
    List<UserInfoDTO> getAllUsers();

    /**
     * Will download the specified document.
     *
     * @param documentInformation the meta information (extracted from a doctoral student) of a document
     * @return the document itself
     */
    DocumentDTO getDocument(DocumentInformationDTO documentInformation) throws BusinessException;

    /**
     * Used internally, do not call manually!
     */
    void initEntityPool();

}
