package com.sos.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sos.api.util.CallBackUtil;
import com.sos.entities.Servico;
import com.sos.service.business.ForumService;
import com.sos.service.business.PostService;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.ServicoService;
import com.sos.service.business.TokenGeneratorService;

@Path("forum")
@Component
public class ForumAPI {

    @Autowired
    private ForumService  forumService;
    @Autowired
    private PostService  postService;
    @Autowired
    private PrestadorService  prestadorService;
    @Autowired
    private TokenGeneratorService tokenGeneratorService;
    @Autowired
    private ServicoService  servicoService;
    
    private final String BLANK_RETURN = "{}";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_REPLICA = "replica";    
   
       
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarForumPorServico(@QueryParam("id") Long id) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			
			Servico servico = servicoService.findByCodigo(id);
			
			Gson gson = new GsonBuilder().create();
    		retorno = gson.toJson(servico.getForum());			
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    
    
}