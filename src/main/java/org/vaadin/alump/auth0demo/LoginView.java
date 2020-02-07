package org.vaadin.alump.auth0demo;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.json.auth.UserInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import java.util.Properties;

/**
 * Created by alump on 05/07/2017.
 */
@Route("login")
public class LoginView extends VerticalLayout implements AfterNavigationObserver {

    private ProgressBar spinner;
    private H1 errorLabel;
    private H2 errorDescLabel;

    private AuthenticationController authenticationController;

    public LoginView() {
        VerticalLayout layout = new VerticalLayout();
        //layout.addClassName("wait-for-login");
        layout.setMargin(true);
        layout.setSpacing(true);

        spinner = new ProgressBar();
        spinner.setIndeterminate(true);
        spinner.setWidth("200px");
        spinner.setHeight("200px");
        layout.add(spinner);
        //layout.setComponentAlignment(spinner, Alignment.MIDDLE_CENTER);

        errorLabel = new H1("Something went wrong :(");
        errorLabel.setWidth("100%");
        errorLabel.setVisible(false);

        errorDescLabel = new H2("n/a");
        errorDescLabel.setWidth("100%");
        errorDescLabel.setVisible(false);

        layout.add(errorLabel, errorDescLabel);
    }

    private void checkAuthentication(VaadinRequest request) {
        VaadinServletRequest servletRequest = (VaadinServletRequest) request;

        try {

            Tokens tokens = getAuthenticationController().handle(servletRequest.getHttpServletRequest());
            UserInfo userInfo = Auth0Util.resolveUser(tokens.getAccessToken());

            Auth0Session.getCurrent().setAuth0Info(tokens, userInfo);
            UI.getCurrent().navigate(MainView.VIEW_NAME);

        } catch(IdentityVerificationException e) {
            if("a0.missing_authorization_code".equals(e.getCode())) {
                String url = getAuthenticationController().buildAuthorizeUrl(servletRequest, Auth0Util.getLoginURL()).build();
                UI.getCurrent().getPage().setLocation(url);
            } else {
                showError(e);
            }
        } catch(Exception e) {
            showError(e);
        }
    }

    private void showError(Throwable t) {
        spinner.setVisible(false);
        errorLabel.setVisible(true);
        errorDescLabel.setVisible(true);
        errorDescLabel.getElement().setText(t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        checkAuthentication(VaadinService.getCurrentRequest());
    }

    protected AuthenticationController getAuthenticationController() {
        if(authenticationController == null) {
            Properties properties = Auth0Util.getAuth0Properties();
            authenticationController = AuthenticationController.newBuilder(
                    properties.getProperty("auth0.domain"),
                    properties.getProperty("auth0.clientId"),
                    properties.getProperty("auth0.clientSecret")).build();
        }

        return authenticationController;
    }
}
