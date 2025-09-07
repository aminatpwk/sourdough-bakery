package org.example.sourdough.service.users;

import org.example.sourdough.model.User;
import org.example.sourdough.model.dto.UserDto;
import org.example.sourdough.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl {
    private final UserRepository userRepository;
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if(existingUser.isPresent()) {
            throw new RuntimeException("User with this e-mail already exists.");
        }

        User newUser = new User();
        newUser.setFirst_name(userDto.getFirst_name());
        newUser.setLast_name(userDto.getLast_name());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(userDto.getPassword());

        //TODO: add hashed password after well-studying spring security
        newUser.setPassword(userDto.getPassword());
        newUser.setRole("CUSTOMER");
        newUser.setPhone(userDto.getPhone());
        newUser.setIs_active(true);
        newUser.setCreated_at(new Date());

        userRepository.save(newUser);
        return "User registered successfully";
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid e-mail or password."));

        //TODO: implement hashing later
        if(!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid e-mail or password.");
        }

        return user;
    }
}
