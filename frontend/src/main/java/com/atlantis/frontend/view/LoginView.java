package com.atlantis.frontend.view;

import com.atlantis.frontend.constant.PageTitleConstant;
import com.atlantis.frontend.constant.QueryParametersConstant;
import com.atlantis.frontend.constant.RouteConstant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.function.Predicate;

@Route(RouteConstant.LOGIN)
@PageTitle(PageTitleConstant.LOGIN)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView(AuthenticationManager authenticationManager) {
        buildUI();
        setupListener(authenticationManager);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey(QueryParametersConstant.ERROR)) {
            loginForm.setError(true);
        }

        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .filter(Predicate.not(auth -> auth instanceof AnonymousAuthenticationToken))
                .ifPresent(authentication -> event.forwardTo(RouteConstant.MAIN));
    }

    private void buildUI() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
        setupLoginForm();

        add(
                new H1("🌊 Atlantis"),
                new Paragraph("Войдите в свой аккаунт"),
                loginForm
        );
    }

    private void setupLoginForm() {
        LoginI18n loginI18n = new LoginI18n();

        LoginI18n.Form form = new LoginI18n.Form();
        form.setTitle("Вход");
        form.setUsername("Имя пользователя или email");
        form.setPassword("Пароль");
        form.setSubmit("Войти");
        form.setForgotPassword("Забыли пароль?");
        loginI18n.setForm(form);

        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle("Ошибка входа");
        errorMessage.setMessage("Неверное имя пользователя или пароль");
        errorMessage.setUsername("Обязательное поле");
        errorMessage.setPassword("Обязательное поле");
        loginI18n.setErrorMessage(errorMessage);

        loginForm.setI18n(loginI18n);
    }

    private void setupListener(AuthenticationManager authenticationManager) {
        loginForm.addLoginListener(event -> {
            try {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        event.getUsername(),
                        event.getPassword()
                );
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                getUI().ifPresent(ui -> ui.navigate(RouteConstant.MAIN));
            } catch (Exception e) {
                loginForm.setError(true);
            }
        });
    }
}