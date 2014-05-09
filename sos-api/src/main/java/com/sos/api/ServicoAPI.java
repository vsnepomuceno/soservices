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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String pesquisarServicos(@QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
		try {
			List<Servico> servicos = servicoService.findAllSortByDescricao();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.ID_REFERENCES);
			xStream.alias("servicos", Servico.class);
			
			retorno = xStream.toXML(servicos);
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
    public String cadastrarServico(String json){
    	String retorno = BLANK_RETURN;
    	try {
    		JSONObject jsonObject = new JSONObject(json);

    		Servico servico = new Servico();
    		configurarServico(servico, jsonObject);
    		
    		servicoService.create(servico);
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
    @Path("{servico}")
    public void removerServico(@PathParam("servico") Long codigo){
    	try {
			Servico servico = servicoService.findByCodigo(codigo);
			if(servico != null){
				servicoService.delete(servico);
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
    @Path("{servico}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editarServico(@PathParam("servico") Long codigo, String json){
    	String retorno = BLANK_RETURN;
    	try{
    		Servico servico = servicoService.findByCodigo(codigo);
    		if(servico != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarServico(servico, jsonObject);
    			
    			servicoService.update(servico);
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
    
    private void configurarServico(Servico servico, JSONObject jsonObject) throws JSONException, ServiceException{
    	servico.setDescricao(jsonObject.getString(PARAM_DESCRICAO));
		servico.setValor(jsonObject.getDouble(PARAM_VALOR));
		servico.setTipoServico(tipoServicoService.findByCodigo(jsonObject.getLong(PARAM_ID_TIPO_SERVICO)));
		servico.setPrestador(prestadorService.findByCodigo(jsonObject.getLong(PARAM_ID_PRESTADOR)));
    }
}