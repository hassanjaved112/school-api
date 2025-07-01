package com.school.school_api.service;

import com.school.school_api.entity.Course;
import com.school.school_api.entity.Enrollment;
import com.school.school_api.entity.Student;
import com.school.school_api.repository.CourseRepository;
import com.school.school_api.repository.EnrollmentRepository;
import com.school.school_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Enrollment enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Student is already enrolled in this course.");
        }

        long enrolledCount = enrollmentRepository.countByCourse(course);
        if (enrolledCount >= course.getCapacity()) {
            throw new RuntimeException("Course is full.");
        }

        // Check if student has completed all prerequisites
        Set<Course> prerequisites = course.getPrerequisites();
        for (Course prereq : prerequisites) {
            boolean hasEnrolled = enrollmentRepository.existsByStudentAndCourse(student, prereq);
            if (!hasEnrolled) {
                throw new RuntimeException("Missing prerequisite: " + prereq.getName());
            }
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }
}
