package com.school.school_api.repository;

import com.school.school_api.entity.Course;
import com.school.school_api.entity.Grade;
import com.school.school_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByStudentAndCourse(Student student, Course course);
    List<Grade> findByStudent(Student student);
}
