package org.vaadin.alump.auth0demo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

@Route("error")
public class ErrorView extends VerticalLayout implements AfterNavigationObserver {

    public ErrorView() {
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        // Just redirect to main view for now
        UI.getCurrent().navigate(MainView.VIEW_NAME);
    }
}
