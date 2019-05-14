package de.hfu.pms.controller;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.exceptions.DoctoralStudentNotFoundException;
import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.model.PersonalData;
import de.hfu.pms.service.DoctoralStudentService;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PersonalDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @ExceptionHandler(DoctoralStudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String doctoralStudentNotFoundHandler(DoctoralStudentNotFoundException ex) {
        return ex.getMessage();
    }


    // samples (can be deleted soon)
    private int c = 1;
    @RequestMapping("/sample")
    public DoctoralStudent docStudent() {
        DoctoralStudent doc = new DoctoralStudent();
        doc.setPersonalData(new PersonalData());
        doc.getPersonalData().setForename("Domenic"+(c++));
        doctoralStudentDao.save(doc);
        return doc;
    }

    @RequestMapping(value= "/add_sample")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctoralStudentDTO getSampleDTO(@RequestParam(value = "name", defaultValue = "World") String name) {
        DoctoralStudentDTO dto = new DoctoralStudentDTO();
        dto.setPersonalData(new PersonalDataDTO());
        dto.getPersonalData().setForename(name);
        DoctoralStudent student = convertToEntity(dto);
        return convertToDTO(student);
    }

}
