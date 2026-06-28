package com.atlantis.frontend.view;

import com.atlantis.frontend.component.AuthenticationNavigation;
import com.atlantis.frontend.constant.PageTitleConstant;
import com.atlantis.frontend.constant.RouteConstant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

@Route(RouteConstant.MAIN)
@PageTitle(PageTitleConstant.MAIN)
@PermitAll
@AnonymousAllowed
public class MainView extends VerticalLayout {

    private final AuthenticationNavigation authenticationNavigation;

    public MainView(AuthenticationContext authenticationContext) {
        this.authenticationNavigation = new AuthenticationNavigation(authenticationContext);
        buildUI();
    }

    private void buildUI() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        FlexLayout header = new FlexLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexLayout.JustifyContentMode.END);
        header.setAlignItems(FlexLayout.Alignment.CENTER);
        header.add(authenticationNavigation);

        VerticalLayout content = new VerticalLayout();
        content.setAlignItems(Alignment.CENTER);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.setPadding(true);
        content.setSpacing(true);

        H1 title = new H1("🌊 Atlantis");
        title.getStyle()
                .set("font-size", "4em")
                .set("font-weight", "bold")
                .set("color", "#1a237e")
                .set("margin", "0");

        H2 subtitle = new H2("Открой мир знаний");
        subtitle.getStyle()
                .set("font-size", "2em")
                .set("color", "#3949ab")
                .set("margin", "0");

        Paragraph description = new Paragraph(
                "Atlantis — это современная образовательная платформа, " +
                        "которая помогает изучать историю, математику, " +
                        "биологию и другие дисциплины. " +
                        "Мы объединяем AI-технологии, геймификацию и увлекательный контент, " +
                        "чтобы сделать обучение настоящим приключением."
        );
        description.getStyle()
                .set("font-size", "1.2em")
                .set("color", "#37474f")
                .set("max-width", "600px")
                .set("text-align", "center")
                .set("line-height", "1.8");

        Paragraph quote = new Paragraph(
                "«Знание — это сокровище, которое всегда с тобой»"
        );
        quote.getStyle()
                .set("font-style", "italic")
                .set("font-size", "1.1em")
                .set("color", "#546e7a")
                .set("margin-top", "20px");

        content.add(title, subtitle, description, quote);

        add(header, content);
        setFlexGrow(1, content);
        setAlignItems(Alignment.CENTER);
    }
}