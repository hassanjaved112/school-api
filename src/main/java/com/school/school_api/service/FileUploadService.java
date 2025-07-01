package com.school.school_api.service;

import com.school.school_api.entity.Student;
import com.school.school_api.model.UploadStatus;
import com.school.school_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileUploadService {

    @Autowired
    private StudentRepository studentRepository;

    private final Map<String, UploadStatus> statusMap = new ConcurrentHashMap<>();

    public UploadStatus getStatus(String jobId) {
        return statusMap.get(jobId);
    }

    public String startImportJob(MultipartFile file) throws IOException {
        String jobId = "import-" + System.currentTimeMillis();
        UploadStatus status = new UploadStatus();
        statusMap.put(jobId, status);
        runAsyncJob(file, jobId); // triggers @Async method
        return jobId;
    }

    @Async
    public void runAsyncJob(MultipartFile file, String jobId) {
        UploadStatus status = statusMap.get(jobId);

        try (Scanner scanner = new Scanner(file.getInputStream())) {
            int count = 0;
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 3) {
                    Student student = new Student();
                    student.setFirstName(parts[0].trim());
                    student.setLastName(parts[1].trim());
                    student.setEmail(parts[2].trim());
                    studentRepository.save(student);
                }
                count++;
                status.setProcessedRecords(count);
            }

            status.setTotalRecords(count);
            status.setInProgress(false);
            status.setStatusMessage("Completed successfully");

        } catch (Exception ex) {
            status.setInProgress(false);
            status.setStatusMessage("Error during import: " + ex.getMessage());
        }
    }
}
