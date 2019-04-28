package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.DoctoralStudent;
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
        return doctoralStudentDao.save(doctoralStudent);
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
