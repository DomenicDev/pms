package de.hfu.pms.controller;

import de.hfu.pms.model.University;
import de.hfu.pms.service.UniversityService;
import de.hfu.pms.shared.dto.UniversityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {

    private final UniversityService universityService;
    private final ModelMapper modelMapper;

    @Autowired
    public UniversityController(UniversityService universityService, ModelMapper modelMapper) {
        this.universityService = universityService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UniversityDTO newUser (@RequestBody UniversityDTO universityDTO ){
        University university = convertToEntity(universityDTO);
        university = universityService.createUniversity(university);
        return convertToDTO(university);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUniversity (@PathVariable Long id){
        universityService.deleteUniversity(id);
        return "Die Universität wurde erfolgreich gelöscht.";
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UniversityDTO getUniversity(@PathVariable Long id){
        return convertToDTO(universityService.getUniversity(id));
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UniversityDTO updateUniversity(@PathVariable Long id, @RequestBody UniversityDTO universityDTO){
        University university = convertToEntity(universityDTO);
        University newUniversity = universityService.updateUniversity(id, university);
        return convertToDTO(newUniversity);
    }

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<University> getUniversityList(){
        return universityService.getUniversityList();
    }


    private University convertToEntity(UniversityDTO universityDTO) {
        return modelMapper.map(universityDTO, University.class);
    }

    private UniversityDTO convertToDTO(University university) {
        return modelMapper.map(university, UniversityDTO.class);
    }
}
