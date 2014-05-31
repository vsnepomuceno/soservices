package com.sos.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.sos.api.filer.SimpleCORSFilter;

public class MyApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public MyApplication () {
        register(RequestContextFilter.class);
        register(CustomExceptionMapper.class);
        register(PrestadorAPI.class);
        register(ServicoAPI.class);
        register(TipoServicoAPI.class);
        register(TokenGeneratorAPI.class);
        register(UsuarioAPI.class);
        register(SimpleCORSFilter.class);
    }
}