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

import com.sos.api.util.CallBackUtil;
import com.sos.entities.TipoServico;
import com.sos.service.business.TipoServicoService;
import com.sos.service.util.exception.ServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@Path("tipo-servico")
@Component
public class TipoServicoAPI {

    @Autowired
    private TipoServicoService tipoServicoService;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_NOME = "nome";
    private final String PARAM_VALORADO = "valorado";
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response pesquisarTiposServicos(@QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			List<TipoServico> tiposServicos = tipoServicoService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.ID_REFERENCES);
			xStream.alias("tiposServicos", TipoServico.class);
			
			retorno = xStream.toXML(tiposServicos);
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON, callback);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
			e.printStackTrace();
		}
        return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarTipoServico(String json, @QueryParam("callback") String callback){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		TipoServico tipoServico = new TipoServico();
    		configurarTipoServico(tipoServico, jsonObject);
    		
			tipoServicoService.create(tipoServico);
			response = CallBackUtil.setResponseOK("Tipo de Serviço Criado Com sucesso.", MediaType.APPLICATION_JSON, callback);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
			e.printStackTrace();
		}
    	return response;
    }
    
    @DELETE
    @Path("{tipo-servico}")
    public Response removerTipoServico(@PathParam("tipo-servico") Long codigo, @QueryParam("callback") String callback){
    	Response response = null;
    	try {
			TipoServico tipoServico = tipoServicoService.findByCodigo(codigo);
			if(tipoServico != null){
				tipoServicoService.delete(tipoServico);
				response = CallBackUtil.setResponseOK("Tipo de Serviço Removido com Sucesso", MediaType.APPLICATION_JSON, callback);
			}
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
			e.printStackTrace();
		}
    	return response;
    }
    
    @PUT
    @Path("{tipo-servico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarTipoServico(@PathParam("tipo-servico") Long codigo, String json, @QueryParam("callback") String callback){
    	Response response = null;
    	try{
    		TipoServico tipoServico = tipoServicoService.findByCodigo(codigo);
    		if(tipoServico != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarTipoServico(tipoServico, jsonObject);
    			
    			tipoServicoService.update(tipoServico);
    			response = CallBackUtil.setResponseOK("Tipo de Serviço Editado com Sucesso", MediaType.APPLICATION_JSON, callback);
    		}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
    		e.printStackTrace();
		}
    	return response;
    }
    
    private void configurarTipoServico(TipoServico tipoServico, JSONObject jsonObject) throws JSONException{
    	tipoServico.setNome(jsonObject.getString(PARAM_NOME));
		tipoServico.setValorado(jsonObject.getBoolean(PARAM_VALORADO));
    }
}