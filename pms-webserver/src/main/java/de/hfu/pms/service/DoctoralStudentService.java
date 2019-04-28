package de.hfu.pms.service;

import de.hfu.pms.model.DoctoralStudent;

import java.util.List;

public interface DoctoralStudentService {

    DoctoralStudent create(DoctoralStudent doctoralStudent);

    DoctoralStudent update(Long id, DoctoralStudent doctoralStudent);

    DoctoralStudent findById(Long id);

    void remove(Long id);

    void anonymize(Long id);

    List<DoctoralStudent> getAll();

    List<DoctoralStudent> search();


}
