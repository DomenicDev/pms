package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.dao.DocumentDao;
import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.model.Document;
import de.hfu.pms.service.DocumentService;
import org.springframework.stereotype.Service;


@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;
    private final DoctoralStudentDao doctoralStudentDao;

    public DocumentServiceImpl(DocumentDao documentDao, DoctoralStudentDao doctoralStudentDao) {
        this.documentDao = documentDao;
        this.doctoralStudentDao = doctoralStudentDao;
    }

    @Override
    public void assignDocument(Long doctoralStudentId, Document document) {
        Document savedDocument = documentDao.save(document);
        DoctoralStudent doctoralStudent = doctoralStudentDao.findById(doctoralStudentId).orElseThrow();
        doctoralStudent.getDocuments().add(savedDocument);
        doctoralStudentDao.save(doctoralStudent);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentDao.findById(id).orElseThrow();
    }


    @Override
    public void deleteDocument(Long documentId) {
        documentDao.deleteById(documentId);
    }
}
