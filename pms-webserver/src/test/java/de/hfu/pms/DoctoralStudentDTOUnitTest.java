package de.hfu.pms;

import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.model.PersonalData;
import de.hfu.pms.model.QualifiedGraduation;
import de.hfu.pms.model.Support;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PersonalDataDTO;
import de.hfu.pms.shared.dto.SupportDTO;
import de.hfu.pms.shared.dto.TravelCostUniversityDTO;
import de.hfu.pms.shared.enums.Gender;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctoralStudentDTOUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertDoctoralStudentEntityToDoctoralStudentDTO_thenCorrect() {
        DoctoralStudent doc = new DoctoralStudent();

        PersonalData personalData = new PersonalData();
        personalData.setForename("Max");
        personalData.setLastName("Mustermann");
        personalData.setGender(Gender.Male);
        doc.setPersonalData(personalData);

        QualifiedGraduation qualifiedGraduation = new QualifiedGraduation();
        doc.setQualifiedGraduation(qualifiedGraduation);
        qualifiedGraduation.setGrade(new BigDecimal("1.30"));

        DoctoralStudentDTO dto = modelMapper.map(doc, DoctoralStudentDTO.class);

        assertEquals(doc.getPersonalData().getLastName(), dto.getPersonalData().getLastName());
        assertEquals(doc.getPersonalData().getGender(), dto.getPersonalData().getGender());
    }

    @Test
    public void whenConvertEntityToDTO_thenCorrect() {
        DoctoralStudentDTO dto = new DoctoralStudentDTO();
        PersonalDataDTO personalDataDTO = new PersonalDataDTO();
        personalDataDTO.setForename("Max");
        personalDataDTO.setLastName("Mustermann");
        dto.setPersonalData(personalDataDTO);


        List<TravelCostUniversityDTO> list = new ArrayList<>();





        DoctoralStudent entity = modelMapper.map(dto, DoctoralStudent.class);

        assertEquals(dto.getPersonalData().getForename(), entity.getPersonalData().getForename());

        // test for set mapping

        SupportDTO supportDTO = new SupportDTO();
        TravelCostUniversityDTO cost = new TravelCostUniversityDTO();
        cost.setDate(LocalDate.now());
        cost.setSum(new BigDecimal("100.75"));
        supportDTO.getTravelCostUniversities().add(cost);

        Support support = modelMapper.map(supportDTO, Support.class);

        assertEquals(supportDTO.getTravelCostUniversities().size(), support.getTravelCostUniversities().size());

    }


}
