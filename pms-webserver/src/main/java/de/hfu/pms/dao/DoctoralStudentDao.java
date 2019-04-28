package de.hfu.pms.dao;

import de.hfu.pms.model.DoctoralStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctoralStudentDao extends JpaRepository<DoctoralStudent, Long> {

}
