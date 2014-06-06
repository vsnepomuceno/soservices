package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
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
import com.sos.api.util.PrestadorExclusionStrategy;
import com.sos.entities.Endereco;
import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.TipoServicoService;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.business.util.FiltroPrestadores;
import com.sos.service.util.exception.ServiceException;

@Path("prestador")
@Component
public class PrestadorAPI {

    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private TipoServicoService tipoServicoService;
    @Autowired
    private UsuarioSevice usuarioService;

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

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response pesquisarPrestadores() {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {
			List<Prestador> prestadores = prestadorService.findAllSortByName();
			Gson gson = new GsonBuilder().setExclusionStrategies(new PrestadorExclusionStrategy()).create();
    		retorno = gson.toJson(prestadores);			
    		response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    @Path("query")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisarPrestadoresPorTipoServico(@QueryParam("tipo_servico_id") String tipoServicoId, 
    		@QueryParam("longitude") String longitude, @QueryParam("latitude") String latitude, @QueryParam("distancia") String distancia) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
    	try {
    		List<Prestador> prestadores = prestadorService.findByFiltroPrestadores(configurarFiltroPrestadores(tipoServicoId, latitude, longitude, distancia));
    		
    		Gson gson = new GsonBuilder().setExclusionStrategies(new PrestadorExclusionStrategy()).create();
    		retorno = gson.toJson(prestadores);
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
    public Response pesquisarPrestadorPorEmail(@QueryParam("email") String email) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
		try {			
			Prestador prestador = prestadorService.findByEmail(email);
			Gson gson = new GsonBuilder().setExclusionStrategies(new PrestadorExclusionStrategy()).create();
    		retorno = gson.toJson(prestador);			
    		response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
		return response;
    }
    
    
    @Path("query")
    @OPTIONS
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verificarPrestadoresPorTipoServico(String json) {
    	String retorno = BLANK_RETURN;
    	Response response = null;
    	try {
    		response = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
    	} catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    		e.printStackTrace();
    	}
    	return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarPrestador(String json){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Prestador prestador = new Prestador();
    		configurarPrestador(prestador, jsonObject);
    		
			prestadorService.create(prestador);
			response = CallBackUtil.setResponseOK("Prestador Criado Com sucesso.", MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
    	return response;
    }
    
    @DELETE
    @Path("{prestador}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removerPrestador(@PathParam("prestador") Long codigo){
    	Response response = null;
    	try {
			Prestador prestador = prestadorService.findByCodigo(codigo);
			if(prestador != null){
				prestadorService.delete(prestador);
				response = CallBackUtil.setResponseOK("Prestador Removido com Sucesso", MediaType.APPLICATION_JSON);
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarPrestador( String json){
    	Response response = null;
    	try{
    		JSONObject jsonObject = new JSONObject(json);
    		String email = jsonObject.getString(PARAM_EMAIL);
    		
    		Prestador prestador = prestadorService.findByEmail(email);
    		if(prestador != null){
    			configurarPrestador(prestador, jsonObject);
    			
    			prestadorService.update(prestador);
    			response = CallBackUtil.setResponseOK("Prestador Editado com Sucesso", MediaType.APPLICATION_JSON);
    		}
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    		e.printStackTrace();
		}
    	return response;
    }
    
    private void configurarPrestador(Prestador prestador, JSONObject jsonObject) throws JSONException{
    	// Não atualizar dados do usuário apenas do prestador 
    	//prestador.setNome(jsonObject.getString(PARAM_NOME));
		//prestador.setEmail(jsonObject.getString(PARAM_EMAIL));
		//prestador.setSenha(jsonObject.getString(PARAM_SENHA));
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
    
    private FiltroPrestadores configurarFiltroPrestadores(String tipoServicoId, String strLongitude, String strLatitude, String strDistancia) throws ServiceException{
    	long codigoTipoServico = 0;
    	TipoServico tipoServico = null;
    	try{
    		codigoTipoServico = Long.valueOf(tipoServicoId);
    		tipoServico = tipoServicoService.findByCodigo(codigoTipoServico); 
    	}catch(NumberFormatException e){
    	}
    	
    	double distancia = 0.0;
    	try{
    		distancia = Double.valueOf(strDistancia);
    	}catch(NumberFormatException e){
    	}
    	
    	double latitude = 0.0;
    	try{
    		latitude = Double.valueOf(strLatitude);
    	}catch(NumberFormatException e){
    	}
    	
    	double longitude = 0.0;
    	try{
    		longitude = Double.valueOf(strLongitude);
    	}catch(NumberFormatException e){
    	}
    	return new FiltroPrestadores(tipoServico, latitude, longitude, distancia);
    }
    
    @POST
    @Path("usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuarioPrestador(String json){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Prestador usuario = new Prestador();
    		configurarUsuarioPrestador(usuario, jsonObject);
    		
    		prestadorService.create(usuario);
			response = CallBackUtil.setResponseOK("Usuario Criado Com sucesso.", MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
    	return response;
    }
	
	private void configurarUsuarioPrestador(Prestador usuario, JSONObject jsonObject) throws JSONException{
		usuario.setNome(jsonObject.getString(PARAM_NOME));
		usuario.setEmail(jsonObject.getString(PARAM_EMAIL));
		usuario.setSenha(jsonObject.getString(PARAM_SENHA));
		
		
		usuario.setCpf(jsonObject.getString(PARAM_EMAIL));
		usuario.setTelefone("00000000");
		Endereco endereco = new Endereco();		
		endereco.setLogradouro("default");
		endereco.setNumero(0);
		endereco.setComplemento("default");
		endereco.setCep("default");
		endereco.setCidade("default");
		endereco.setEstado("default");
		usuario.setEndereco(endereco);
	}
}    