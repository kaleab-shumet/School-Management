package com.school.management.utils.returnables;

import com.school.management.models.User;
import com.school.management.utils.UserRole;
import lombok.Data;

@Data
public class UserReturnable {


    private UserRole role;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int grade;

    public UserReturnable(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setGrade(user.getGrade());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setRole(user.getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
