package com.school.school_api.controller;

import com.school.school_api.model.UploadStatus;
import com.school.school_api.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService uploadService;

    @PostMapping("/students")
    public ResponseEntity<String> uploadStudents(@RequestParam("file") MultipartFile file) throws IOException {
        String jobId = uploadService.startImportJob(file);
        return ResponseEntity.ok(jobId);
    }

    @GetMapping("/status/{jobId}")
    public ResponseEntity<UploadStatus> getStatus(@PathVariable String jobId) {
        UploadStatus status = uploadService.getStatus(jobId);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
    }
}