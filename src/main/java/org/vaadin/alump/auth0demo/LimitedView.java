package org.vaadin.alump.auth0demo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route(LimitedView.VIEW_NAME)
public class LimitedView extends VerticalLayout {

    public static final String VIEW_NAME = "limited";

    public LimitedView() {
        super();
    }

    @PostConstruct
    protected void init() {
        Button backToMain = new Button("Back to main view", e -> UI.getCurrent().navigate(MainView.VIEW_NAME));

        H1 label = new H1("Access Granted!");


        add(backToMain, label);
    }
}
