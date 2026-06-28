package com.atlantis.backend.security.config;

import com.atlantis.backend.security.entity.Role;
import com.atlantis.backend.security.service.SecurityUserService;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import static com.atlantis.backend.constant.URLConstant.ADMIN;
import static com.atlantis.backend.constant.URLConstant.FAVICON;
import static com.atlantis.backend.constant.URLConstant.LOGIN;
import static com.atlantis.backend.constant.URLConstant.LOGOUT;
import static com.atlantis.backend.constant.URLConstant.REGISTER;
import static com.atlantis.backend.constant.URLConstant.MAIN;
import static com.atlantis.backend.constant.URLConstant.VAADIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final int BCRYPT_STRENGTH = 12;

    private final SecurityUserService securityUserService;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityContext(securityContext -> securityContext
                        .requireExplicitSave(false)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(MAIN, LOGIN, LOGOUT, REGISTER).permitAll()
                        .requestMatchers(VAADIN, FAVICON).permitAll()
                        .requestMatchers(ADMIN).hasRole(Role.ADMIN.name())
                )
                .formLogin(formLogin -> formLogin
                        .loginPage(LOGIN)
                        .defaultSuccessUrl(MAIN, true)
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN))
                )
                .userDetailsService(securityUserService)
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> configurer
                        .loginView(LOGIN, MAIN)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }
}