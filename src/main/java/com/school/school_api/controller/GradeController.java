package com.school.school_api.controller;

import com.school.school_api.entity.Grade;
import com.school.school_api.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.school.school_api.dto.GradeSummaryDTO;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity<Grade> assignGrade(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam String gradeValue) {
        return ResponseEntity.ok(gradeService.assignGrade(studentId, courseId, gradeValue));
    }

    @PostMapping("/complete")
    public ResponseEntity<Grade> completeCourse(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        return ResponseEntity.ok(gradeService.completeCourse(studentId, courseId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }
    

    @GetMapping("/summary/{studentId}")
    public ResponseEntity<List<GradeSummaryDTO>> getSummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getAcademicSummary(studentId));
    }
}