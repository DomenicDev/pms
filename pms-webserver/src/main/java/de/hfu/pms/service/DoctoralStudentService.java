package de.hfu.pms.service;

import de.hfu.pms.model.*;

import java.util.List;

public interface DoctoralStudentService {

    DoctoralStudent create(DoctoralStudent doctoralStudent);

    DoctoralStudent update(Long id, DoctoralStudent doctoralStudent);

    DoctoralStudent updatePhoto(Long id, byte[] data);

    DoctoralStudent update(Long id, PersonalData personalData);
    DoctoralStudent update(Long id, QualifiedGraduation qualifiedGraduation);
    DoctoralStudent update(Long id, TargetGraduation targetGraduation);
    DoctoralStudent update(Long id, Employment employment);
    DoctoralStudent update(Long id, Support support);
    DoctoralStudent update(Long id, AlumniState alumniState);

    DoctoralStudent findById(Long id);

    void remove(Long id);

    void anonymize(Long id);

    List<DoctoralStudent> getAll();

    List<DoctoralStudent> search();


}
