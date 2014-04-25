package com.sos.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sos.entities.EPapel;
import com.sos.entities.Papel;
import com.sos.service.business.PapelService;
import com.sos.service.util.exception.ServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Jersey2 Spring integration example.
 * Demonstrate how to use Spring managed JAX-RS resource class with request scope (+ Spring bean DI).
 *
 * @author Marko Asplund (marko.asplund at gmail.com)
 */
@Path("spring-hello")
@Component
public class SpringRequestResource {

    @Autowired
    private GreetingService greetingService;
    
    @Autowired
    private PapelService papelService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHello() {
    	Papel papel = new Papel();
    	papel.setId((long) 1);
    	papel.setPapel("ROLE_ADMIN");
    	
    	XStream xStream = new XStream(new JettisonMappedXmlDriver());
    	xStream.setMode(XStream.NO_REFERENCES);
    	xStream.alias("papel", Papel.class);
    	
    	try {
			papelService.findByPapel(EPapel.ROLE_ADMIN.getPapel());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
        return xStream.toXML(papel);
//        return greetingService.greet("world");
    }
}
