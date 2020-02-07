package org.vaadin.alump.auth0demo;

import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.spring.SpringServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("vaadinServlet")
public class Auth0Servlet extends SpringServlet {

    private final ApplicationContext context;

    public Auth0Servlet(ApplicationContext context) {
        super(context, false);
        this.context = context;
    }

    @Override
    protected VaadinServletService createServletService(
            DeploymentConfiguration deploymentConfiguration)
            throws ServiceException {
        // this is needed when using a custom service URL
        Auth0Service service = new Auth0Service(this, deploymentConfiguration, this.context);
        service.init();
        return service;
    }
}
