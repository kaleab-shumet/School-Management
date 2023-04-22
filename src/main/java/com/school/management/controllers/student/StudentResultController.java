package com.school.management.controllers.student;

import com.school.management.models.Result;
import com.school.management.models.User;
import com.school.management.services.CourseService;
import com.school.management.services.ResultService;
import com.school.management.services.UserService;
import com.school.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/student/results")
public class StudentResultController {

    private final ResultService resultService;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public StudentResultController(ResultService resultService, CourseService courseService, UserService userService) {
        this.resultService = resultService;
        this.courseService = courseService;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Result>> getResultById(@PathVariable Long id) {
        Result result = resultService.findResultById(id);
        ApiResponse<Result> userApiResponse;
        if (result != null) {
            userApiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Result retrieved successfully.", result);
        } else {
            userApiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Result not found.", null);
        }
        return userApiResponse.createResponse();
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Result>>> getAllResults() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User cUser = userService.findByEmail(email);

        System.out.println(cUser.toString());

        List<Result> allResults = resultService.getResultsByUserAndGrade(cUser, cUser.getGrade());
        ApiResponse<List<Result>> apiResponse;
        if (!allResults.isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Results retrieved successfully.", allResults);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "No Result found.", null);
        }
        return apiResponse.createResponse();
    }

}