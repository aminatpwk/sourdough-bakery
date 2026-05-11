package org.example.sourdough.service.users;

import org.example.sourdough.model.dto.UserResponse;

import java.util.List;

public interface UserService {
    public List<UserResponse> getAllUsers();
}
