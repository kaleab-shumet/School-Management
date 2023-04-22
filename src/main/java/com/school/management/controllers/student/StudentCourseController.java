package com.school.management.controllers.student;

import com.school.management.models.Course;
import com.school.management.models.User;
import com.school.management.services.CourseService;
import com.school.management.services.UserService;
import com.school.management.utils.ApiResponse;
import com.school.management.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/student/courses")
public class StudentCourseController {

    private final CourseService courseService;
    private UserService userService;

    @Autowired
    public StudentCourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable("id") Long id) throws CustomException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User cUser = userService.findByEmail(email);

        Course course = courseService.getCourseByIdAndGrade(id, cUser.getGrade());
        if (course == null){
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Course Not found");
        }
        ApiResponse<Course> response = new ApiResponse<>(HttpStatus.OK.value(), "Course retrieved successfully.", course);
        return response.createResponse();
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() throws CustomException {

        try {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User cUser = userService.findByEmail(email);

            System.out.println(cUser.toString());

            List<Course> courses = courseService.getAllCoursesByGrade(cUser.getGrade());
            ApiResponse<List<Course>> response = new ApiResponse<>(HttpStatus.OK.value(), "Courses retrieved successfully.", courses);
            return response.createResponse();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }


    }





}