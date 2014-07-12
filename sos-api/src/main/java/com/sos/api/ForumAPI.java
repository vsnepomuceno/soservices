package com.sos.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sos.api.util.CallBackUtil;
import com.sos.api.util.ForumExclusionStrategy;
import com.sos.entities.Post;
import com.sos.entities.Prestador;
import com.sos.entities.Servico;
import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.business.ForumService;
import com.sos.service.business.PostService;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.ServicoService;
import com.sos.service.business.TokenGeneratorService;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.util.exception.ServiceException;

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
    @Autowired
    private UsuarioSevice usuarioSevice;
    
    private final String BLANK_RETURN = "{}";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_RESPOSTA = "resposta";    
    private final String PARAM_PERGUNTA = "pergunta";    
    private final String PARAM_EMAIL_USUARIO = "email_usuario";    
   
    @POST
    @Path("servico/{idServico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postarForum(@PathParam("idServico") Long id, String json, @HeaderParam("token-api") String tokenApi) {
    	Response response = null;
		try {
			Servico servico = servicoService.findByCodigo(id);
			if(servico != null){
				Post post = new Post();				
				JSONObject jsonObject = new JSONObject(json);
				configurarPost(post, servico, jsonObject);
				post.setResposta("");
				
				Token token = tokenGeneratorService.findByApiKeyAndUsuarioId(tokenApi, post.getUsuario().getId());
				if(token != null){
					
					postService.create(post);
					response = CallBackUtil.setResponseOK("Post criado com Sucesso", MediaType.APPLICATION_JSON);
				}else{
					response = CallBackUtil.setResponseError(Status.UNAUTHORIZED.getStatusCode(), 
							"Você não tem permissão para postar neste fórum.");
				}
			}else{
				response = CallBackUtil.setResponseError(Status.UNAUTHORIZED.getStatusCode(), "Serviço não encontrado.");
			}
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    @GET
    @Path("servico/{idServico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarForumPorServico(@PathParam("idServico") Long id) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
    	try {
    		Servico servico = servicoService.findByCodigo(id);
    		if(servico != null){
    			Gson gson = new GsonBuilder().setExclusionStrategies(new ForumExclusionStrategy()).create();
    			retorno = gson.toJson(servico.getForum());			
    			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
    		}else{
    			response = CallBackUtil.setResponseError(Status.UNAUTHORIZED.getStatusCode(), "Serviço não encontrado.");	
    		}
    	} catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    		e.printStackTrace();
    	}
    	return response;
    }
    
    @PUT
    @Path("resposta")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response respostaPost(String json, @QueryParam("id") Long id, @HeaderParam("token-api") String tokenApi){
    	Response response = null;
    	try{
    		JSONObject jsonObject = new JSONObject(json);
    		String email = jsonObject.getString(PARAM_EMAIL);
    		String resposta = jsonObject.getString(PARAM_RESPOSTA);
    		
    		Prestador prestador = prestadorService.findByEmail(email);
    		if(prestador != null){    			
    			Token token = tokenGeneratorService.findByApiKeyAndUsuarioId(tokenApi, prestador.getId());
				if(token != null){
					Post post = postService.findByCodigo(id);
					post.setResposta(resposta);
					postService.update(post);
					response = CallBackUtil.setResponseOK("Resposta enviada com Sucesso.", MediaType.APPLICATION_JSON);
				}else{
					response = CallBackUtil.setResponseError(Status.UNAUTHORIZED.getStatusCode(), 
							"Você não tem permissão para editar prestador de serviço.");
				}
    		}else{
    			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), "Prestador não encontrado.");
    		}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    		e.printStackTrace();
		}
    	return response;
    }
    
    private void configurarPost(Post post, Servico servico, JSONObject jsonObject){
		try{
			post.setMensagem(jsonObject.getString(PARAM_PERGUNTA));
			post.setForum(servico.getForum());
			
			String emailUsuario = jsonObject.getString(PARAM_EMAIL_USUARIO);
			Usuario usuario = usuarioSevice.findByEmail(emailUsuario);
			post.setUsuario(usuario);
		}catch(JSONException | ServiceException e){
			if(e instanceof ServiceException){
				e.printStackTrace();
			}
		}
    }
}