package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import com.sos.api.util.ServicoExclusionStrategy;
import com.sos.entities.Servico;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.ServicoService;
import com.sos.service.business.TipoServicoService;
import com.sos.service.util.exception.ServiceException;

@Path("servico")
@Component
public class ServicoAPI {

    @Autowired
    private ServicoService servicoService;
    @Autowired
    private TipoServicoService tipoServicoService;
    @Autowired
    private PrestadorService  prestadorService;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_VALOR = "valor";
    private final String PARAM_DESCRICAO = "descricao";
    private final String PARAM_NOME_TIPO_SERVICO = "nome_tipo_servico";
    private final String PARAM_EMAIL_PRESTADOR = "usuario_email";
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response pesquisarServicos() {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			List<Servico> servicos = servicoService.findAllSortByDescricao();
			
			Gson gson = new GsonBuilder().setExclusionStrategies(new ServicoExclusionStrategy()).create();
    		retorno = gson.toJson(servicos);			
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    @GET
    @Path("email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarServicosPorPrestador(@QueryParam("email") String email) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			List<Servico> servicos = servicoService.findByPrestadorEmail(email);
			
			Gson gson = new GsonBuilder().setExclusionStrategies(new ServicoExclusionStrategy()).create();
    		retorno = gson.toJson(servicos);			
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarServico(String json){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Servico servico = new Servico();
    		configurarServico(servico, jsonObject);
    		
    		servicoService.create(servico);
    		response = CallBackUtil.setResponseOK("Serviço Criado Com sucesso.", MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
    	return response;
    }
    
    @DELETE
    @Path("{servico}")
    public Response removerServico(@PathParam("servico") Long codigo){
    	Response response = null;
    	try {
			Servico servico = servicoService.findByCodigo(codigo);
			if(servico != null){
				servicoService.delete(servico);
				response = CallBackUtil.setResponseOK("Serviço Removido com Sucesso", MediaType.APPLICATION_JSON);
			}
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
    	return response;
    }
    
    @PUT
    @Path("{servico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarServico(@PathParam("servico") Long codigo, String json){
    	Response response = null;
    	try{
    		Servico servico = servicoService.findByCodigo(codigo);
    		if(servico != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarServico(servico, jsonObject);
    			
    			servicoService.update(servico);
    			response = CallBackUtil.setResponseOK("Serviço Editado com Sucesso", MediaType.APPLICATION_JSON);
    		}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    		e.printStackTrace();
		}
    	return response;
    }
    
    private void configurarServico(Servico servico, JSONObject jsonObject) throws JSONException, ServiceException{
    	servico.setDescricao(jsonObject.getString(PARAM_DESCRICAO));
		try{
			servico.setValor(Double.parseDouble(jsonObject.getString(PARAM_VALOR)));
		}catch(JSONException e){
			servico.setValor(null);
		}
		servico.setTipoServico(tipoServicoService.findByNome(jsonObject.getString(PARAM_NOME_TIPO_SERVICO)));
		servico.setPrestador(prestadorService.findByEmail(jsonObject.getString(PARAM_EMAIL_PRESTADOR)));
    }
}