package com.atlantis.frontend.model;

import lombok.Data;

@Data
public class RegistrationRq {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}