package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
import com.sos.api.util.AvaliacaoExclusionStrategy;
import com.sos.api.util.CallBackUtil;
import com.sos.entities.Avaliacao;
import com.sos.entities.Prestador;
import com.sos.entities.TipoAvaliacao;
import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.business.AvaliacaoService;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.TokenGeneratorService;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.util.exception.ServiceException;

@Path("avaliacao")
@Component
public class AvaliacaoAPI {

    @Autowired
    private PrestadorService  prestadorService;
    @Autowired
    private UsuarioSevice  usuarioService;
    @Autowired
    private AvaliacaoService  avaliacaoService;
    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_REPLICA = "replica";    

    private final String PARAM_NOTA = "nota";    
    private final String PARAM_DEPOIMENTO = "depoimento";    
    private final String PARAM_USUARIO_ID = "usuario_id";    
    private final String PARAM_USUARIO_AVALIADOR_ID = "usuario_avaliador_id";    
       
    @GET
    @Path("email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarAvaliacoesPorPrestador(@QueryParam("email") String email) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			Prestador prestador = prestadorService.findByEmail(email);
			
			List<Avaliacao> avaliacoes = avaliacaoService.findByUsuarioId(prestador.getId());
			
			Gson gson = new GsonBuilder().setExclusionStrategies(new AvaliacaoExclusionStrategy()).create();
    		retorno = gson.toJson(avaliacoes);			
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    @PUT
    @Path("replica")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response replicaAvaliacao(String json, @QueryParam("id") Long id, @HeaderParam("token-api") String tokenApi){
    	Response response = null;
    	try{
    		JSONObject jsonObject = new JSONObject(json);
    		String email = jsonObject.getString(PARAM_EMAIL);
    		String replica = jsonObject.getString(PARAM_REPLICA);
    		
    		Prestador prestador = prestadorService.findByEmail(email);
    		if(prestador != null){    			
    			Token token = tokenGeneratorService.findByApiKeyAndUsuarioId(tokenApi, prestador.getId());
				if(token != null){
					Avaliacao avaliacao = avaliacaoService.findByCodigo(id);
					avaliacao.setReplica(replica);
					avaliacaoService.update(avaliacao);
					response = CallBackUtil.setResponseOK("Replica enviada com Sucesso.", MediaType.APPLICATION_JSON);
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
    
    @POST
    @Path("avaliar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response avaliarPrestador(String json, @HeaderParam("token-api") String tokenApi){
    	Response response = null;
    	try{
    		JSONObject jsonObject = new JSONObject(json);
    		Avaliacao avaliacao = new Avaliacao();
    		avaliacao.setReplica("");
    		configurarAvaliacao(avaliacao, jsonObject);
    		
    		Token token = tokenGeneratorService.findByApiKeyAndUsuarioId(tokenApi, avaliacao.getUsuario().getId());
			if(token != null){
				avaliacaoService.create(avaliacao);
				response = CallBackUtil.setResponseOK("Avaliação realizada com sucesso.", MediaType.APPLICATION_JSON);
			}else{
				response = CallBackUtil.setResponseError(Status.UNAUTHORIZED.getStatusCode(), 
						"Você não tem permissão para avaliar o serviço do prestador.");
			}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    		e.printStackTrace();
    	}
    	return response;
    }
    
    private void configurarAvaliacao(Avaliacao avaliacao, JSONObject jsonObject){
    	Integer nota = null;
    	try {
			nota = jsonObject.getInt(PARAM_NOTA);
		} catch (Exception e) {
		}
    	
    	String depoimento = null;
    	try {
			depoimento = jsonObject.getString(PARAM_DEPOIMENTO);
		} catch (JSONException e) {
		}
    	
    	Long usuarioId = null;
    	Usuario usuario = null;
    	try {
    		usuarioId = jsonObject.getLong(PARAM_USUARIO_ID);
    		usuario = usuarioService.findByCodigo(usuarioId);
    	} catch (JSONException | ServiceException e) {
		}
    	
    	Long usuarioAvaliadorId = null;
    	Usuario usuarioAvaliador = null;
    	try {
    		usuarioAvaliadorId = jsonObject.getLong(PARAM_USUARIO_AVALIADOR_ID);
    		usuarioAvaliador = usuarioService.findByCodigo(usuarioAvaliadorId);
    	} catch (JSONException | ServiceException e) {
		}
    	
    	avaliacao.setDepoimento(depoimento);
    	avaliacao.setNota(nota);
    	avaliacao.setTipoAvaliacao(TipoAvaliacao.USUARIO);
    	avaliacao.setUsuario(usuario);
    	avaliacao.setUsuarioAvaliador(usuarioAvaliador);
    }
}