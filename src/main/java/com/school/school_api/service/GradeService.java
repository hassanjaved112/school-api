package com.school.school_api.service;

import com.school.school_api.entity.*;
import com.school.school_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.school.school_api.dto.GradeSummaryDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Grade assignGrade(Long studentId, Long courseId, String gradeValue) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Student is not enrolled in this course.");
        }

        Grade grade = gradeRepository.findByStudentAndCourse(student, course)
                .orElse(new Grade());

        if (grade.getId() != null && grade.isCourseCompleted()) {
            throw new RuntimeException("Cannot update grade. Course already completed.");
        }

        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGradeValue(gradeValue);
        grade.setCourseCompleted(false); // Optional logic

        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return gradeRepository.findByStudent(student);
    }

    public Grade completeCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Grade grade = gradeRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new RuntimeException("Grade not assigned yet."));

        grade.setCourseCompleted(true);
        return gradeRepository.save(grade);
    }
    
    public List<GradeSummaryDTO> getAcademicSummary(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Grade> grades = gradeRepository.findByStudent(student);
        List<GradeSummaryDTO> summaryList = new ArrayList<>();

        for (Grade grade : grades) {
            double gpa = convertLetterGradeToGPA(grade.getGradeValue());
            summaryList.add(new GradeSummaryDTO(
                    grade.getCourse().getName(),
                    grade.getGradeValue(),
                    gpa
            ));
        }

        return summaryList;
    }

    private double convertLetterGradeToGPA(String letterGrade) {
        return switch (letterGrade.toUpperCase()) {
            case "A+", "A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> throw new RuntimeException("Invalid grade: " + letterGrade);
        };
    }
}