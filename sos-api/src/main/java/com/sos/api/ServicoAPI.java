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
import com.sos.entities.Servico;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.ServicoService;
import com.sos.service.business.TipoServicoService;
import com.sos.service.util.exception.ServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

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
    private final String PARAM_ID_TIPO_SERVICO = "id_tipo_servico";
    private final String PARAM_ID_PRESTADOR = "id_prestador";
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response pesquisarServicos(@QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			List<Servico> servicos = servicoService.findAllSortByDescricao();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.ID_REFERENCES);
			xStream.alias("servicos", Servico.class);
			
			retorno = xStream.toXML(servicos);
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
    public Response cadastrarServico(String json, @QueryParam("callback") String callback){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Servico servico = new Servico();
    		configurarServico(servico, jsonObject);
    		
    		servicoService.create(servico);
    		response = CallBackUtil.setResponseOK("Serviço Criado Com sucesso.", MediaType.APPLICATION_JSON, callback);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
			e.printStackTrace();
		}
    	return response;
    }
    
    @DELETE
    @Path("{servico}")
    public Response removerServico(@PathParam("servico") Long codigo, @QueryParam("callback") String callback){
    	Response response = null;
    	try {
			Servico servico = servicoService.findByCodigo(codigo);
			if(servico != null){
				servicoService.delete(servico);
				response = CallBackUtil.setResponseOK("Serviço Removido com Sucesso", MediaType.APPLICATION_JSON, callback);
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
    @Path("{servico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarServico(@PathParam("servico") Long codigo, String json, @QueryParam("callback") String callback){
    	Response response = null;
    	try{
    		Servico servico = servicoService.findByCodigo(codigo);
    		if(servico != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarServico(servico, jsonObject);
    			
    			servicoService.update(servico);
    			response = CallBackUtil.setResponseOK("Serviço Editado com Sucesso", MediaType.APPLICATION_JSON, callback);
    		}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
    		e.printStackTrace();
		}
    	return response;
    }
    
    private void configurarServico(Servico servico, JSONObject jsonObject) throws JSONException, ServiceException{
    	servico.setDescricao(jsonObject.getString(PARAM_DESCRICAO));
		try{
			servico.setValor(jsonObject.getDouble(PARAM_VALOR));
		}catch(JSONException e){
			servico.setValor(null);
		}
		servico.setTipoServico(tipoServicoService.findByCodigo(jsonObject.getLong(PARAM_ID_TIPO_SERVICO)));
		servico.setPrestador(prestadorService.findByCodigo(jsonObject.getLong(PARAM_ID_PRESTADOR)));
    }
}