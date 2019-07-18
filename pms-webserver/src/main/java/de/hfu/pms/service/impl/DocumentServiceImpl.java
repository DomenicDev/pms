package de.hfu.pms.service.impl;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.dao.DocumentDao;
import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.model.Document;
import de.hfu.pms.service.DocumentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;


@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;
    private final DoctoralStudentDao doctoralStudentDao;

    public DocumentServiceImpl(DocumentDao documentDao, DoctoralStudentDao doctoralStudentDao) {
        this.documentDao = documentDao;
        this.doctoralStudentDao = doctoralStudentDao;
    }

    @Override
    public void assignDocument(Long doctoralStudentId, String filename, byte[] data) throws IOException {
        // create new document object
        Document document = new Document();
        document.setFilename(filename);

        // save it to document dao
        Document savedDocument = documentDao.save(document);

        // assign it to the specific doctoral student and save the changes
        DoctoralStudent doctoralStudent = doctoralStudentDao.findById(doctoralStudentId).orElseThrow();
        doctoralStudent.getDocuments().add(savedDocument);
        doctoralStudentDao.save(doctoralStudent);

        // now we want to save the file on the disk (local storage)

        // look up root upload folder
        Path path = Paths.get("/uploads/");
        if ( ! path.toFile().exists() ) {
            path.toFile().mkdir();
        }

        Path filePath = Paths.get("/uploads/" + savedDocument.getId());
        Files.write(filePath, data);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentDao.findById(id).orElseThrow();
    }

    @Override
    public byte[] getDocumentData(Long id) throws IOException {
        Path filePath = Paths.get("/uploads/" + id);
        return Files.readAllBytes(filePath);
    }

    @Override
    public void deleteDocument(Long documentId) {
        documentDao.deleteById(documentId);
        // todo remove on disk as well

    }

    @Override
    public void deleteAll(Long doctoralStudentId) {
        DoctoralStudent doctoralStudent = doctoralStudentDao.findById(doctoralStudentId).orElseThrow();
        Set<Document> documents = doctoralStudent.getDocuments();
        if (documents != null) {
            documents.clear();
        }
        doctoralStudentDao.save(doctoralStudent);
    }
}
