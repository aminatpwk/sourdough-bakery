package org.example.sourdough.service.users;

import org.example.sourdough.model.dto.AuthenticationResponse;
import org.example.sourdough.model.dto.LoginRequest;
import org.example.sourdough.model.dto.RegistrationResponse;
import org.example.sourdough.model.dto.UserDto;


public interface AuthService {
    public RegistrationResponse registerUser(UserDto userDto);
    public AuthenticationResponse loginUser(LoginRequest loginRequest);
    public AuthenticationResponse refreshToken(String refreshToken);
}
