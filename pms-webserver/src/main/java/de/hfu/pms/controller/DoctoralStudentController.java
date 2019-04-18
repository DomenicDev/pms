package de.hfu.pms.controller;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.model.DoctoralStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctoralStudentController {

    private final DoctoralStudentDao doctoralStudentDao;

    @Autowired
    public DoctoralStudentController(DoctoralStudentDao doctoralStudentDao) {
        this.doctoralStudentDao = doctoralStudentDao;
    }

    private int c = 1;

    @RequestMapping("/doc")
    public DoctoralStudent docStudent() {
        DoctoralStudent doc = new DoctoralStudent();
        doc.getPersonalData().setForename("Domenic"+(c++));
        doctoralStudentDao.save(doc);
        return doc;
    }



}
