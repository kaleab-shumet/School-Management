package com.school.management.utils.updatables;

import com.school.management.models.Course;
import com.school.management.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ResultUpdatable {

    private long userId;
    private long courseId;
    private BigDecimal testOne;
    private BigDecimal assignmentOne;
    private BigDecimal testTwo;
    private BigDecimal assignmentTwo;
    private BigDecimal finalExam;
    private int grade = -1;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getTestOne() {
        return testOne;
    }

    public void setTestOne(BigDecimal testOne) {
        this.testOne = testOne;
    }

    public BigDecimal getAssignmentOne() {
        return assignmentOne;
    }

    public void setAssignmentOne(BigDecimal assignmentOne) {
        this.assignmentOne = assignmentOne;
    }

    public BigDecimal getTestTwo() {
        return testTwo;
    }

    public void setTestTwo(BigDecimal testTwo) {
        this.testTwo = testTwo;
    }

    public BigDecimal getAssignmentTwo() {
        return assignmentTwo;
    }

    public void setAssignmentTwo(BigDecimal assignmentTwo) {
        this.assignmentTwo = assignmentTwo;
    }

    public BigDecimal getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(BigDecimal finalExam) {
        this.finalExam = finalExam;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
