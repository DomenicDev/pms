package de.hfu.pms.pool;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.exceptions.BusinessException;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.shared.dto.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class EntityPool {

    private static EntityPool entityPool = new EntityPool();

    private EventBus eventBus = EventBusSystem.getEventBus();
    private static Logger logger = Logger.getLogger(EntityPool.class);
    private Map<Long, UniversityDTO> universities = new HashMap<>();
    private Collection<FacultyDTO> faculties = new HashSet<>();
    private Collection<PreviewDoctoralStudentDTO> previewStudents = new HashSet<>();
    private Collection<UserInfoDTO> users = new HashSet<>();
    private Collection<DoctoralStudentDTO> doctoralStudents = new HashSet<>();
    private ApplicationServices applicationServices;

    private UserInfoDTO loggedInUser = null;

    private EntityPool() {
        // private constructor
    }

    public static EntityPool getInstance() {
        return entityPool;
    }

    public void setApplicationServices(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    public ApplicationServices getApplicationServices() {
        return applicationServices;
    }

    public void addAll(Collection<UniversityDTO> data) {
        if (data == null) {
            return;
        }
        logger.log(Level.DEBUG, "Adding " + data.size() + " items to university pool.");
        for (UniversityDTO uni : data) {
            universities.put(uni.getId(), uni);
        }
    }

    public void initPreviews(Collection<PreviewDoctoralStudentDTO> previews) {
        if (previews == null) {
            return;
        }
        this.previewStudents.addAll(previews);
    }

    public void initUsers(Collection<UserInfoDTO> initUsers) {
        if (initUsers == null) {
            return;
        }
        logger.log(Level.DEBUG, "Adding " + initUsers.size() + " items to users pool.");
        users.addAll(initUsers);
    }

    public void initFaculties(Collection<FacultyDTO> initFaculties) {
        if (initFaculties == null) {
            return;
        }
        logger.log(Level.DEBUG, "Adding " + initFaculties.size() + " items to faculty pool.");
        faculties.addAll(initFaculties);
    }


    public void addFaculty(FacultyDTO faculty) {
        if (faculty == null) {
            return;
        }
        logger.log(Level.DEBUG, "Adding new faculty (" + faculty.getFacultyName() + " to faculty pool.");
        faculties.add(faculty);
    }

    public void deleteFaculty(FacultyDTO faculty) {
        if (faculty == null) {
            return;
        }
        logger.log(Level.DEBUG, "Removing faculty (" + faculty.getFacultyName() + " from faculty pool.");
        faculties.remove(faculty);
    }

    public void addUniversity(UniversityDTO universityDTO) {
        if (universityDTO == null) {
            return;
        }
        universities.put(universityDTO.getId(), universityDTO);
    }

    public void updateUniversity(UniversityDTO universityDTO) {
        if (universityDTO == null) {
            return;
        }
        universities.put(universityDTO.getId(), universityDTO);
    }

    public Collection<UniversityDTO> getUniversities() {
        return universities.values();
    }

    public Collection<FacultyDTO> getFaculties() {
        return faculties;
    }

    public Collection<PreviewDoctoralStudentDTO> getPreviewStudents() {
        return previewStudents;
    }

    public Collection<UserInfoDTO> getUsers() {
        return users;
    }

    public Collection<DoctoralStudentDTO> getDoctoralStudents() {return doctoralStudents;}

    public UserInfoDTO getLoggedInUser() throws BusinessException {
        return applicationServices.getCurrentUser();
    }

}
