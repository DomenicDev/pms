package de.hfu.pms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.eventbus.EventBus;
import de.hfu.pms.client.RestClient;
import de.hfu.pms.config.AppConfig;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import javafx.collections.transformation.SortedList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ApplicationServiceImpl implements ApplicationServices {

    private Logger logger = Logger.getLogger(ApplicationServiceImpl.class);

    private EventBus eventBus = EventBusSystem.getEventBus();

    private ObjectMapper mapper = new ObjectMapper();
    private RestClient restClient;

    private final String HOST_URL = AppConfig.get("host");
    private final String STUDENT_PREFIX = "/student/";
    private final String UNIVERSITY_PREFIX = "/university/";

    public ApplicationServiceImpl() {
        this.restClient = new RestClient();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void addDoctoralStudent(DoctoralStudentDTO student) {
        try {
            String json = mapper.writeValueAsString(student);
            String response = restClient.postJson(HOST_URL + STUDENT_PREFIX + "create" , json);
            DoctoralStudentDTO dto = mapper.readValue(response, DoctoralStudentDTO.class);
            logger.log(Level.INFO, "Doctoral Student created: " + dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


            HttpResponse response = restClient.get(HOST_URL + "/");
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
    public List<UniversityDTO> getAllUniversities() {
        try {
            String response = restClient.get(HOST_URL + UNIVERSITY_PREFIX + "getList");
            return mapper.readValue(response, new TypeReference<List<UniversityDTO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public void initEntityPool() {
        Collection<UniversityDTO> universities = getAllUniversities();
        EntityPool.getInstance().addAll(universities);
    }
}
