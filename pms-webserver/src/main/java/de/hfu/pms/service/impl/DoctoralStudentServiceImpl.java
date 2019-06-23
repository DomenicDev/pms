package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.dao.PhotoDao;
import de.hfu.pms.exceptions.AlreadyAnonymizedException;
import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.*;
import de.hfu.pms.service.DoctoralStudentService;
import de.hfu.pms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class DoctoralStudentServiceImpl implements DoctoralStudentService {

    private final DoctoralStudentDao doctoralStudentDao;
    private final DocumentService documentService;
    private final PhotoDao photoDao;

    @Autowired
    public DoctoralStudentServiceImpl(DoctoralStudentDao doctoralStudentDao, DocumentService documentService, PhotoDao photoDao) {
        this.doctoralStudentDao = doctoralStudentDao;
        this.documentService = documentService;
        this.photoDao = photoDao;
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
    public DoctoralStudent deletePhoto(Long id) {
        DoctoralStudent entity = findById(id);
        Long photoId = entity.getPhotoId();
        if (photoId != null) {
            photoDao.deleteById(photoId);
        }
        entity.setPhotoId(null);
        return doctoralStudentDao.save(entity);
    }

    @Override
    public DoctoralStudent updatePhoto(Long id, String filename, byte[] data) {
        DoctoralStudent entity = findById(id);
        Long photoId = entity.getPhotoId();
        Photo photo;
        if (photoId == null) {
            photo = new Photo();
        } else {
            photo = photoDao.findById(photoId).orElseThrow();
        }
        photo.setFilename(filename);
        photo.setPhoto(data);
        photo = photoDao.save(photo);
        entity.setPhotoId(photo.getId());
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
    public Collection<DoctoralStudent> search(String searchText) {
        if (searchText == null) {
            return null;
        }

        searchText = "(?i)^.*" + searchText + ".*$"; // disables case-sensitivity

        // the search implementation could be optimized a lot
        // e.g. by using a search engine
        // this can be done in a future version
        Collection<DoctoralStudent> searchResult = new LinkedList<>();

        // search each student and add matching entities to the return list
        Collection<DoctoralStudent> all = doctoralStudentDao.findAll();
        for (DoctoralStudent student : all) {
            if (fullTextSearch(student, searchText)) {
                searchResult.add(student);
            }
        }
        return searchResult;
    }

    private boolean fullTextSearch(DoctoralStudent doctoralStudent, String searchText) {
        PersonalData personalData = doctoralStudent.getPersonalData();
        if (personalData.getForename() != null && personalData.getForename().matches(searchText)) return true;
        if (personalData.getLastName() != null && personalData.getLastName().matches(searchText)) return true;
        if (personalData.getFormerLastName() != null && personalData.getFormerLastName().matches(searchText))
            return true;
        if (personalData.getTitle() != null && personalData.getTitle().matches(searchText)) return true;
        if (personalData.getDateOfBirth() != null && personalData.getDateOfBirth().toString().matches(searchText))
            return true;
        if (personalData.getFamilyStatus() != null && personalData.getFamilyStatus().toString().matches(searchText))
            return true;
        if (personalData.getEmail() != null && personalData.getEmail().matches(searchText)) return true;
        if (personalData.getTelephone() != null && personalData.getTelephone().matches(searchText)) return true;

        QualifiedGraduation qualifiedGraduation = doctoralStudent.getQualifiedGraduation();
        if (qualifiedGraduation.getGrade() != null && qualifiedGraduation.getGrade().matches(searchText)) return true;
        if (qualifiedGraduation.getSubjectArea() != null && qualifiedGraduation.getSubjectArea().matches(searchText))
            return true;
        if (qualifiedGraduation.getGraduation() != null && qualifiedGraduation.getGraduation().name().matches(searchText))
            return true;
        if (qualifiedGraduation.getGradedUniversity() != null && qualifiedGraduation.getGradedUniversity().getName().matches(searchText))
            return true;

        Address address = doctoralStudent.getPersonalData().getAddress();
        if (address.getStreet() != null && address.getStreet().matches(searchText)) return true;
        if (address.getPlz() != null && address.getPlz().matches(searchText)) return true;
        if (address.getLocation() != null && address.getLocation().matches(searchText)) return true;
        if (address.getCountry() != null && address.getCountry().matches(searchText)) return true;

        TargetGraduation promotion = doctoralStudent.getTargetGraduation();
        if (promotion.getTargetDegree() != null && promotion.getTargetDegree().matches(searchText)) return true;
        if (promotion.getNameOfDissertation() != null && promotion.getNameOfDissertation().matches(searchText))
            return true;
        if (promotion.getInternalSupervisor() != null && promotion.getInternalSupervisor().matches(searchText))
            return true;
        if (promotion.getFacultyHFU() != null && promotion.getFacultyHFU().getFacultyName().matches(searchText))
            return true;
        if (promotion.getExternalSupervisor() != null && promotion.getExternalSupervisor().matches(searchText))
            return true;
        if (promotion.getExternalUniversity() != null && promotion.getExternalUniversity().getName().matches(searchText))
            return true;
        if (promotion.getExternalFaculty() != null && promotion.getExternalFaculty().matches(searchText)) return true;
        if (promotion.getRating() != null && promotion.getRating().name().matches(searchText)) return true;

        // add some more...

        AlumniState alumniState = doctoralStudent.getAlumniState();
        if (alumniState.getEmployer() != null && alumniState.getEmployer().matches(searchText)) return true;
        if (alumniState.getJobTitle() != null && alumniState.getJobTitle().matches(searchText)) return true;

        // when we reach this point there was no match, so we can return false
        return false;
    }

    @Override
    public void remove(Long id) {
        doctoralStudentDao.deleteById(id);
    }

    @Override
    public DoctoralStudent anonymize(Long id) {
        DoctoralStudent doctoralStudent = doctoralStudentDao.findById(id).orElseThrow(() -> new DoctoralStudentNotFoundException(id));
        if (!doctoralStudent.getAnonymized()) {
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
            anonymizedPersonalData.setDateOfBirth(LocalDate.of(dateOfBirth.getYear(), 1, 1));

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
            if (doctoralStudent.getPhotoId() != null) {
                photoDao.deleteById(doctoralStudent.getId());
            }
            doctoralStudent.setPhotoId(null);

            //delete all Documents
            documentService.deleteAll(id);

            doctoralStudentDao.deleteById(id);
            return doctoralStudentDao.save(anonymizedDoctoralStudent);
        } else throw new AlreadyAnonymizedException(id);
    }

    @Override
    public List<DoctoralStudent> getAll() {
        return doctoralStudentDao.findAll();
    }

}
