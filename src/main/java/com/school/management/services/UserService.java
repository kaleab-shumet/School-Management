package com.school.management.services;

import com.school.management.models.User;
import com.school.management.repository.UserRepository;
import com.school.management.utils.UserRole;
import com.school.management.utils.updatables.UserUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Bean
    public BCryptPasswordEncoder newPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User createUser(User user) {
        user.setPassword(newPasswordEncoder().encode(user.getPassword()));
        user.setRole(UserRole.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        userRepository.findAll().forEach(allUsersList::add);
        return allUsersList;
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User updateUserById(Long id, UserUpdatable userUpdatable) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }


        if (userUpdatable == null)
            return null;

        if (userUpdatable.getFirstName() != null)
            existingUser.setFirstName(userUpdatable.getFirstName());

        if (userUpdatable.getLastName() != null)
            existingUser.setLastName(userUpdatable.getLastName());

        if (userUpdatable.getEmail() != null)
            existingUser.setEmail(userUpdatable.getEmail());

        if (userUpdatable.getGrade() > 0)
            existingUser.setGrade(userUpdatable.getGrade());

        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}