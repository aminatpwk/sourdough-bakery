package org.example.sourdough.service.users;

import org.example.sourdough.exception.AuthenticationFailedException;
import org.example.sourdough.exception.InvalidTokenException;
import org.example.sourdough.exception.ResourceAlreadyExistsException;
import org.example.sourdough.model.User;
import org.example.sourdough.model.dto.AuthenticationResponse;
import org.example.sourdough.model.dto.LoginRequest;
import org.example.sourdough.model.dto.RegistrationResponse;
import org.example.sourdough.model.dto.UserDto;
import org.example.sourdough.model.security.SecurityUser;
import org.example.sourdough.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtServiceImpl jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public RegistrationResponse registerUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if(existingUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", userDto.getEmail());
        }

        User newUser = new User();
        newUser.setFirst_name(userDto.getFirst_name());
        newUser.setLast_name(userDto.getLast_name());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole("CUSTOMER");
        newUser.setPhone(userDto.getPhone());
        newUser.setIs_active(true);
        newUser.setCreated_at(new Date());

        userRepository.save(newUser);
        return new RegistrationResponse("User registered successfully", newUser.getEmail());
    }

    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch(AuthenticationException e){
            throw new AuthenticationFailedException("Invalid e-mail or password.");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = new SecurityUser(user);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                user.getEmail(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getRole());
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        try{
            String userEmail = jwtService.extractUsername(refreshToken);
            User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
            UserDetails userDetails = new SecurityUser(user);

            if (!jwtService.isValidJwt(refreshToken, userDetails)) {
                throw new InvalidTokenException("Invalid or expired refresh token");
            }

            String newAccessToken = jwtService.generateToken(userDetails);
            return new AuthenticationResponse(
                    newAccessToken,
                    refreshToken,
                    user.getEmail(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getRole());

        } catch(InvalidTokenException e){
            throw e;
        }
        catch(Exception e){
            throw new InvalidTokenException("Invalid refresh token.", e);
        }
    }
}
