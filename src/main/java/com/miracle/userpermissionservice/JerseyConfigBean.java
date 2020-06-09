package com.miracle.userpermissionservice;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfigBean extends ResourceConfig {

    public JerseyConfigBean()
    {
        register(RestServlet.class);
    }
}
