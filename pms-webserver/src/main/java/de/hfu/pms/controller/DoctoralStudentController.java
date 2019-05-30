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

import java.util.*;

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
        byte[] photo = patchDTO.getPhoto();


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
        if (photo != null) {
            doctoralStudentService.updatePhoto(id, photo);
        }

        return ResponseEntity.ok("resource patched");
    }


    @GetMapping("/previews")
    public List<PreviewDoctoralStudentDTO> getAllDoctoralStudentsAsPreview() {
        List<DoctoralStudent> doctoralStudents = doctoralStudentService.getAll();
        List<PreviewDoctoralStudentDTO> previews = new ArrayList<>();
        for (DoctoralStudent student : doctoralStudents) {
            DoctoralStudentDTO dto = convertToDTO(student);
            PreviewDoctoralStudentDTO preview = Converter.convert(dto);
            previews.add(preview);
        }
        return previews;
    }

    @GetMapping("/get/{id}")
    public DoctoralStudentDTO getDoctoralStudent(@PathVariable Long id) {
        DoctoralStudent student = doctoralStudentService.findById(id);
        return convertToDTO(student);
    }

    @PostMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDoctoralStudent(@PathVariable Long id) {
        doctoralStudentService.remove(id);
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
