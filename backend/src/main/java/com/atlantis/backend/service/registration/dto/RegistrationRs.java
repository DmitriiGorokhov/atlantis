package com.atlantis.backend.service.registration.dto;

import com.atlantis.backend.database.entity.User;

public record RegistrationRs(
        boolean success,
        String errorMessage,
        User user
) {
    public static RegistrationRs success(User user) {
        return new RegistrationRs(true, null, user);
    }

    public static RegistrationRs error(String errorMessage) {
        return new RegistrationRs(false, errorMessage, null);
    }
}