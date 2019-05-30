package de.hfu.pms.dao;

import de.hfu.pms.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDao extends JpaRepository<Document, Long> {
}
