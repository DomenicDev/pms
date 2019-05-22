package de.hfu.pms.service;

import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import javafx.collections.transformation.SortedList;

import java.io.IOException;
import java.util.Collection;

public interface ApplicationServices {

    // todo: add Documents to DoctoralStudentDTO-class or separate them?

    DoctoralStudentDTO addDoctoralStudent(DoctoralStudentDTO student);

    void editDoctoralStudent(DoctoralStudentDTO student);

    void deleteDoctoralStudent(int studentID);

    void pseudonymisate(int studentID);

    Collection<PreviewDoctoralStudentDTO> getPreviews();

    DoctoralStudentDTO getDoctoralStudent(Long studentID) throws IOException;

    SortedList<DoctoralStudentDTO> searchDoctoralStudents(String keyword);

    /**
     * Login with the specified credentials.
     * @param username the username to login with
     * @param pwHash the password to login with
     * @throws LoginFailedException if login authentication failed
     */
    void login(String username, String pwHash) throws LoginFailedException;

    Collection<UniversityDTO> getAllUniversities();

    void addUniversity(UniversityDTO universityDTO);

    void updateUniversity(Long id, UniversityDTO universityDTO);

    void logout();

    void changePassword(String Username, String newPwHash, String previousPwHash);

    void addUser(UserDTO userDTO);

    void removeUser(String username);

    void changeUserPrivileges(String username, UserRole newUserRole);

    UserDTO getUser(String username);

    SortedList<UserDTO> getAllUsers();

    void initEntityPool();


    // Todo add all other needed methods

}
