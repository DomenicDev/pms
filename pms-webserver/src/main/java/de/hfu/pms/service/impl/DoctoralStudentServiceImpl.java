package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.service.DoctoralStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctoralStudentServiceImpl implements DoctoralStudentService {

    private final DoctoralStudentDao doctoralStudentDao;

    @Autowired
    public DoctoralStudentServiceImpl(DoctoralStudentDao doctoralStudentDao) {
        this.doctoralStudentDao = doctoralStudentDao;
    }

    @Override
    public DoctoralStudent createDoctoralStudent(DoctoralStudent doctoralStudent) {
        doctoralStudentDao.save(doctoralStudent);
        return doctoralStudent;
    }
}
