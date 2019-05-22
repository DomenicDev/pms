package de.hfu.pms.service;

import de.hfu.pms.model.University;

import java.util.List;

public interface UniversityService {

    void createUniversity(University newUniversity);
    void deleteUniversity(Long id);
    University getUniversity(Long id);
    University updateUniversity(Long id, University newUniversity);
    List<University> getUniversityList();
}
