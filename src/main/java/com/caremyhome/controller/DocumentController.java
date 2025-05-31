package com.caremyhome.controller;

import com.caremyhome.model.Document;
import com.caremyhome.service.PropertyDocumentService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final PropertyDocumentService docService;


    public DocumentController(PropertyDocumentService docService) {
        this.docService = docService;
    }

    // GET /api/documents/{propertyId}
    @GetMapping("/{propertyId}")
    public List<Map<String, Object>> getDocuments(@PathVariable UUID propertyId) {
        return docService.getDocuments(propertyId).stream().map(doc -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", doc.getId());
            m.put("name", doc.getName());
            m.put("uploadedAt", doc.getUploadedAt());
            // No filePath for security!
            return m;
        }).collect(Collectors.toList());
    }

    // POST /api/documents/upload/{propertyId}
    @PostMapping("/upload/{propertyId}")
    public ResponseEntity<?> upload(
            @PathVariable UUID propertyId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Document doc = docService.upload(propertyId, file);
            Map<String, Object> m = new HashMap<>();
            m.put("id", doc.getId());
            m.put("name", doc.getName());
            m.put("uploadedAt", doc.getUploadedAt());
            return ResponseEntity.ok(m);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // GET /api/documents/download/{docId}
    @GetMapping("/download/{docId}")
    public ResponseEntity<?> download(@PathVariable UUID docId) throws IOException {
        Document doc = docService.getDocument(docId);

        File file = new File(doc.getFilePath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}