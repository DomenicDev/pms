package de.hfu.pms.controller;

import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.*;
import de.hfu.pms.service.DoctoralStudentService;
import de.hfu.pms.service.DocumentService;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.utils.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class DoctoralStudentController {

    private final DocumentService documentService;
    private final DoctoralStudentService doctoralStudentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctoralStudentController(DoctoralStudentService doctoralStudentService, ModelMapper modelMapper, DocumentService documentService) {
        this.doctoralStudentService = doctoralStudentService;
        this.modelMapper = modelMapper;
        this.documentService = documentService;
    }

    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctoralStudentDTO createDoctoralStudent(@RequestBody CreateDoctoralStudentDTO doctoralStudentDTO) {
        DoctoralStudent student = convertToEntity(doctoralStudentDTO);
        DoctoralStudent studentCreated = doctoralStudentService.create(student);
        return convertToDTO(studentCreated);
    }

    @PostMapping("/update/{id}")
    public DoctoralStudentDTO updateDoctoralStudent(@RequestBody DoctoralStudentDTO doctoralStudentDTO, @PathVariable Long id) {
        DoctoralStudent student = convertToEntity(doctoralStudentDTO);
        DoctoralStudent updatedStudent = doctoralStudentService.update(id, student);
        return convertToDTO(updatedStudent);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> patchDoctoralStudent(@RequestBody PatchDoctoralStudentDTO patchDTO, @PathVariable Long id) {
        // extract all attributes from patch object
        PersonalDataDTO personalDataDTO = patchDTO.getPatchedPersonalData();
        QualifiedGraduationDTO qualifiedGraduationDTO = patchDTO.getPatchedQualifiedGraduation();
        TargetGraduationDTO targetGraduationDTO = patchDTO.getPatchedTargetGraduation();
        EmploymentDTO employmentDTO = patchDTO.getPatchedEmployment();
        SupportDTO supportDTO = patchDTO.getPatchedSupport();
        AlumniStateDTO alumniStateDTO = patchDTO.getPatchedAlumniState();
        boolean photoChanged = patchDTO.isChangedPhoto();
        byte[] photo = patchDTO.getPhoto();
        Collection<DocumentDTO> documentsToAdd = patchDTO.getDocumentsToAdd();
        Collection<Long> documentsToRemove = patchDTO.getDocumentsToRemove();


        // check what has been changed
        if (personalDataDTO != null) {
            PersonalData personalData = modelMapper.map(personalDataDTO, PersonalData.class);
            doctoralStudentService.update(id, personalData);
        }
        if (qualifiedGraduationDTO != null) {
            QualifiedGraduation qualifiedGraduation = modelMapper.map(qualifiedGraduationDTO, QualifiedGraduation.class);
            doctoralStudentService.update(id, qualifiedGraduation);
        }
        if (targetGraduationDTO != null) {
            TargetGraduation targetGraduation = modelMapper.map(targetGraduationDTO, TargetGraduation.class);
            doctoralStudentService.update(id, targetGraduation);
        }
        if (employmentDTO != null) {
            Employment employment = modelMapper.map(employmentDTO, Employment.class);
            doctoralStudentService.update(id, employment);
        }
        if (supportDTO != null) {
            Support support = modelMapper.map(supportDTO, Support.class);
            doctoralStudentService.update(id, support);
        }
        if (alumniStateDTO != null) {
            AlumniState alumniState = modelMapper.map(alumniStateDTO, AlumniState.class);
            doctoralStudentService.update(id, alumniState);
        }
        if (photoChanged) {
            if (photo != null) {
                doctoralStudentService.updatePhoto(id, photo);
            } else {
                doctoralStudentService.updatePhoto(id, null);
            }
        }
        if (documentsToAdd != null && !documentsToAdd.isEmpty()) {
            for (DocumentDTO documentDTO : documentsToAdd) {
                uploadDocument(documentDTO, id);
            }
        }
        if (documentsToRemove != null && !documentsToRemove.isEmpty()) {
            for (Long docId : documentsToRemove) {
                documentService.deleteDocument(docId);
            }
        }

        return ResponseEntity.ok("resource patched");
    }


    @GetMapping("/previews")
    public Collection<PreviewDoctoralStudentDTO> getAllDoctoralStudentsAsPreview() {
        Collection<DoctoralStudent> doctoralStudents = doctoralStudentService.getAll();
        return convertToPreview(doctoralStudents);
    }

    @GetMapping("/preview/{id}")
    public PreviewDoctoralStudentDTO getPreview(@PathVariable Long id) {
        DoctoralStudent entity = doctoralStudentService.findById(id);
        DoctoralStudentDTO dto = convertToDTO(entity);
        return Converter.convert(dto);
    }

    @GetMapping("/get/{id}")
    public DoctoralStudentDTO getDoctoralStudent(@PathVariable Long id) {
        DoctoralStudent student = doctoralStudentService.findById(id);
        return convertToDTO(student);
    }

    @GetMapping("get/exceeded")
    public Collection<PreviewDoctoralStudentDTO> getAlerts() {
        Collection<DoctoralStudent> all = doctoralStudentService.getAll();
        Collection<DoctoralStudent> exceededStudents = new HashSet<>();
        LocalDate now = LocalDate.now();

        for (DoctoralStudent student : all) {
            TargetGraduation targetGraduation = student.getTargetGraduation();
            if (targetGraduation == null) continue;

            // check if student has already finished its promotion
            // also make sure that student has neither canceled its promotion nor has been anonymized
            if (targetGraduation.getPromotionCanceled() || student.getAnonymized() || targetGraduation.getProcedureCompleted() != null) continue;

            LocalDate endDate = targetGraduation.getPrognosticatedPromotionDate(); // end of promotion
            if (endDate == null) continue;

            // we want to alert 2 months before the prognosticated end date
            LocalDate alertDate = endDate.minusMonths(2);

            // if we already reached that exceeding date we know
            // that this student needs to be alerted
            if (now.isAfter(alertDate)) {
                exceededStudents.add(student);
            }

        }

        // return preview dto objects for exceeding students
        return convertToPreview(exceededStudents);
    }

    @GetMapping("/search/{keyword}")
    public Collection<PreviewDoctoralStudentDTO> searchResult(@PathVariable String keyword) {
        if (keyword == null) {
            return null;
        }

        Collection<DoctoralStudent> searchResult = doctoralStudentService.search(keyword);
        return convertToPreview(searchResult);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDoctoralStudent(@PathVariable Long id) {
        doctoralStudentService.remove(id);
    }

    @GetMapping("anonymize/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnonymizeResultDTO anonymize(@PathVariable Long id){
        DoctoralStudent anonymizedStudent = doctoralStudentService.anonymize(id);
        DoctoralStudentDTO dto = convertToDTO(anonymizedStudent);
        return new AnonymizeResultDTO(id, dto);
    }

    // ------------------ //
    //      DOCUMENTS     //
    // ------------------ //

    @PostMapping("/docs/{studentId}")
    public ResponseEntity<?> uploadDocument(@RequestBody DocumentDTO document, @PathVariable Long studentId) {
        Document entity = convertToEntity(document);
        documentService.assignDocument(studentId, entity);
        return ResponseEntity.ok("document uploaded");
    }

    @GetMapping("/docs/{id}")
    public DocumentDTO getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return convertToDTO(document);
    }

    private Collection<PreviewDoctoralStudentDTO> convertToPreview(Collection<DoctoralStudent> students) {
        Collection<PreviewDoctoralStudentDTO> previews = new HashSet<>();
        for (DoctoralStudent student : students) {
            DoctoralStudentDTO dto = convertToDTO(student);
            PreviewDoctoralStudentDTO preview = Converter.convert(dto);
            previews.add(preview);
        }
        return previews;
    }

    private DocumentDTO convertToDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setFilename(document.getFilename());
        documentDTO.setData(document.getData());
        return documentDTO;
    }

    private Document convertToEntity(DocumentDTO documentDTO) {
        Document entity = new Document();
        entity.setFilename(documentDTO.getFilename());
        entity.setData(documentDTO.getData());
        return entity;
    }


    private DoctoralStudent convertToEntity(DoctoralStudentDTO doctoralStudentDTO) {
        DoctoralStudent doctoralStudent = modelMapper.map(doctoralStudentDTO, DoctoralStudent.class);
        return doctoralStudent;
    }

    private DoctoralStudent convertToEntity(CreateDoctoralStudentDTO doctoralStudentDTO) {
        DoctoralStudent doctoralStudent = modelMapper.map(doctoralStudentDTO, DoctoralStudent.class);
        return doctoralStudent;
    }

    private DoctoralStudentDTO convertToDTO(DoctoralStudent doctoralStudent) {
        DoctoralStudentDTO doctoralStudentDTO = modelMapper.map(doctoralStudent, DoctoralStudentDTO.class);

        // create document meta information list
        doctoralStudentDTO.setDocuments(getMetaInformation(doctoralStudent.getDocuments()));

        return doctoralStudentDTO;
    }

    private Set<DocumentInformationDTO> getMetaInformation(Collection<Document> documents) {
        Set<DocumentInformationDTO> metas = new HashSet<>();
        for (Document document : documents) {
            DocumentInformationDTO info = modelMapper.map(document, DocumentInformationDTO.class);
            metas.add(info);
        }
        return metas;
    }

    @ResponseBody
    @ExceptionHandler(DoctoralStudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String doctoralStudentNotFoundHandler(DoctoralStudentNotFoundException ex) {
        return ex.getMessage();
    }

}
