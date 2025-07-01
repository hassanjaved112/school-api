package com.school.school_api.service;

import com.school.school_api.entity.Course;
import com.school.school_api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        if (courseRepository.existsByName(course.getName())) {
            throw new RuntimeException("Course with this name already exists.");
        }

        // Load prerequisite references from DB if passed by ID only
        Set<Course> loadedPrereqs = new HashSet<>();
        for (Course prereq : course.getPrerequisites()) {
            Course loaded = courseRepository.findById(prereq.getId())
                    .orElseThrow(() -> new RuntimeException("Prerequisite course not found: ID " + prereq.getId()));
            loadedPrereqs.add(loaded);
        }

        course.setPrerequisites(loadedPrereqs);
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course updateCourse(Long id, Course updated) {
        Course course = getCourseById(id);
        course.setName(updated.getName());
        course.setDescription(updated.getDescription());
        course.setCapacity(updated.getCapacity());

        Set<Course> updatedPrereqs = new HashSet<>();
        for (Course prereq : updated.getPrerequisites()) {
            Course loaded = courseRepository.findById(prereq.getId())
                    .orElseThrow(() -> new RuntimeException("Invalid prerequisite ID: " + prereq.getId()));
            updatedPrereqs.add(loaded);
        }

        course.setPrerequisites(updatedPrereqs);
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        courseRepository.deleteById(id);
    }
}