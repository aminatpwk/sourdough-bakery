package org.example.sourdough.model.dto;

import org.example.sourdough.model.User;

import java.util.Date;

public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private boolean isActive;
    private Date createdAt;

    public UserResponse() {}

    public static UserResponse from(User user) {
        UserResponse dto = new UserResponse();
        dto.id = user.getId();
        dto.firstName = user.getFirst_name();
        dto.lastName = user.getLast_name();
        dto.email = user.getEmail();
        dto.phone = user.getPhone();
        dto.role = user.getRole();
        dto.isActive = user.isIs_active();
        dto.createdAt = user.getCreated_at();
        return dto;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public boolean isActive() { return isActive; }
    public Date getCreatedAt() { return createdAt; }
}
