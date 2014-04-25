package com.sos.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class MyApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public MyApplication () {
        register(RequestContextFilter.class);
        register(JerseyResource.class);
        register(SpringSingletonResource.class);
        register(TipoServicoAPI.class);
        register(CustomExceptionMapper.class);
    }
}