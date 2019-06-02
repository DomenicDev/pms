package de.hfu.pms.service;

import de.hfu.pms.model.Document;

public interface DocumentService {

    void assignDocument(Long doctoralStudentId, Document document);

    Document getDocumentById(Long id);

    void deleteDocument(Long documentId);
}
