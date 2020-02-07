package org.vaadin.alump.auth0demo;


import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.SpringVaadinServletService;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by alump on 05/07/2017.
 */
public class Auth0Service extends SpringVaadinServletService {

    public Auth0Service(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration, ApplicationContext context)
            throws ServiceException {
        super(servlet, deploymentConfiguration, context);
    }

    @Override
    protected List<RequestHandler> createRequestHandlers()
            throws ServiceException {
        List<RequestHandler> list = super.createRequestHandlers();
        return list;
    }

    @Override
    protected VaadinSession createVaadinSession(VaadinRequest request) {
        return new Auth0Session(this);
    }
}
