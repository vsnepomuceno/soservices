package com.sos.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
import com.sos.api.util.TokenExclusionStrategy;
import com.sos.entities.Endereco;
import com.sos.entities.Prestador;
import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.business.PrestadorService;
import com.sos.service.business.TokenGeneratorService;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.util.MessageUtil;
import com.sos.service.util.exception.ServiceException;

@Path("token")
@Component
public class TokenGeneratorAPI {

    @Autowired
    private TokenGeneratorService tokenGeneratorService;
    @Autowired
    private UsuarioSevice usuarioSevice;
    @Autowired
    private PrestadorService prestadorSevice;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_SENHA = "senha";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_NOME = "nome";
    private final String PARAM_APIKEY = "apiKey";
    private final String PARAM_FACEBOOK_ID = "facebookId";
   
    @POST
    @Path("{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String gerarToken(@PathParam("token") Long id, String json){
    	String retorno = BLANK_RETURN;
    	Token token = null;
    	try{
    		Usuario usuario = usuarioSevice.findByCodigo(id);
    		if(usuario != null){
    			token = tokenGeneratorService.findByUsuarioId(id);
    			
    			Gson gson = new GsonBuilder().setExclusionStrategies(new TokenExclusionStrategy()).create();

    			if(token != null){
    	    		retorno = gson.toJson(token);
    			}else{
    				JSONObject jsonObject = new JSONObject(json);
    				configurarUsuario(usuario, jsonObject);

    				token = tokenGeneratorService.create(usuario);
    				retorno = gson.toJson(token);
    			}
    		}else{
    			//TODO Saber qual a mensagem enviar para o cliente
    		}
    	}catch(ServiceException e){
    		//TODO Saber qual mensagem passar para o usuário
    	}catch (Exception e) {
    		//TODO Saber qual mensagem passar para o usuário
		}
    	return retorno;
    }
    
    @DELETE
    @Path("/{logout}/{email}")
    public void removerToken(@PathParam("logout") String logout, @PathParam("email") String email, String json){
    	try {
    		Usuario usuario = usuarioSevice.findByEmail(email);
			if(usuario != null){
				JSONObject jsonObject = new JSONObject(json);

				if(logout.equalsIgnoreCase("logout")){
					Token token = new Token();
					configurarToken(token, usuario, jsonObject);
					
					Token tokenPesquisado = tokenGeneratorService.findByApiKeyAndUsuarioId(token.getApiKey(), usuario.getId());
					if(tokenPesquisado != null){
						tokenGeneratorService.delete(tokenPesquisado);
					}
				}else if(logout.equalsIgnoreCase("logoutAll")){
					tokenGeneratorService.deleteAllByUsuario(usuario);
				}
			}else{
				//TODO Saber qual mensagem passar para o usuário
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response realizarLogin(String json){
    	Response response = null;
    	try{
			Usuario usuario = new Usuario();
			configurarUsuario(usuario, new JSONObject(json));
			response = configurarResponse(usuario);
    	}catch(ServiceException e){
    		response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
		}
    	return response;
    }
    
    @POST
    @Path("login/facebook")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response realizarLoginFacebook(String json){
    	Response response = null;
    	try{
			Prestador prestador = new Prestador();
			configurarUsuario(prestador, new JSONObject(json));
			configurarPrestador(prestador);
			Prestador usuarioPesquisado = prestadorSevice.findByEmail(prestador.getEmail());
			if(usuarioPesquisado == null){
				prestadorSevice.create(prestador);
			} else {
				if(!usuarioPesquisado.getFacebookId().equals(prestador.getFacebookId())){
					prestadorSevice.update(prestador);;
				}
				prestador.setSenha(usuarioPesquisado.getSenha());
			}
			response = configurarResponse(prestador);
    	}catch (Exception e) {
    		response = CallBackUtil.setResponseError(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
		}
    	return response;
    }
    
    private void configurarPrestador(Prestador usuario) {
    	usuario.setCpf(usuario.getEmail());
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

	private Response configurarResponse(Usuario usuario) throws ServiceException{
    	Token token = tokenGeneratorService.create(usuario);
    	String retorno = BLANK_RETURN;
		Response response = null;
		if (token != null) {
			Gson gson = new GsonBuilder().setExclusionStrategies(new TokenExclusionStrategy()).create();
			retorno = gson.toJson(token);
			response  = CallBackUtil.setResponseOK(retorno, MediaType.APPLICATION_JSON);
		} else {
			response = CallBackUtil.setResponseError(Status.INTERNAL_SERVER_ERROR.getStatusCode(), MessageUtil.getMessageFromBundle("exception.token_nao_encontrado"));
		}
		return response;
    }
    
    private void configurarUsuario(Usuario usuario, JSONObject jsonObject) throws JSONException{
    	usuario.setSenha(jsonObject.getString(PARAM_SENHA));
		usuario.setEmail(jsonObject.getString(PARAM_EMAIL));
		
		try{
			usuario.setNome(jsonObject.getString(PARAM_NOME));
		}catch(Exception e){
			usuario.setNome(null);
		}		
		
		try{
			usuario.setFacebookId(jsonObject.getString(PARAM_FACEBOOK_ID));
		}catch(Exception e){
			usuario.setFacebookId(null);
		}		
    }
    
    private void configurarToken(Token token, Usuario usuario, JSONObject jsonObject) throws JSONException, ServiceException{
    	token.setUsuario(usuario);
    	token.setApiKey(jsonObject.getString(PARAM_APIKEY));
    }
}