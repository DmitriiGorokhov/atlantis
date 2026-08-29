package com.atlantis.backend.service.registration.dto;

public record RegistrationRq(
        String username,
        String email,
        String password
) {}