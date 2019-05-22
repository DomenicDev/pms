package de.hfu.pms.dao;

import de.hfu.pms.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityDao extends JpaRepository<University, Long> {
}
