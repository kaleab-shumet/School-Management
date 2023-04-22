package com.school.management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Entity(name = "Courses")
@Data
public class Course extends BaseEntity {

    @Id
    @Column(nullable= false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false)
    @NotBlank(message="Course Name must not be blank")
    @Size(min=3, message="Course Name must be at least 3 characters long")
    private String name;


    @Column(nullable=false)
    @NotBlank(message="Instructor must not be blank")
    private String instructor;


    @Column(nullable=false)
    @NotNull(message="Grade must be entered")
    @Min(value=1, message="Grade must not be negative")
    private int grade;



}
