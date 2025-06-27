package com.caremyhome.service;

import com.caremyhome.model.Document;
import com.caremyhome.model.Property;
import com.caremyhome.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.caremyhome.repository.PropertyRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyDocumentService {

    private final DocumentRepository docRepo;
    private final PropertyRepository propertyRepo;

    @Value("${afrihaven.uploads.dir:uploads}")
    private String uploadsDir;

    public PropertyDocumentService(DocumentRepository docRepo, PropertyRepository propertyRepo) {
        this.docRepo = docRepo;
        this.propertyRepo = propertyRepo;
    }

    @Transactional
    public Document upload(UUID propertyId, MultipartFile file) throws IOException {
        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        String folder = uploadsDir + "/property_" + propertyId;
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();

        String filename = Instant.now().toEpochMilli() + "_" + file.getOriginalFilename();
        String filepath = folder + "/" + filename;
        File dest = new File(filepath);
        file.transferTo(dest);

        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setFilePath(filepath);
        doc.setUploadedAt(Instant.now());
        doc.setProperty(property);

        return docRepo.save(doc);
    }

    public List<Document> getDocuments(UUID propertyId) {
        return docRepo.findByProperty_Id(propertyId);
    }

    public Document getDocument(UUID docId) {
        return docRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }
}
