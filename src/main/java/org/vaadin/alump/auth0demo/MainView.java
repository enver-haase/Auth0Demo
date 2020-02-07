package org.vaadin.alump.auth0demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

@Route(MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements AfterNavigationObserver {

    public final static String VIEW_NAME = "main";

    private final Auth0Management management;

    public MainView(Auth0Management management) {
        this.management = management;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        init();
    }

    private void init() {
        setMargin(true);
        setSpacing(true);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.setWidth("100%");

        Button login = new Button("Login");
        login.setVisible(!Auth0Session.getCurrent().isLoggedIn());
        login.addClickListener(e->login());
        Button logout = new Button("Logout");
        logout.addClickListener(e->logout());
        logout.setVisible(Auth0Session.getCurrent().isLoggedIn());
        Button tryAccess = new Button("Try access view",
                e -> UI.getCurrent().navigate(LimitedView.VIEW_NAME));
        buttons.add(login, logout, tryAccess);

        H1 hello = new H1();
        if(!Auth0Session.getCurrent().isLoggedIn()) {
            hello.getElement().setText("Please login");
        }

        add(buttons, hello);

        Auth0Session.getCurrent().getUser().ifPresent(u -> {
            hello.getElement().setText("Hey " + u.getGivenName().orElse(u.getSubject()) + "!");

            HorizontalLayout info = new HorizontalLayout();
            info.setSpacing(true);
            info.setWidth("100%");
            add(info);

            Component userinfo = createUserInfoGrid(u);
            info.add(userinfo);
            // TODO
            //userinfo.setWidth("100%");
            //info.setExpandRatio(userinfo, 1f);

//            u.getPicture().ifPresent(url -> {
////                Image image = new Image(null, new ExternalResource(url));
////                image.setWidth("200px");
////                image.setHeight("200px");
////                info.add(image);
////            });
        });

        Label mgnLabel = new Label();
        mgnLabel.setWidth("100%");
        add(mgnLabel);
        try {
            if(management.isEnabled()) {
                mgnLabel.getElement().setText("Found " + management.getUsers().size() + " users from Auth0");
            } else {
                mgnLabel.getElement().setText("Management API key not defined");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mgnLabel.getElement().setText("Failed to access management API");
            //mgnLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        }

    }

    private Component createUserInfoGrid(Auth0User user) {
        FormLayout layout = new FormLayout();

        user.getKeys().stream().filter(key -> !key.equals("clientID")).forEach(key -> {
            try {
                String value = user.getValue(key);

                H3 row = new H3(value);
                //row.setCaption(key);
                layout.add(row);
            } catch(Exception e) {
                System.err.println("Failed to read property " + key);
                e.printStackTrace();
            }
        });

        return layout;
    }

    private void login() {
        Auth0Session.getCurrent().login();
    }

    private void logout() {
        Auth0Session.getCurrent().logout();
    }
}