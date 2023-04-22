package com.school.management.controllers.admin;

import com.school.management.models.Course;
import com.school.management.services.CourseService;
import com.school.management.utils.ApiResponse;
import com.school.management.utils.CustomException;
import com.school.management.utils.MyUtils;
import com.school.management.utils.updatables.CourseUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses(@RequestParam(required = false) String grade) throws CustomException {



        try {

            int grd = MyUtils.parseToInt(grade, -1);

            List<Course> courses = courseService.getAllCourses();

            if (grd > 0){
                courses = courses.stream().filter(e -> (e.getGrade() == grd)).toList();
            }

            ApiResponse<List<Course>> response = new ApiResponse<>(HttpStatus.OK.value(), "Courses retrieved successfully.", courses);
            return response.createResponse();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable("id") Long id) throws CustomException {

        Course course = courseService.getCourseById(id);
        if (course == null){
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Course Not found");
        }
        ApiResponse<Course> response = new ApiResponse<>(HttpStatus.OK.value(), "Course retrieved successfully.", course);
        return response.createResponse();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.createCourse(course);
        ApiResponse<Course> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Course added successfully.", newCourse);
        return response.createResponse();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable("id") Long id, @RequestBody CourseUpdatable courseUpdatable) throws CustomException {
        Course updatedCourse = courseService.updateCourse(id, courseUpdatable);
        if (updatedCourse == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Course Not found");
        }
        ApiResponse<Course> response = new ApiResponse<>(HttpStatus.OK.value(), "Course updated successfully.", updatedCourse);
        return response.createResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable("id") Long id) throws CustomException {
        boolean deleted = courseService.deleteCourseById(id);
        if (!deleted) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Course Not found");
        }
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Course deleted successfully.", "Course with ID: " + id + " deleted.");
        return response.createResponse();
    }



}