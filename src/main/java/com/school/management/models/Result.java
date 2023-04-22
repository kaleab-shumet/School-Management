package com.school.management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;


@Entity(name = "Results")
@Data
public class Result extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @Column()

    @Min(value = 0, message = "Test One must not be negative")
    @Max(value = 15, message = "Test One must not greater than 15")
    private BigDecimal testOne;
    @Column
    @Min(value = 0, message = "AssignmentOne must not be negative")
    @Max(value = 20, message = "AssignmentOne must not greater than 20")
    private BigDecimal assignmentOne;
    @Column
    @Min(value = 0, message = "TestTwo must not be negative")
    @Max(value = 15, message = "TestTwo must not greater than 15")
    private BigDecimal testTwo;
    @Column
    @Min(value = 0, message = "AssignmentTwo must not be negative")
    @Max(value = 20, message = "AssignmentTwo must not greater than 20")
    private BigDecimal assignmentTwo;
    @Column
    @Min(value = 0, message = "FinalExam must not be negative")
    @Max(value = 30, message = "FinalExam must not greater than 30")
    private BigDecimal finalExam;
    @Column(nullable = false)
    @NotNull(message = "Grade must be entered")
    @Min(value = 1, message = "Grade must not be negative")
    @Max(value = 12, message = "Grade must not greater than 12")
    private int grade;

    public Result(User user, Course course, BigDecimal testOne, BigDecimal assignmentOne, BigDecimal testTwo, BigDecimal assignmentTwo, BigDecimal finalExam, int grade) {
        this.user = user;
        this.course = course;
        this.testOne = testOne;
        this.assignmentOne = assignmentOne;
        this.testTwo = testTwo;
        this.assignmentTwo = assignmentTwo;
        this.finalExam = finalExam;
        this.grade = grade;
    }


    public Result() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
