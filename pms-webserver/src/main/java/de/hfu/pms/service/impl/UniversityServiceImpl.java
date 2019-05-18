package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UniversityDao;
import de.hfu.pms.exceptions.UniversityNotFoundException;
import de.hfu.pms.model.University;
import de.hfu.pms.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UniversityServiceImpl implements UniversityService {

    private final UniversityDao universityDao;

    @Autowired
    public UniversityServiceImpl(UniversityDao universityDao) {
        this.universityDao = universityDao;
    }

    @Override
    public void createUniversity(University newUniversity) {
        universityDao.save(newUniversity);
    }

    @Override
    public void deleteUniversity(Integer id) {
        universityDao.deleteById(id);
    }

    @Override
    public University getUniversity(Integer id) {
        return universityDao.findById(id).orElseThrow(() -> new UniversityNotFoundException(id));
    }

    @Override
    public List<University> getUniversityList() {
        return universityDao.findAll();
    }
}
