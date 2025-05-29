package com.caremyhome.controller;

import com.caremyhome.dto.DocumentDTO;
import com.caremyhome.model.Document;
import com.caremyhome.model.Property;
import com.caremyhome.repository.DocumentRepository;
import com.caremyhome.repository.PropertyRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final PropertyRepository propertyRepository;

    @Value("${upload.directory:uploads}")
    private String uploadDir;

    public DocumentController(DocumentRepository documentRepository, PropertyRepository propertyRepository) {
        this.documentRepository = documentRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/upload/{propertyId}")
    public String uploadDocument(@PathVariable UUID propertyId, @RequestParam("file") MultipartFile file) throws IOException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(folder, fileName);
        file.transferTo(dest);

        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setFilePath(dest.getAbsolutePath());
        doc.setProperty(property);
        documentRepository.save(doc);

        return "Upload successful";
    }

    @GetMapping("/{propertyId}")
    public List<Map<String, Object>> getDocumentsByProperty(@PathVariable UUID propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        List<Document> docs = documentRepository.findByProperty(property);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Document d : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", d.getId());
            map.put("name", d.getName());
            map.put("uploadedAt", d.getUploadedAt());
            response.add(map);
        }
        return response;
    }

    @GetMapping("/download/{docId}")
    public void downloadDocument(@PathVariable UUID docId, HttpServletResponse response) throws IOException {
        Document doc = documentRepository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        File file = new File(doc.getFilePath());
        if (!file.exists()) throw new FileNotFoundException("File not found");

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping("/property/{propertyId}")
    public List<DocumentDTO> getByProperty(@PathVariable UUID propertyId) {
        return documentRepository.findByPropertyId(propertyId).stream()
                .map(DocumentDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
