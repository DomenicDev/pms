package de.hfu.pms.service;

import de.hfu.pms.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);

    Faculty editFaculty(Long id, Faculty faculty);

    void removeFaculty(Long id);

    Collection<Faculty> getAll();

}
