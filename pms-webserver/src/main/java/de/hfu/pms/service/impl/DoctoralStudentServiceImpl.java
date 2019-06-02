package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.*;
import de.hfu.pms.service.DoctoralStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctoralStudentServiceImpl implements DoctoralStudentService {

    private final DoctoralStudentDao doctoralStudentDao;

    @Autowired
    public DoctoralStudentServiceImpl(DoctoralStudentDao doctoralStudentDao) {
        this.doctoralStudentDao = doctoralStudentDao;
    }

    @Override
    public DoctoralStudent create(DoctoralStudent doctoralStudent) {
        doctoralStudentDao.save(doctoralStudent);
        return doctoralStudent;
    }

    @Override
    public DoctoralStudent update(Long id, DoctoralStudent doctoralStudent) {
        doctoralStudent.setId(id);
        return doctoralStudentDao.save(doctoralStudent);
    }

    @Override
    public DoctoralStudent updatePhoto(Long id, byte[] data) {
        DoctoralStudent entity = findById(id);
        entity.setPhoto(data);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent update(Long id, PersonalData personalData) {
        DoctoralStudent entity = findById(id);
        entity.setPersonalData(personalData);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent update(Long id, QualifiedGraduation qualifiedGraduation) {
        DoctoralStudent entity = findById(id);
        entity.setQualifiedGraduation(qualifiedGraduation);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent update(Long id, TargetGraduation targetGraduation) {
        DoctoralStudent entity = findById(id);
        entity.setTargetGraduation(targetGraduation);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent update(Long id, Employment employment) {
        DoctoralStudent entity = findById(id);
        entity.setEmployment(employment);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent update(Long id, Support support) {
        DoctoralStudent entity = findById(id);
        entity.setSupport(support);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent update(Long id, AlumniState alumniState) {
        DoctoralStudent entity = findById(id);
        entity.setAlumniState(alumniState);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent findById(Long id) {
       return doctoralStudentDao.findById(id).orElseThrow(() -> new DoctoralStudentNotFoundException(id));
    }

    @Override
    public void remove(Long id) {
        doctoralStudentDao.deleteById(id);
    }

    @Override
    public void anonymize(Long id) {
        // TODO
        throw new UnsupportedOperationException("method must still be implemented");
    }

    @Override
    public List<DoctoralStudent> getAll() {
        return doctoralStudentDao.findAll();
    }

    @Override
    public List<DoctoralStudent> search() {
        // TODO
        throw new UnsupportedOperationException("method must still be implemented");
    }
}
