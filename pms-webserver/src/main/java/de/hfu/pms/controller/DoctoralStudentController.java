package de.hfu.pms.controller;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.service.DoctoralStudentService;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class DoctoralStudentController {

    private final DoctoralStudentDao doctoralStudentDao;
    private final DoctoralStudentService doctoralStudentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctoralStudentController(DoctoralStudentDao doctoralStudentDao, DoctoralStudentService doctoralStudentService, ModelMapper modelMapper) {
        this.doctoralStudentDao = doctoralStudentDao;
        this.doctoralStudentService = doctoralStudentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctoralStudentDTO createDoctoralStudent(@RequestBody DoctoralStudentDTO doctoralStudentDTO) {
        DoctoralStudent student = convertToEntity(doctoralStudentDTO);
        DoctoralStudent studentCreated = doctoralStudentService.create(student);
        return convertToDTO(studentCreated);
    }

    @PutMapping("/update/{id}")
    public DoctoralStudentDTO updateDoctoralStudent(@RequestBody DoctoralStudentDTO doctoralStudentDTO, @PathVariable Long id) {
        DoctoralStudent student = convertToEntity(doctoralStudentDTO);
        DoctoralStudent updatedStudent = doctoralStudentService.update(id, student);
        return convertToDTO(updatedStudent);
    }

    @GetMapping("/previews")
    public List<PreviewDoctoralStudentDTO> getAllDoctoralStudentsAsPreview() {
        List<DoctoralStudent> doctoralStudents = doctoralStudentService.getAll();
        List<PreviewDoctoralStudentDTO> previews = new ArrayList<>();
        for (DoctoralStudent student : doctoralStudents) {
            previews.add(convertToPreview(student));
        }
        return previews;
    }

    @GetMapping("/get/{id}")
    public DoctoralStudentDTO getDoctoralStudent(@PathVariable Long id) {
        DoctoralStudent student = doctoralStudentService.findById(id);
        return convertToDTO(student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDoctoralStudent(@PathVariable Long id) {
        doctoralStudentService.remove(id);
    }


    private DoctoralStudent convertToEntity(DoctoralStudentDTO doctoralStudentDTO) {
        DoctoralStudent doctoralStudent = modelMapper.map(doctoralStudentDTO, DoctoralStudent.class);
        return doctoralStudent;
    }

    private DoctoralStudentDTO convertToDTO(DoctoralStudent doctoralStudent) {
        DoctoralStudentDTO doctoralStudentDTO = modelMapper.map(doctoralStudent, DoctoralStudentDTO.class);
        return doctoralStudentDTO;
    }

    private PreviewDoctoralStudentDTO convertToPreview(DoctoralStudent doctoralStudent) {
        PreviewDoctoralStudentDTO preview = new PreviewDoctoralStudentDTO();
        preview.setId(doctoralStudent.getId());
        preview.setForeName(doctoralStudent.getPersonalData().getForename());
        preview.setName(doctoralStudent.getPersonalData().getLastName());
        preview.setFaculty(doctoralStudent.getTargetGraduation().getFacultyHFU());
        preview.setEmail(doctoralStudent.getPersonalData().getEmail());
        preview.setPhoneNumber(doctoralStudent.getPersonalData().getTelephone());
        preview.setGender(doctoralStudent.getPersonalData().getGender());
        return preview;
    }

    @ResponseBody
    @ExceptionHandler(DoctoralStudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String doctoralStudentNotFoundHandler(DoctoralStudentNotFoundException ex) {
        return ex.getMessage();
    }

}
