package com.atlantis.frontend.component;

import com.atlantis.frontend.constant.RouteConstant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationNavigation extends HorizontalLayout {

    private final AuthenticationContext authenticationContext;

    public AuthenticationNavigation(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        buildUI();
        update();
    }

    private void buildUI() {
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
    }

    private void update() {
        removeAll();

        if (authenticationContext.isAuthenticated()) {
            renderAuthenticatedUI();
        } else {
            renderUnauthenticatedUI();
        }
    }

    private void renderAuthenticatedUI() {
        String username = authenticationContext
                .getAuthenticatedUser(UserDetails.class)
                .map(UserDetails::getUsername)
                .orElse("Пользователь");

        Span usernameSpan = new Span(username);
        usernameSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1em");

        Button logoutButton = new Button("Выйти", click -> getUI().ifPresent(ui -> ui.navigate(RouteConstant.LOGOUT)));
        logoutButton.getStyle().set("color", "#dc3545");

        add(usernameSpan, logoutButton);
    }

    private void renderUnauthenticatedUI() {
        Button loginButton = new Button("Войти", click -> getUI().ifPresent(ui -> ui.navigate(RouteConstant.LOGIN)));
        Button registrationButton = new Button("Зарегистрироваться", click -> getUI().ifPresent(ui -> ui.navigate(RouteConstant.REGISTRATION)));

        add(loginButton, registrationButton);
    }
}