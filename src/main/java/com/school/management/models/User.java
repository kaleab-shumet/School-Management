package com.school.management.models;


import com.school.management.utils.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Entity(name = "Users")
@Data
public class User extends BaseEntity {

    @Id
    @Column(nullable= false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false)
    @NotBlank(message="First Name must not be blank")
    @Size(min=3, message="First Name must be at least 3 characters long")
    private String firstName;


    @Column(nullable=false)
    @NotBlank(message="Last Name must not be blank")
    @Size(min=3, message="Last Name must be at least 3 characters long")
    private String lastName;

    @Column(nullable=false, unique = true)
    @NotBlank(message="Email must not be blank")
    @Email(message = "Please enter valid email")
    private String email;

    @Column(nullable=false)
    @NotBlank(message="Password must not be blank")
    private String password;


    @Column(nullable=false)
    @NotNull(message="Grade must be entered")
    @Min(value=1, message="Grade must not be negative")
    private int grade;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Column(name = "role", columnDefinition = "VARCHAR(10) default 'STUDENT'")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }



}
