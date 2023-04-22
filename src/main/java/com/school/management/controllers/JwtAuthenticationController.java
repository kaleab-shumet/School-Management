package com.school.management.controllers;

import com.school.management.models.User;
import com.school.management.repository.UserRepository;
import com.school.management.services.JwtUserDetailsService;
import com.school.management.services.UserService;
import com.school.management.utils.ApiResponse;
import com.school.management.utils.CustomException;
import com.school.management.utils.UserRole;
import com.school.management.utils.jwt.JwtRequest;
import com.school.management.utils.jwt.JwtResponse;
import com.school.management.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {


            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Incorrect email or password");
        }


        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        ApiResponse<JwtResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "Login Success", new JwtResponse(token));


        return response.createResponse();
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAdmin() throws Exception {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User cUser = userService.findByEmail(email);

            if (cUser == null) {
                throw new CustomException(HttpStatus.NOT_FOUND.value(), "Not Found");
            } else if (cUser.getRole() != UserRole.ADMIN) {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
            } else {
                ApiResponse<String> vResponse = new ApiResponse<>(HttpStatus.OK.value(), "Verification Successful");
                return vResponse.createResponse();
            }

        } catch (Exception e) {
            throw e;
        }
    }

}