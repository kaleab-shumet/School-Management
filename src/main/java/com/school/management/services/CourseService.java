package com.school.management.services;

import com.school.management.models.Course;
import com.school.management.repository.CourseRepository;
import com.school.management.utils.updatables.CourseUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {

        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(course);

    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        courseRepository.findAll().forEach(allCourses::add);
        return allCourses;
    }

    public List<Course> getAllCoursesByGrade(int grade) {
        List<Course> allCourses = new ArrayList<>();
        courseRepository.findCourseByGrade(grade).forEach(allCourses::add);
        return allCourses;
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course getCourseByIdAndGrade(Long id, int grade) {
        return courseRepository.findCourseByIdAndGrade(id, grade);
    }

    public Course updateCourse(Long id, CourseUpdatable courseUpdatable) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse == null) {
            return null;
        }
        if (courseUpdatable == null) return null;

        if (courseUpdatable.getName() != null) existingCourse.setName(courseUpdatable.getName());
        if (courseUpdatable.getInstructor() != null) existingCourse.setInstructor(courseUpdatable.getInstructor());
        if (courseUpdatable.getGrade() > 0) existingCourse.setGrade(courseUpdatable.getGrade());

        existingCourse.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(existingCourse);


    }

    public boolean deleteCourseById(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}