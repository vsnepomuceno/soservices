package com.sos.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sos.api.util.CallBackUtil;
import com.sos.entities.Usuario;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.util.exception.ServiceException;

@Path("usuario")
@Component
public class UsuarioAPI {
	
	
	@Autowired
    private UsuarioSevice usuarioService;

    private final String PARAM_NOME = "nome";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_SENHA = "senha";
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarPrestador(String json){
    	Response response = null;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Usuario usuario = new Usuario();
    		configurarUsuario(usuario, jsonObject);
    		
    		usuarioService.create(usuario);
			response = CallBackUtil.setResponseOK("Usuario Criado Com sucesso.", MediaType.APPLICATION_JSON);
		} catch (ServiceException e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			response = CallBackUtil.setResponseError(Status.BAD_REQUEST.getStatusCode(), e.getMessage());
			e.printStackTrace();
		}
    	return response;
    }
	
	private void configurarUsuario(Usuario usuario, JSONObject jsonObject) throws JSONException{
		usuario.setNome(jsonObject.getString(PARAM_NOME));
		usuario.setEmail(jsonObject.getString(PARAM_EMAIL));
		usuario.setSenha(jsonObject.getString(PARAM_SENHA));
	}
}