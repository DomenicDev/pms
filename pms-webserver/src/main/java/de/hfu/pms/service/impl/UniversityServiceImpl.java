package de.hfu.pms.service.impl;

import de.hfu.pms.dao.UniversityDao;
import de.hfu.pms.exceptions.UniversityNotFoundException;
import de.hfu.pms.model.University;
import de.hfu.pms.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public void deleteUniversity(Long id) {
        universityDao.deleteById(id);
    }

    @Override
    public University getUniversity(Long id) {
        return universityDao.findById(id).orElseThrow(() -> new UniversityNotFoundException(id));
    }

    @Override
    public University updateUniversity(Long id, University newUniversity) {
        University oldUniversity = universityDao.findById(id).orElseThrow( () -> new UniversityNotFoundException(id));
        newUniversity.setId(oldUniversity.getId());
        universityDao.save(newUniversity);

        return newUniversity;
    }

    @Override
    public List<University> getUniversityList() {
        return universityDao.findAll();
    }
}
