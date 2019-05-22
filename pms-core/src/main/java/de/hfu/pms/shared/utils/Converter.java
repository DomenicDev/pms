package de.hfu.pms.shared.utils;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;

public class Converter {

    public static PreviewDoctoralStudentDTO convert(DoctoralStudentDTO doctoralStudent) {
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


}
