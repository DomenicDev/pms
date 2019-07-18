package de.hfu.pms.service;

import de.hfu.pms.model.Document;

import java.io.IOException;

public interface DocumentService {

    void assignDocument(Long doctoralStudentId, String filename, byte[] data) throws IOException;

    Document getDocumentById(Long id);

    byte[] getDocumentData(Long id) throws IOException;

    void deleteDocument(Long documentId);

    void deleteAll(Long doctoralStudentId);
}
