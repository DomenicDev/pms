package de.hfu.pms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.hfu.pms.client.RestClient;
import de.hfu.pms.config.AppConfig;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.exceptions.LoginFailedException;
import de.hfu.pms.pool.EntityPool;
import de.hfu.pms.shared.dto.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ApplicationServiceImpl implements ApplicationServices {

    private Logger logger = Logger.getLogger(ApplicationServiceImpl.class);
    private ObjectMapper mapper = new ObjectMapper();
    private RestClient restClient;

    private final String HOST_URL = AppConfig.get("host");
    private final String STUDENT_PREFIX = "/student/";
    private final String USER_PREFIX = "/user/";
    private final String UNIVERSITY_PREFIX = "/university/";
    private final String FACULTY_PREFIX = "/faculty/";

    private final String STUDENT_SERVICES = HOST_URL + STUDENT_PREFIX;
    private final String USER_SERVICES = HOST_URL + USER_PREFIX;
    private final String UNIVERSITY_SERVICES = HOST_URL + UNIVERSITY_PREFIX;

    public ApplicationServiceImpl() {
        this.restClient = new RestClient();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public PreviewDoctoralStudentDTO addDoctoralStudent(CreateDoctoralStudentDTO student) throws BusinessException {
        try {
            String json = mapper.writeValueAsString(student);
            String response = restClient.postJson(HOST_URL + STUDENT_PREFIX + "create", json);
            PreviewDoctoralStudentDTO dto = mapper.readValue(response, PreviewDoctoralStudentDTO.class);
            logger.log(Level.INFO, "Doctoral Student created: " + dto);
            return dto;
        } catch (IOException e) {
            throw new BusinessException("could not create student " + e);
        }
    }

    @Override
    public void patchDoctoralStudent(PatchDoctoralStudentDTO patchDoctoralStudentDTO) throws BusinessException {
        Long id = patchDoctoralStudentDTO.getId();
        System.out.println(id);
        try {
            String json = toJSON(patchDoctoralStudentDTO);
            restClient.patchJson(STUDENT_SERVICES + "update/" + id, json);
        } catch (IOException e) {
            throw new BusinessException("Doctoral student could not be updated.");
        }
    }

    @Override
    public void deleteDoctoralStudent(Long studentID) throws BusinessException {
        try {
            restClient.delete(STUDENT_SERVICES + "delete/" + studentID);
        } catch (IOException e) {
            throw new BusinessException(e.getLocalizedMessage());
        }
    }

    @Override
    public AnonymizeResultDTO anonymize(Long studentID) throws BusinessException {
        try {
            String response = restClient.get(HOST_URL + STUDENT_PREFIX + "anonymize/" + studentID);
            return toObject(response, AnonymizeResultDTO.class);
        } catch (IOException e) {
            throw new BusinessException("could not anonymize"+e);
        }
    }

    @Override
    public PreviewDoctoralStudentDTO getPreview(Long id) throws BusinessException {
        try {
            return toObject(restClient.get(STUDENT_SERVICES + "preview/" + id), PreviewDoctoralStudentDTO.class);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Collection<PreviewDoctoralStudentDTO> getPreviews() {
        try {
            String response = restClient.get(HOST_URL + STUDENT_PREFIX + "previews");
            return mapper.readValue(response, new TypeReference<List<PreviewDoctoralStudentDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DoctoralStudentDTO getDoctoralStudent(Long studentID) throws IOException {
        try {
            String json = restClient.get(STUDENT_SERVICES + "/get/" + studentID);
            return mapper.readValue(json, DoctoralStudentDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override
    public Collection<PreviewDoctoralStudentDTO> searchDoctoralStudents(String keyword) throws BusinessException {
        keyword = keyword.replaceAll("\\s", ".");
        try {
            String response = restClient.get(STUDENT_SERVICES + "search/" + keyword);
            return mapper.readValue(response, new TypeReference<List<PreviewDoctoralStudentDTO>>() {});
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Collection<PreviewDoctoralStudentDTO> getAlertedDoctoralStudents() throws IOException {
        try {
            String response = restClient.get(STUDENT_SERVICES + "get/exceeded");
            return mapper.readValue(response, new TypeReference<List<PreviewDoctoralStudentDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    @Override
    public void login(String username, String password) throws LoginFailedException {
        logger.log(Level.DEBUG, "Try to Login with Username and Password");
        // first update the login credentials with the specified ones
        restClient.setLoginCredentials(username, password);


        try {
            // to verify that the credentials is valid we call an secured site
            // if the credentials are correct we will return a response with status code 200 (--> OK)
            restClient.get(HOST_URL + "/");

            logger.log(Level.INFO, "Client successfully logged in.");
        } catch (IOException e) {
            throw new LoginFailedException(e);
        }

    }

    @Override
    public UserInfoDTO getCurrentUser() throws BusinessException {
        try {
            String json = restClient.get(USER_SERVICES + "user");
            return toObject(json, UserInfoDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("could not receive current user");
        }
    }


    @Override
    public List<UniversityDTO> getAllUniversities() {
        try {
            String response = restClient.get(HOST_URL + UNIVERSITY_PREFIX + "getList");
            return mapper.readValue(response, new TypeReference<List<UniversityDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UniversityDTO addUniversity(UniversityDTO universityDTO) throws BusinessException {
        try {
            String json = mapper.writeValueAsString(universityDTO);
            String response = restClient.postJson(HOST_URL + UNIVERSITY_PREFIX + "create", json);
            UniversityDTO dto = mapper.readValue(response, UniversityDTO.class);
            logger.log(Level.INFO, "University created: " + dto);
            return dto;
        } catch (IOException e) {
            throw new BusinessException("University could not be added");
        }
    }

    @Override
    public UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO) throws BusinessException {
        try {
            String json = mapper.writeValueAsString(universityDTO);
            String response = restClient.postJson(HOST_URL + UNIVERSITY_PREFIX + "update/" + id, json);
            UniversityDTO dto = mapper.readValue(response, UniversityDTO.class);
            logger.log(Level.INFO, "University updated: " + dto);
            return dto;
        } catch (IOException e) {
            throw new BusinessException("University could not be updated" + e);
        }
    }

    @Override
    public void deleteUniversity(Long id) throws BusinessException {
        try {
            restClient.delete(UNIVERSITY_SERVICES + "delete/" + id);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<FacultyDTO> getAllFaculties() {
        try {
            String response = restClient.get(HOST_URL + FACULTY_PREFIX + "getList");
            return mapper.readValue(response, new TypeReference<List<FacultyDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FacultyDTO addFaculty(FacultyDTO facultyDTO) throws BusinessException {
        try {
            String json = mapper.writeValueAsString(facultyDTO);
            String response = restClient.postJson(HOST_URL + FACULTY_PREFIX + "create", json);
            FacultyDTO dto = mapper.readValue(response, FacultyDTO.class);
            logger.log(Level.INFO, "Faculty created: " + dto);
            return dto;
        } catch (IOException e) {
            throw new BusinessException("Faculty could not be created " + e);
        }
    }

    @Override
    public FacultyDTO updateFaculty(FacultyDTO facultyDTO) throws BusinessException {
        try {
            String json = mapper.writeValueAsString(facultyDTO);
            String response = restClient.postJson(HOST_URL + FACULTY_PREFIX + "update/" + facultyDTO.getId(), json);
            FacultyDTO dto = mapper.readValue(response, FacultyDTO.class);
            logger.log(Level.INFO, "Faculty updated: " + dto);
            return dto;
        } catch (IOException e) {
            throw new BusinessException("Faculty could not be updated: " + e);
        }
    }

    @Override
    public void deleteFaculty(Long facultyId) throws BusinessException{
        try {
            restClient.delete(HOST_URL + FACULTY_PREFIX + "delete/" + facultyId);
            logger.log(Level.INFO, "Faculty deleted with id: " + facultyId);
        } catch (IOException e) {
            throw new BusinessException("could not delete faculty");
        }
    }

    @Override
    public void logout() {

    }

    @Override
    public void changePassword(String username, String newPassword) throws BusinessException {
        try {
            String response = restClient.postJson(HOST_URL + USER_PREFIX + "updatePassword/" + username, newPassword);
            logger.log(Level.INFO, response);
        } catch (IOException e) {
            throw new BusinessException("could not change password...");
        }
    }

    @Override
    public UserInfoDTO addUser(UserDTO userDTO) throws BusinessException {
        try {
            String json = mapper.writeValueAsString(userDTO);
            String response = restClient.postJson(HOST_URL + USER_PREFIX + "create", json);
            UserInfoDTO dto = mapper.readValue(response, UserInfoDTO.class);
            logger.log(Level.INFO, "User created: " + dto);
            return dto;
        } catch (IOException e) {
            throw new BusinessException("could not add new User...");
        }
    }

    @Override
    public UserInfoDTO updateUser(UpdateUserDTO updateUserDTO) throws BusinessException {
        try {
            String response = restClient.patchJson(USER_SERVICES + "update/" + updateUserDTO.getUsername(), toJSON(updateUserDTO));
            return toObject(response, UserInfoDTO.class);
        } catch (IOException e) {
            throw new BusinessException("User could not be updated");
        }
    }

    @Override
    public void deleteUser(String username) throws BusinessException {
        try {
            String response = restClient.delete(HOST_URL + USER_PREFIX + "delete/" + username);
            logger.log(Level.INFO, response);
        } catch (IOException e) {
            throw new BusinessException("could not remove User...");
        }
    }

    @Override
    public UserInfoDTO changeAccountInformation(String username, String forename, String surname, String email) throws BusinessException {
        ChangeUserInformationDTO changeDTO = new ChangeUserInformationDTO();
        changeDTO.setForename(forename);
        changeDTO.setLastName(surname);
        changeDTO.setEmail(email);

        try {
            String response = restClient.patchJson(USER_SERVICES + "patch/" + username, mapper.writeValueAsString(changeDTO));
            return mapper.readValue(response, UserInfoDTO.class);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public UserInfoDTO getUser(String username) throws BusinessException {
        try {
            String response = restClient.get(HOST_URL + USER_PREFIX + "get/" + username);
            return mapper.readValue(response, UserInfoDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new BusinessException("could not get User");
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        try {
            String response = restClient.get(HOST_URL + USER_PREFIX + "getList");
            return mapper.readValue(response, new TypeReference<List<UserInfoDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DocumentDTO getDocument(DocumentInformationDTO documentInformation) throws BusinessException {
        try {
            String json = restClient.get(STUDENT_SERVICES + "/docs/" + documentInformation.getId());
            return mapper.readValue(json, DocumentDTO.class);
        } catch (IOException e) {
            throw new BusinessException("could not receive document: " + e);
        }
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
