package com.atlantis.backend.service.registration;

import com.atlantis.backend.database.entity.Role;
import com.atlantis.backend.database.entity.User;
import com.atlantis.backend.database.repository.RoleRepository;
import com.atlantis.backend.database.repository.UserRepository;
import com.atlantis.backend.service.registration.dto.RegistrationRq;
import com.atlantis.backend.service.registration.dto.RegistrationRs;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationRs register(RegistrationRq rq) {
        String username = rq.username();
        if (userRepository.findByUsername(username).isPresent()) {
            return RegistrationRs.error("Имя пользователя уже занято");
        }

        String email = rq.email();
        if (userRepository.findByEmail(email).isPresent()) {
            return RegistrationRs.error("Электронная почта уже занята");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Роль ROLE_USER не найдена"));

        User user = new User()
                .setUsername(username)
                .setEmail(email)
                .setPasswordHash(passwordEncoder.encode(rq.password()))
                .setRole(role);
        userRepository.save(user);

        return RegistrationRs.success(user);
    }
}