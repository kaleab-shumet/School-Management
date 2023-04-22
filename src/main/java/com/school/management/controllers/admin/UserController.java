package com.school.management.controllers.admin;

import com.school.management.models.User;
import com.school.management.services.UserService;
import com.school.management.utils.ApiResponse;
import com.school.management.utils.CustomException;
import com.school.management.utils.MyUtils;
import com.school.management.utils.returnables.UserReturnable;
import com.school.management.utils.updatables.UserUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
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

    @PostMapping
    public ResponseEntity<ApiResponse<UserReturnable>> createUser(@RequestBody User user) throws CustomException {
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "User already exists");
        }

        User savedUser = userService.createUser(user);
        ApiResponse<UserReturnable> userApiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully.", new UserReturnable(savedUser));
        return userApiResponse.createResponse();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserReturnable>> updateUser(@PathVariable Long id, @RequestBody UserUpdatable userUpdatable) {
        User updatedUser = userService.updateUserById(id, userUpdatable);
        ApiResponse<UserReturnable> userApiResponse;
        if (updatedUser != null) {
            userApiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", new UserReturnable(updatedUser));
        } else {
            userApiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
        }
        return userApiResponse.createResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        ApiResponse<String> apiResponse;
        if (deleted) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
        }
        return apiResponse.createResponse();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserReturnable>>> getAllUsers(@RequestParam(required = false) String grade) {

        System.out.println("getAllUsers - Get all users runned");

        int grd = MyUtils.parseToInt(grade, -1);

        List<User> users = userService.getAllUsers();

        if (grd > 0) {
            users = users.stream().filter(e -> (e.getGrade() == grd)).toList();
        }

        ApiResponse<List<UserReturnable>> listApiResponse;
        if (!users.isEmpty()) {
            List<UserReturnable> newUsers = users.stream().map(UserReturnable::new).toList();
            listApiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Users retrieved successfully.", newUsers);
        } else {
            listApiResponse = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "No users found.", null);
        }
        return listApiResponse.createResponse();
    }
}