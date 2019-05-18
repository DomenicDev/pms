package de.hfu.pms.dao;

import de.hfu.pms.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityDao extends JpaRepository<University,int> {
}
