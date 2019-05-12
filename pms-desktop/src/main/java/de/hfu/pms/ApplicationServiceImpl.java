package de.hfu.pms;

import de.hfu.pms.config.AppConfig;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import httpConector.RestClient;
import javafx.collections.transformation.SortedList;
import org.apache.log4j.Logger;

public class ApplicationServiceImpl implements ApplicationServices {

    private Logger logger = Logger.getLogger(ApplicationServiceImpl.class);

    private RestClient restClient;

    private String basicUrl = AppConfig.get("host");

    public ApplicationServiceImpl() {
        this.restClient = new RestClient();
    }

    @Override
    public void addDoctoralStudent(DoctoralStudentDTO student) {

    }

    @Override
    public void editDoctoralStudent(DoctoralStudentDTO student) {

    }

    @Override
    public void deleteDoctoralStudent(int studentID) {

    }

    @Override
    public void pseudonymisate(int studentID) {

    }

    @Override
    public DoctoralStudentDTO getDoctoralStudent(int studentID) {
        return null;
    }

    @Override
    public SortedList<DoctoralStudentDTO> searchDoctoralStudents(String keyword) {
        return null;
    }

    @Override
    public void login(String username, String password) throws LoginFailedException {
        // first update the login credentials with the specified ones
        restClient.setLoginCredentials(username, password);

        /*
        try {
            // to verify that the credentials is valid we call an secured site
            // if the credentials are correct we will return a response with status code 200 (--> OK)


            HttpResponse response = restClient.get(basicUrl + "/");
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                // login failed
                throw new LoginFailedException();
            }
            logger.log(Level.INFO, "Client successfully logged in.");
        } catch (IOException e) {
            throw new LoginFailedException(e);
        }
        */
    }

    @Override
    public void logout() {

    }

    @Override
    public void changePassword(String Username, String newPwHash, String previousPwHash) {

    }

    @Override
    public void addUser(String username, String pwHash) {

    }

    @Override
    public void removeUser(String username) {

    }

    @Override
    public void changeUserPrivileges(String username, UserRole newUserRole) {

    }

    @Override
    public UserRole getCurrentUserPrivileges() {
        return null;
    }

    @Override
    public SortedList<UserDTO> getAllUsers() {
        return null;
    }
}
