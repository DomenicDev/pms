package de.hfu.pms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.eventbus.EventBus;
import de.hfu.pms.client.RestClient;
import de.hfu.pms.config.AppConfig;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.*;
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
    private final String USER_PREFIX = "/user/";
    private final String UNIVERSITY_PREFIX = "/university/";
    private final String FACULTY_PREFIX ="/faculty/";

    private final String STUDENT_SERVICES = HOST_URL + STUDENT_PREFIX;

    public ApplicationServiceImpl() {
        this.restClient = new RestClient();
        this.mapper.registerModule(new JavaTimeModule());
    }

    //TODO: Add good exception handling and logging
    @Override
    public DoctoralStudentDTO addDoctoralStudent(DoctoralStudentDTO student) {
        try {
            String json = mapper.writeValueAsString(student);
            String response = restClient.postJson(HOST_URL + STUDENT_PREFIX + "create" , json);
            DoctoralStudentDTO dto = mapper.readValue(response, DoctoralStudentDTO.class);
            logger.log(Level.INFO, "Doctoral Student created: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // todo: better throw exception
    }

    @Override
    public DoctoralStudentDTO editDoctoralStudent(DoctoralStudentDTO student) {
        try {
            String json = mapper.writeValueAsString(student);
            String response = restClient.postJson(STUDENT_SERVICES + "update/" + student.getId(), json);
            DoctoralStudentDTO updatedEntity =  mapper.readValue(response, DoctoralStudentDTO.class);
            logger.log(Level.DEBUG, "Doctoral student has been updated successfully: " + updatedEntity);
            return updatedEntity;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void patchDoctoralStudent(PatchDoctoralStudentDTO patchDoctoralStudentDTO) throws BusinessException {
        Long id = patchDoctoralStudentDTO.getId();
        System.out.println(id);
        try {
            String json = toJSON(patchDoctoralStudentDTO);
            restClient.patchJson(STUDENT_SERVICES + "update/" + id, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDoctoralStudent(int studentID) {

    }

    @Override
    public void pseudonymisate(int studentID) {

    }

    @Override
    public Collection<PreviewDoctoralStudentDTO> getPreviews() {
        try {
            String response = restClient.get(HOST_URL + STUDENT_PREFIX + "previews");
            return mapper.readValue(response, new TypeReference<List<PreviewDoctoralStudentDTO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DoctoralStudentDTO getDoctoralStudent(Long studentID) throws IOException {
        try {
            String json = restClient.get(STUDENT_SERVICES + "/get/" + studentID);
            DoctoralStudentDTO dto = mapper.readValue(json, DoctoralStudentDTO.class);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override
    public SortedList<DoctoralStudentDTO> searchDoctoralStudents(String keyword) {
        return null;
    }

    @Override
    public void login(String username, String password) throws LoginFailedException {
        logger.log(Level.DEBUG, "Try to Login with Username and Password");
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
    public UserDTO getCurrentUser() {
        // dummy object
        // todo return real one
        return new UserDTO("admin", "1234","Bob","Baumeister","test@example.com", UserRole.ADMIN);
    }

    @Override
    public UserDTO ChangeAccountinformation(String forename, String lastname, String email) {

        return null;
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
    public UniversityDTO addUniversity(UniversityDTO universityDTO) {
        try {
            String json = mapper.writeValueAsString(universityDTO);
            String response = restClient.postJson(HOST_URL + UNIVERSITY_PREFIX + "create", json);
            UniversityDTO dto = mapper.readValue(response, UniversityDTO.class);
            logger.log(Level.INFO, "University created: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO) {
        try {
            String json = mapper.writeValueAsString(universityDTO);
            String response = restClient.postJson(HOST_URL + UNIVERSITY_PREFIX + "update/" + id,json);
            UniversityDTO dto = mapper.readValue(response, UniversityDTO.class);
            logger.log(Level.INFO, "University updated: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo deleteUniversity

    @Override
    public List<FacultyDTO> getAllFaculties(){
        try {
            String response = restClient.get(HOST_URL + FACULTY_PREFIX + "getList");
            return mapper.readValue(response, new TypeReference<List<FacultyDTO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FacultyDTO addFaculty(FacultyDTO facultyDTO) {
        try {
            String json = mapper.writeValueAsString(facultyDTO);
            String response = restClient.postJson(HOST_URL + FACULTY_PREFIX + "create", json);
            FacultyDTO dto = mapper.readValue(response, FacultyDTO.class);
            logger.log(Level.INFO, "Faculty created: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public FacultyDTO updateFaculty(FacultyDTO facultyDTO) {
        try {
            String json = mapper.writeValueAsString(facultyDTO);
            String response = restClient.postJson(HOST_URL + FACULTY_PREFIX + "update/" + facultyDTO.getId(),json);
            FacultyDTO dto = mapper.readValue(response, FacultyDTO.class);
            logger.log(Level.INFO, "Faculty updated: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FacultyDTO deleteFaculty(FacultyDTO facultyDTO) {
        // todo: not working
        try {
            String json = mapper.writeValueAsString(facultyDTO);
            String response = restClient.postJson(HOST_URL + FACULTY_PREFIX + "delete", json);
            FacultyDTO dto = mapper.readValue(response, FacultyDTO.class);
            logger.log(Level.INFO, "Faculty deleted: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public UserDTO changePassword(UserDTO userDTO, String newPassword) throws BusinessException {
        try {
            String json = newPassword;
            String response = restClient.postJson(HOST_URL + USER_PREFIX + "updatePassword/" + userDTO.getUsername(), json);
            UserDTO dto = mapper.readValue(response, UserDTO.class);
            logger.log(Level.INFO, response);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new BusinessException("could not change password...");
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        try {
            String json = mapper.writeValueAsString(userDTO);
            String response = restClient.postJson(HOST_URL + USER_PREFIX + "create", json);
            UserDTO dto = mapper.readValue(response, UserDTO.class);
            logger.log(Level.INFO, "User created: " + dto);
            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeUser(String username) {
        try {
            String response = restClient.get(HOST_URL + USER_PREFIX +"delete/" + username);
            logger.log(Level.INFO, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDTO changeUserPrivileges(String username, UserRole newUserRole) {
        try {
            String response = restClient.get(HOST_URL + USER_PREFIX +"updateRole/" + username);
            logger.log(Level.INFO, response);
            return mapper.readValue(response, UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDTO changeUserEmail(String username, String email) {
        try {
            String response = restClient.postJson(HOST_URL + USER_PREFIX +"updateEmail/" + username, email);
            UserDTO dto = mapper.readValue(response, UserDTO.class);
            logger.log(Level.INFO, response);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDTO getUser(String username) {
        try {
            String response = restClient.get(HOST_URL + USER_PREFIX + "get/" + username);
            return mapper.readValue(response,UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            String response = restClient.get(HOST_URL + USER_PREFIX + "getList");
            return mapper.readValue(response, new TypeReference<List<UserDTO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initEntityPool() {
        Collection<UniversityDTO> universities = getAllUniversities();
        Collection<FacultyDTO> faculties = getAllFaculties();
        EntityPool.getInstance().addAll(universities);
        EntityPool.getInstance().initFaculties(faculties);

        EntityPool.getInstance().initPreviews(getPreviews());
        EntityPool.getInstance().initUsers(getAllUsers());
    }


    private <T> String toJSON(T object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    private <T> T toObject(String json, Class<T> c) throws IOException {
        return mapper.readValue(json, c);
    }
}
