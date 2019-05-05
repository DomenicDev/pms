package de.hfu.pms;

import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import javafx.collections.transformation.SortedList;

public interface ApplicationServices {

    // todo: add Documents to DoctoralStudentDTO-class or separate them?

    void addDoctoralStudent(DoctoralStudentDTO student);

    void editDoctoralStudent(DoctoralStudentDTO student);

    void deleteDoctoralStudent(int studentID);

    void pseudonymisate(int studentID);

    DoctoralStudentDTO getDoctoralStudent(int studentID);

    SortedList<DoctoralStudentDTO> searchDoctoralStudents(String keyword);

    /**
     * Login with the specified credentials.
     * @param username the username to login with
     * @param pwHash the password to login with
     * @throws LoginFailedException if login authentication failed
     */
    void login(String username, String pwHash) throws LoginFailedException;

    void logout();

    void changePassword(String Username, String newPwHash, String previousPwHash);

    void addUser(String username, String pwHash);

    void removeUser(String username);

    void changeUserPrivileges(String username, UserRole newUserRole);

    UserRole getCurrentUserPrivileges();

    SortedList<UserDTO> getAllUsers();


    // Todo add all other needed methods

}
