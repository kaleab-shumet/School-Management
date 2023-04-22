package com.school.management.controllers.student;

import com.school.management.models.User;
import com.school.management.services.UserService;
import com.school.management.utils.ApiResponse;
import com.school.management.utils.returnables.UserReturnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/student/users")
public class StudentUserController {

    private final UserService userService;

    @Autowired
    public StudentUserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserReturnable>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        ApiResponse<UserReturnable> userApiResponse;
        if (user != null) {
            userApiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User retrieved successfully.", new UserReturnable(user));
        } else {
            userApiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
        }
        return userApiResponse.createResponse();
    }
}