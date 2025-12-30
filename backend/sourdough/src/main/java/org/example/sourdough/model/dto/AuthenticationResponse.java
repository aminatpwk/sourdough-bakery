package org.example.sourdough.model.dto;

public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public AuthenticationResponse() {}

    public AuthenticationResponse(String accessToken, String refreshToken, String email, String firstName, String lastName, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
