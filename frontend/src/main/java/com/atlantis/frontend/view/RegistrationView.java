package com.atlantis.frontend.view;

import com.atlantis.backend.service.registration.RegistrationService;
import com.atlantis.backend.service.registration.dto.RegistrationRq;
import com.atlantis.backend.service.registration.dto.RegistrationRs;
import com.atlantis.frontend.component.RegistrationForm;
import com.atlantis.frontend.constant.PageTitleConstant;
import com.atlantis.frontend.constant.RouteConstant;
import com.atlantis.frontend.mapper.RegistrationMapper;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
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

@Route(RouteConstant.REGISTRATION)
@PageTitle(PageTitleConstant.REGISTRATION)
@AnonymousAllowed
public class RegistrationView extends VerticalLayout implements BeforeEnterObserver {

    private final RegistrationForm registrationForm = new RegistrationForm();

    public RegistrationView(RegistrationService registrationService,
                            AuthenticationManager authenticationManager) {
        buildUI();
        setupListener(registrationService, authenticationManager);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
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

        add(
                new H1("🌊 Atlantis"),
                new Paragraph("Зарегистрируйтесь"),
                registrationForm
        );

        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
    }

    private void setupListener(RegistrationService registrationService,
                               AuthenticationManager authenticationManager) {
        registrationForm.addRegistrationListener(event -> {
            try {
                RegistrationRq rq = RegistrationMapper.MAPPER.toRq(event.getRegistrationRq());
                RegistrationRs rs = registrationService.register(rq);
                if (!rs.success()) {
                    throw new RuntimeException(rs.errorMessage());
                }

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        rq.username(),
                        rq.password()
                );
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                getUI().ifPresent(ui -> ui.navigate(RouteConstant.MAIN));
            } catch (Exception e) {
                registrationForm.setError(e.getMessage());
            }
        });
    }
}