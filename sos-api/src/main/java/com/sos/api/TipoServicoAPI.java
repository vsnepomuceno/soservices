package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String pesquisarTiposServicos(@QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
		try {
			List<TipoServico> tiposServicos = tipoServicoService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.ID_REFERENCES);
			xStream.alias("tiposServicos", TipoServico.class);
			
			retorno = xStream.toXML(tiposServicos);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return CallBackUtil.checarCallback(callback, retorno);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String cadastrarTipoServico(String json){
    	String retorno = BLANK_RETURN;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		TipoServico tipoServico = new TipoServico();
    		configurarTipoServico(tipoServico, jsonObject);
    		
			tipoServicoService.create(tipoServico);
		} catch (ServiceException e) {
			//TODO Saber qual mensagem passar para o usuário
		} catch (JSONException e) {
			//TODO Saber qual mensagem passar para o usuário
		} catch (Exception e) {
			//TODO Saber qual mensagem passar para o usuário
		}
    	return retorno;
    }
    
    @DELETE
    @Path("{tipo-servico}")
    public void removerTipoServico(@PathParam("tipo-servico") Long codigo){
    	try {
			TipoServico tipoServico = tipoServicoService.findByCodigo(codigo);
			if(tipoServico != null){
				tipoServicoService.delete(tipoServico);
			}else{
				//TODO Saber qual mensagem passar para o usuário
			}
		} catch (ServiceException e) {
			//TODO Saber qual mensagem passar para o usuário
		} catch (Exception e) {
			//TODO Saber qual mensagem passar para o usuário
		}
    }
    
    @PUT
    @Path("{tipo-servico}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editarTipoServico(@PathParam("tipo-servico") Long codigo, String json){
    	String retorno = BLANK_RETURN;
    	try{
    		TipoServico tipoServico = tipoServicoService.findByCodigo(codigo);
    		if(tipoServico != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarTipoServico(tipoServico, jsonObject);
    			
    			tipoServicoService.update(tipoServico);
    		}else{
    			//TODO Saber qual mensagem passar para o usuário
    		}
    	}catch(ServiceException e){
    		//TODO Saber qual mensagem passar para o usuário
    	}catch (Exception e) {
    		//TODO Saber qual mensagem passar para o usuário
		}
    	return retorno;
    }
    
    private void configurarTipoServico(TipoServico tipoServico, JSONObject jsonObject) throws JSONException{
    	tipoServico.setNome(jsonObject.getString(PARAM_NOME));
		tipoServico.setValorado(jsonObject.getBoolean(PARAM_VALORADO));
    }
}