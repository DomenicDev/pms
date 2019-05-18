package de.hfu.pms.service;

import de.hfu.pms.model.University;

import java.util.List;

public interface UniversityService {

    void createUniversity(University newUniversity);
    void deleteUniversity(Integer id);
    University getUniversity(Integer id);
    List<University> getUniversityList();
}
