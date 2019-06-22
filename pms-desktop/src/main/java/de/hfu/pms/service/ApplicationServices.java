package de.hfu.pms.service;

import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.enums.UserRole;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ApplicationServices {

    // todo: add Documents to DoctoralStudentDTO-class or separate them?

    DoctoralStudentDTO addDoctoralStudent(CreateDoctoralStudentDTO student) throws BusinessException;

    DoctoralStudentDTO editDoctoralStudent(DoctoralStudentDTO student) throws BusinessException;

    void patchDoctoralStudent(PatchDoctoralStudentDTO patchDoctoralStudentDTO) throws BusinessException;

    void deleteDoctoralStudent(Long studentID) throws BusinessException;

    AnonymizeResultDTO anonymize(Long studentID) throws BusinessException;

    PreviewDoctoralStudentDTO getPreview(Long id) throws BusinessException;

    Collection<PreviewDoctoralStudentDTO> getPreviews();

    DoctoralStudentDTO getDoctoralStudent(Long studentID) throws IOException;

    Collection<PreviewDoctoralStudentDTO> searchDoctoralStudents(String keyword) throws BusinessException;

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

    UserDTO ChangeAccountinformation(String forename, String lastname, String email) throws BusinessException;

    Collection<UniversityDTO> getAllUniversities();

    UniversityDTO addUniversity(UniversityDTO universityDTO);

    UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO);

    Collection<FacultyDTO> getAllFaculties();

    FacultyDTO addFaculty(FacultyDTO facultyDTO);

    FacultyDTO deleteFaculty(FacultyDTO facultyDTO);

    FacultyDTO updateFaculty(FacultyDTO facultyDTO);

    void logout();

    UserInfoDTO changePassword(String username, String newPassword) throws BusinessException;

    UserInfoDTO addUser(UserDTO userDTO) throws BusinessException;

    /**
     * Can only be used by admin
     *
     * @param updateUserDTO
     * @return
     * @throws BusinessException
     */
    UserInfoDTO updateUser(UpdateUserDTO updateUserDTO) throws BusinessException;

    void deleteUser(String username) throws BusinessException;

    UserInfoDTO changeUserPrivileges(String username, UserRole newUserRole) throws BusinessException;

    UserInfoDTO changeAccountInformation(String username, String forename, String surname, String email) throws BusinessException;

    UserInfoDTO changeUserEmail(String username, String email) throws BusinessException;

    UserDTO getUser(String username) throws BusinessException;

    List<UserInfoDTO> getAllUsers();

    DocumentDTO getDocument(DocumentInformationDTO documentInformation);

    void initEntityPool();


    // Todo add all other needed methods

}
