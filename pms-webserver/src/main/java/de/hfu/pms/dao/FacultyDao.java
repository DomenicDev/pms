package de.hfu.pms.dao;

import de.hfu.pms.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyDao extends JpaRepository<Faculty, Long> {
}
