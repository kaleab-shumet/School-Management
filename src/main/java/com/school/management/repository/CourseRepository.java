package com.school.management.repository;

import com.school.management.models.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends CrudRepository<Course,Long> {


    @Query("SELECT c FROM Courses c WHERE c.grade = :grade")
    List<Course> findCourseByGrade(@Param("grade") int grade);

    @Query("SELECT c FROM Courses c WHERE c.id = :id and c.grade = :grade")
    Course findCourseByIdAndGrade(@Param("id") Long id, @Param("grade") Integer grade);

}
