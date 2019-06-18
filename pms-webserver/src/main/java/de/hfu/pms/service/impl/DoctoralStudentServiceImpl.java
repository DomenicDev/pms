package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.*;
import de.hfu.pms.service.DoctoralStudentService;
import de.hfu.pms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctoralStudentServiceImpl implements DoctoralStudentService {

    private final DoctoralStudentDao doctoralStudentDao;
    private final DocumentService documentService;

    @Autowired
    public DoctoralStudentServiceImpl(DoctoralStudentDao doctoralStudentDao, DocumentService documentService) {
        this.doctoralStudentDao = doctoralStudentDao;
        this.documentService = documentService;
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
    public DoctoralStudent anonymize(Long id) {
        DoctoralStudent doctoralStudent = doctoralStudentDao.findById(id).orElseThrow(() -> new DoctoralStudentNotFoundException(id));
        DoctoralStudent anonymizedDoctoralStudent = new DoctoralStudent();
        anonymizedDoctoralStudent.setAnonymized(true);


        //Get all subtables
        PersonalData personalData = doctoralStudent.getPersonalData();
        QualifiedGraduation qualifiedGraduation = doctoralStudent.getQualifiedGraduation();
        TargetGraduation targetGraduation = doctoralStudent.getTargetGraduation();
        Employment employment = doctoralStudent.getEmployment();
        Support support = doctoralStudent.getSupport();
        AlumniState alumniState = doctoralStudent.getAlumniState();

        //Delete PersonalData
        PersonalData anonymizedPersonalData = new PersonalData();
        anonymizedPersonalData.setGender(personalData.getGender());
        LocalDate dateOfBirth = personalData.getDateOfBirth();

        //fill in asterix
        String fillCharacter = "*";
        anonymizedPersonalData.setLastName(fillCharacter);
        anonymizedPersonalData.setForename(fillCharacter);
        anonymizedPersonalData.setEmail(fillCharacter);
        anonymizedPersonalData.setTelephone(fillCharacter);
        anonymizedPersonalData.setDateOfBirth(LocalDate.of(dateOfBirth.getYear(),1,1));

        anonymizedDoctoralStudent.setPersonalData(anonymizedPersonalData);

        //QualifiedGraduation
        anonymizedDoctoralStudent.setQualifiedGraduation(qualifiedGraduation);

        //Delete name of Dissertation
        targetGraduation.setNameOfDissertation(null);
        anonymizedDoctoralStudent.setTargetGraduation(targetGraduation);

        //delete employment
        anonymizedDoctoralStudent.setEmployment(null);

        //delete awards
        support.setAwards(null);
        anonymizedDoctoralStudent.setSupport(support);

        //modify AllumniState
        alumniState.setAgreementNews(false);
        alumniState.setAgreementEvaluation(false);
        anonymizedDoctoralStudent.setAlumniState(alumniState);

        //delete Photo
        doctoralStudent.setPhoto(null);

        //delete all Documents
        documentService.deleteAll(id);

        doctoralStudentDao.deleteById(id);
        return doctoralStudentDao.save(anonymizedDoctoralStudent);
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
