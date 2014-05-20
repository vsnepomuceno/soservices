package com.sos.api;

import java.util.List;
import java.util.Set;

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
import com.sos.entities.Endereco;
import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.TipoServicoService;
import com.sos.service.business.util.FiltroPrestadores;
import com.sos.service.util.exception.ServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@Path("prestador")
@Component
public class PrestadorAPI {

    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private TipoServicoService tipoServicoService;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_NOME = "nome";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_SENHA = "senha";
    private final String PARAM_CPF = "cpf";
    private final String PARAM_LOGRADOURO = "logradouro";
    private final String PARAM_NUMERO = "numero";
    private final String PARAM_COMPLEMENTO = "complemento";
    private final String PARAM_CEP = "cep";
    private final String PARAM_TELEFONE = "telefone";
    private final String PARAM_CIDADE = "cidade";
    private final String PARAM_ESTADO = "estado";

    private final String PARAM_QUERY = "query";
    private final String PARAM_DISTANCIA = "distancia";
    private final String PARAM_LATITUDE = "latitude";
    private final String PARAM_LONGITUDE = "longitude";
    private final String PARAM_TIPO_SERVICO_ID = "tipo_servico_id";
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response pesquisarPrestadores(@QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			List<Prestador> prestadores = prestadorService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.ID_REFERENCES);
			xStream.alias("prestadores", Prestador.class);
			xStream.omitField(Set.class, "credenciais");
			
			retorno = xStream.toXML(prestadores);
			response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON, callback);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
			e.printStackTrace();
		}
		return response;
    }
    
    @Path("query")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarPrestadoresPorTipoServico(String json, @QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
    	try {
    		List<Prestador> prestadores = prestadorService.findByFiltroPrestadores(configurarFiltroPrestadores(new JSONObject(json)));
    		
    		XStream xStream = new XStream(new JettisonMappedXmlDriver());
    		xStream.setMode(XStream.ID_REFERENCES);
    		xStream.alias("prestadores", Prestador.class);
    		xStream.omitField(Set.class, "credenciais");
    		
    		retorno = xStream.toXML(prestadores);
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
    public Response cadastrarPrestador(String json, @QueryParam("callback") String callback){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Prestador prestador = new Prestador();
    		configurarPrestador(prestador, jsonObject);
    		
			prestadorService.create(prestador);
			response = CallBackUtil.setResponseOK("Prestador Criado Com sucesso.", MediaType.APPLICATION_JSON, callback);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
			e.printStackTrace();
		}
    	return response;
    }
    
    @DELETE
    @Path("{prestador}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removerPrestador(@PathParam("prestador") Long codigo, @QueryParam("callback") String callback){
    	Response response = null;
    	try {
			Prestador prestador = prestadorService.findByCodigo(codigo);
			if(prestador != null){
				prestadorService.delete(prestador);
				response = CallBackUtil.setResponseOK("Prestador Removido com Sucesso", MediaType.APPLICATION_JSON, callback);
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
    @Path("{prestador}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarPrestador(@PathParam("prestador") Long codigo, String json, @QueryParam("callback") String callback){
    	Response response = null;
    	try{
    		Prestador prestador = prestadorService.findByCodigo(codigo);
    		if(prestador != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarPrestador(prestador, jsonObject);
    			
    			prestadorService.update(prestador);
    			response = CallBackUtil.setResponseOK("Prestador Editado com Sucesso", MediaType.APPLICATION_JSON, callback);
    		}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), callback);
    		e.printStackTrace();
		}
    	return response;
    }
    
    private void configurarPrestador(Prestador prestador, JSONObject jsonObject) throws JSONException{
    	prestador.setNome(jsonObject.getString(PARAM_NOME));
		prestador.setEmail(jsonObject.getString(PARAM_EMAIL));
		prestador.setSenha(jsonObject.getString(PARAM_SENHA));
		prestador.setCpf(jsonObject.getString(PARAM_CPF));
		prestador.setTelefone(jsonObject.getString(PARAM_TELEFONE));

		Endereco endereco = null;
		if(prestador.getEndereco() == null){
			endereco = new Endereco();
		}else{
			endereco = prestador.getEndereco();
		}
		endereco.setLogradouro(jsonObject.getString(PARAM_LOGRADOURO));
		endereco.setNumero(jsonObject.getInt(PARAM_NUMERO));
		try{
			endereco.setComplemento(jsonObject.getString(PARAM_COMPLEMENTO));
		}catch(JSONException e){
			endereco.setComplemento(null);
		}
		endereco.setCep(jsonObject.getString(PARAM_CEP));
		endereco.setCidade(jsonObject.getString(PARAM_CIDADE));
		endereco.setEstado(jsonObject.getString(PARAM_ESTADO));
		prestador.setEndereco(endereco);
    }
    
    private FiltroPrestadores configurarFiltroPrestadores(JSONObject jsonObject) throws ServiceException, JSONException{
    	JSONObject jsonFilter = jsonObject.getJSONObject("query");
    	
    	long codigoTipoServico = 0;
    	TipoServico tipoServico = null;
    	try{
    		codigoTipoServico = jsonFilter.getLong(PARAM_TIPO_SERVICO_ID);
    		tipoServico = tipoServicoService.findByCodigo(codigoTipoServico); 
    	}catch(JSONException e){
    	}
    	
    	double distancia = 0.0;
    	try{
    		distancia = jsonFilter.getDouble(PARAM_DISTANCIA);
    	}catch(JSONException e){
    	}
    	
    	double latitude = 0.0;
    	try{
    		latitude = jsonFilter.getDouble(PARAM_LATITUDE);
    	}catch(JSONException e){
    	}
    	
    	double longitude = 0.0;
    	try{
    		longitude = jsonFilter.getDouble(PARAM_LONGITUDE);
    	}catch(JSONException e){
    	}
    	
    	return new FiltroPrestadores(tipoServico, latitude, longitude, distancia);
    }
}    