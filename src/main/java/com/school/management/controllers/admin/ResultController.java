package com.school.management.controllers.admin;

import com.school.management.models.Course;
import com.school.management.models.Result;
import com.school.management.models.User;
import com.school.management.services.CourseService;
import com.school.management.services.ResultService;
import com.school.management.services.UserService;
import com.school.management.utils.ApiResponse;
import com.school.management.utils.CustomException;
import com.school.management.utils.MyUtils;
import com.school.management.utils.updatables.ResultUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/results")
public class ResultController {

    private final ResultService resultService;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public ResultController(ResultService resultService, CourseService courseService, UserService userService) {
        this.resultService = resultService;
        this.courseService = courseService;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Result>> getResultById(@PathVariable Long id) {

        try {
            Result result = resultService.findResultById(id);
            ApiResponse<Result> resultApiResponse;
            if (result != null) {
                resultApiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Result retrieved successfully.", result);
            } else {
                resultApiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Result not found.", null);
            }
            return resultApiResponse.createResponse();
        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping
    public ResponseEntity<ApiResponse<Result>> createResult(@RequestBody ResultUpdatable resultUpdatable) throws CustomException {

        Course course = courseService.getCourseById(resultUpdatable.getCourseId());
        User user = userService.getUserById(resultUpdatable.getUserId());

        ApiResponse<Result> resultApiResponse;


        if (course == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Not valid course");
        } else if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "User not found");
        } else {

            Result resultRecord = resultService.getResultByUserAndCourse(user, course);
            if (resultRecord != null) {
                throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Duplicate Found");
            }

            Result result = new Result(user, course, resultUpdatable.getTestOne(), resultUpdatable.getAssignmentOne(), resultUpdatable.getTestTwo(), resultUpdatable.getAssignmentTwo(), resultUpdatable.getFinalExam(), course.getGrade());
            Result savedResult = resultService.createResult(result);
            resultApiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), "Result created successfully.", savedResult);
        }
        return resultApiResponse.createResponse();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Result>> updateResult(@PathVariable Long id, @RequestBody ResultUpdatable resultUpdatable) {
        Result updateResult = resultService.updateResult(id, resultUpdatable);
        ApiResponse<Result> resultApiResponse;
        if (updateResult != null) {
            resultApiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Result updated successfully.", updateResult);
        } else {
            resultApiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Result not found.", null);
        }
        return resultApiResponse.createResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteResult(@PathVariable Long id) {
        boolean deleted = resultService.deleteResultById(id);
        ApiResponse<String> apiResponse;
        if (deleted) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Result deleted successfully.", null);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Result not found.", null);
        }
        return apiResponse.createResponse();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Result>>> getAllResults(@RequestParam(required = false) String grade, @RequestParam(required = false) String courseId) {

        System.out.println("getAllUsers - Get all allResults runned");

        List<Result> allResults = resultService.getAllResults();

        int grd = MyUtils.parseToInt(grade, -1);
        long course_id = MyUtils.parseToLong(courseId, -1);

        if (grd > 0) {
            allResults = allResults.stream().filter(e -> e.getGrade() == grd).toList();
        }

        if (course_id > 0) {
            allResults = allResults.stream().filter(e -> e.getCourse().getId() == course_id).toList();
        }

        ApiResponse<List<Result>> apiResponse;
        if (!allResults.isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Results retrieved successfully.", allResults);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "No Result found.", null);
        }
        return apiResponse.createResponse();
    }
}