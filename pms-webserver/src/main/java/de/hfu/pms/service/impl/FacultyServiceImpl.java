package de.hfu.pms.service.impl;

import de.hfu.pms.dao.FacultyDao;
import de.hfu.pms.model.Faculty;
import de.hfu.pms.service.FacultyService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyDao facultyDao;

    public FacultyServiceImpl(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyDao.save(faculty);
    }

    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
        return facultyDao.save(faculty);
    }

    @Override
    public void removeFaculty(Long id) {
        facultyDao.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyDao.findAll();
    }
}
