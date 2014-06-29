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

import com.sos.api.util.CallBackUtil;
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
    @Path("email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarAvaliacoesPorPrestador(@QueryParam("email") String email) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			/*Prestador prestador = prestadorService.findByEmail(email);
			
			List<Avaliacao> avaliacoes = avaliacaoService.findByUsuarioId(prestador.getId());
			
			Gson gson = new GsonBuilder().setExclusionStrategies(new AvaliacaoExclusionStrategy()).create();
    		retorno = gson.toJson(avaliacoes);	*/		
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    
    
}