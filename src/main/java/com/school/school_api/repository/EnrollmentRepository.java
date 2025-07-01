package com.school.school_api.repository;

import com.school.school_api.entity.Course;
import com.school.school_api.entity.Enrollment;
import com.school.school_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentAndCourse(Student student, Course course);
    long countByCourse(Course course);
    List<Enrollment> findByStudent(Student student);
}