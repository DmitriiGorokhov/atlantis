package com.atlantis.frontend.view;

import com.atlantis.frontend.constant.PageTitleConstant;
import com.atlantis.frontend.constant.RouteConstant;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;

@Route(RouteConstant.LOGOUT)
@PageTitle(PageTitleConstant.LOGOUT)
@PermitAll
@RequiredArgsConstructor
public class LogoutView extends Div implements BeforeEnterObserver {

    private final AuthenticationContext authenticationContext;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Закрываем UI с Push-соединениями
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.accessSynchronously(() -> {
                ui.getPushConfiguration().setPushMode(PushMode.DISABLED);
                ui.close();
            });
        }

        // Выполняем полноценный логаут
        authenticationContext.logout();
    }
}