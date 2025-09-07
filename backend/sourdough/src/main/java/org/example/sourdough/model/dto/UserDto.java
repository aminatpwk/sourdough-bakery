package org.example.sourdough.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank(message = "First name is required.")
    private String first_name;

    @NotBlank(message = "Last name is required.")
    private String last_name;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "E-mail is required.")
    @Email(message = "E-mail must be a valid e-mail address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
