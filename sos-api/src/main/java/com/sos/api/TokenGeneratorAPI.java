package com.sos.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sos.api.util.TokenExclusionStrategy;
import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.business.TokenGeneratorService;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.util.exception.ServiceException;

@Path("token")
@Component
public class TokenGeneratorAPI {

    @Autowired
    private TokenGeneratorService tokenGeneratorService;
    @Autowired
    private UsuarioSevice usuarioSevice;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_SENHA = "senha";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_APIKEY = "apikey";
   
    @POST
    @Path("{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String gerarToken(@PathParam("token") Long id, String json){
    	String retorno = BLANK_RETURN;
    	try{
    		Usuario usuario = usuarioSevice.findByCodigo(id);
    		if(usuario != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarUsuario(usuario, jsonObject);
    			Token token = tokenGeneratorService.create(usuario);
    			if(token != null){
    				Gson gson = new GsonBuilder().setExclusionStrategies(new TokenExclusionStrategy()).create();
    	    		retorno = gson.toJson(token);
    			}else{
    				//TODO Saber qual mensagem passar para o usuário
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
    @Path("/{logout}/{token}")
    public void removerToken(@PathParam("logout") String logout, @PathParam("token") Long codigo, String json){
    	try {
    		Usuario usuario = usuarioSevice.findByCodigo(codigo);
			if(usuario != null){
				JSONObject jsonObject = new JSONObject(json);

				if(logout.equalsIgnoreCase("logout")){
					Token token = new Token();
					configurarToken(token, usuario, jsonObject);
					
					Token tokenPesquisado = tokenGeneratorService.findByApiKeyAndUsuarioId(token.getApiKey(), codigo);
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
    
    private void configurarUsuario(Usuario usuario, JSONObject jsonObject) throws JSONException{
    	usuario.setSenha(jsonObject.getString(PARAM_SENHA));
		usuario.setEmail(jsonObject.getString(PARAM_EMAIL));
    }
    
    private void configurarToken(Token token, Usuario usuario, JSONObject jsonObject) throws JSONException, ServiceException{
    	token.setUsuario(usuario);
    	token.setApiKey(jsonObject.getString(PARAM_APIKEY));
    }
}