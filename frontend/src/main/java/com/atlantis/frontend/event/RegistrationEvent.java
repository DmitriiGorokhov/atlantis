package com.atlantis.frontend.event;

import com.atlantis.frontend.component.RegistrationForm;
import com.atlantis.frontend.model.RegistrationRq;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class RegistrationEvent extends ComponentEvent<RegistrationForm> {

    private final RegistrationRq registrationRq;

    public RegistrationEvent(RegistrationForm form,
                             RegistrationRq rq) {
        super(form, false);
        registrationRq = rq;
    }
}