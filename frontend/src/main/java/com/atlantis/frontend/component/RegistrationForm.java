package com.atlantis.frontend.component;

import com.atlantis.frontend.event.RegistrationEvent;
import com.atlantis.frontend.model.RegistrationRq;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.apache.commons.lang3.StringUtils;

public class RegistrationForm extends FormLayout {

    private final TextField username = new TextField("Имя пользователя");
    private final EmailField email = new EmailField("Электронная почта");
    private final PasswordField password = new PasswordField("Пароль");
    private final PasswordField confirmPassword = new PasswordField("Подтвердите пароль");
    private final Span error = new Span();
    private final Button submit = new Button("Зарегистрироваться");

    private final Binder<RegistrationRq> binder = new BeanValidationBinder<>(RegistrationRq.class);
    private final RegistrationRq rq = new RegistrationRq();

    public RegistrationForm() {
        buildFields();
        buildValidation();
        buildLayout();
        addListeners();
    }

    public void addRegistrationListener(ComponentEventListener<RegistrationEvent> listener) {
        addListener(RegistrationEvent.class, listener);
    }

    public void setError(String message) {
        error.setText(message);
        error.setVisible(true);
    }

    private void buildFields() {
        username.setPlaceholder("Имя пользователя");
        username.setRequired(true);
        username.setWidthFull();

        email.setPlaceholder("Электронная почта");
        email.setRequired(true);
        email.setWidthFull();

        password.setPlaceholder("Пароль");
        password.setRequired(true);
        password.setWidthFull();

        confirmPassword.setPlaceholder("Подтвердите пароль");
        confirmPassword.setRequired(true);
        confirmPassword.setWidthFull();

        error.getStyle()
                .set("color", "var(--lumo-error-color)")
                .set("font-size", "var(--lumo-font-size-s)");
        error.setVisible(false);

        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setWidthFull();
    }

    private void buildValidation() {
        binder.forField(username)
                .asRequired("Имя пользователя обязательно")
                .bind(RegistrationRq::getUsername, RegistrationRq::setUsername);

        binder.forField(email)
                .asRequired("Электронная почта обязательна")
                .bind(RegistrationRq::getEmail, RegistrationRq::setEmail);

        binder.forField(password)
                .asRequired("Пароль обязателен")
                .withValidator(pass -> pass.length() >= 8, "Пароль должен иметь минимум 8 символов")
                .bind(RegistrationRq::getPassword, RegistrationRq::setPassword);

        binder.forField(confirmPassword)
                .asRequired("Подтверждение пароля обязательно")
                .withValidator(confirmPass -> StringUtils.equals(confirmPass, password.getValue()), "Пароли не совпадают")
                .bind(RegistrationRq::getConfirmPassword, RegistrationRq::setConfirmPassword);

        binder.setBean(rq);
    }

    private void buildLayout() {
        setWidth("400px");
        setMaxWidth("100%");

        add(username, email, password, confirmPassword, error, submit);

        setColspan(error, 2);
        setColspan(submit, 2);
    }

    private void addListeners() {
        submit.addClickListener(click -> {
            try {
                binder.writeBean(rq);
                fireEvent(new RegistrationEvent(this, rq));
            } catch (ValidationException e) {
                setError("Введите корректные данные");
            }
        });

        // Скрываем ошибку при изменении любого поля
        username.addValueChangeListener(e -> hideError());
        email.addValueChangeListener(e -> hideError());
        password.addValueChangeListener(e -> hideError());
        confirmPassword.addValueChangeListener(e -> hideError());
    }

    private void hideError() {
        error.setVisible(false);
    }
}