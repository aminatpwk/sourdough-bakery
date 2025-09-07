package org.example.sourdough.service.users;

import org.example.sourdough.model.User;
import org.example.sourdough.model.dto.UserDto;


public interface AuthService {
    public String registerUser(UserDto userDto);
    public User loginUser(String email, String password);
}
