package de.hfu.pms.shared.utils;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.dto.TargetGraduationDTO;

import java.time.LocalDate;

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

        // set boolean flags
        TargetGraduationDTO targetGraduationDTO = doctoralStudent.getTargetGraduation();
        if (targetGraduationDTO != null) {
            // membership
            preview.setMemberHFUCollege(targetGraduationDTO.isMemberOfHFUKolleg());

            // active
            if (targetGraduationDTO.getPrognosticatedPromotionDate() != null) {
                if (!targetGraduationDTO.isPromotionCanceled() && LocalDate.now().isBefore(targetGraduationDTO.getPrognosticatedPromotionDate())) {
                    preview.setActive(true);
                }
            } else {
                preview.setActive(false);
            }

        }

        preview.setAnonymized(doctoralStudent.isAnonymized());

        return preview;
    }


}
