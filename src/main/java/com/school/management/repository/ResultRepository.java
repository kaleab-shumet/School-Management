package com.school.management.repository;

import com.school.management.models.Course;
import com.school.management.models.Result;
import com.school.management.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResultRepository extends CrudRepository<Result, Long> {


    @Query("SELECT r FROM Results r WHERE r.user = :user and r.grade = :grade")
    List<Result> findResultByUserIdAndGrade(@Param("user") User user, @Param("grade") int grade);

    @Query("SELECT r FROM Results r WHERE r.user = :user and r.course = :course")
    Optional<Result> findResultByUserAndCourse(User user, Course course);
}
