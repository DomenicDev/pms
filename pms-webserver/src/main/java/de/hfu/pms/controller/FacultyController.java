package de.hfu.pms.controller;

import de.hfu.pms.model.Faculty;
import de.hfu.pms.service.FacultyService;
import de.hfu.pms.shared.dto.FacultyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    private ModelMapper modelMapper = new ModelMapper();

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("create")
    public FacultyDTO createFaculty(@RequestBody FacultyDTO facultyDTO) {
        Faculty faculty = convertToEntity(facultyDTO);
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return convertToDTO(createdFaculty);
    }

    @PostMapping("/update/{id}")
    public FacultyDTO updateDoctoralStudent(@RequestBody FacultyDTO facultyDTO, @PathVariable Long id) {
        Faculty student = convertToEntity(facultyDTO);
        Faculty updatedStudent = facultyService.editFaculty(id, student);
        return convertToDTO(updatedStudent);
    }

    @GetMapping("getList")
    public Collection<Faculty> getAll() {
        return facultyService.getAll();
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
    }

    private Faculty convertToEntity(FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }

    private FacultyDTO convertToDTO(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDTO.class);
    }

}
