package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sos.api.util.CallBackUtil;
import com.sos.entities.Usuario;
import com.sos.service.business.UsuarioSevice;
import com.sos.service.util.exception.ServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@Path("usuario")
@Component
public class UsuarioAPI {

    @Autowired
    private UsuarioSevice usuarioService;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_NOME = "nome";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_SENHA = "senha";
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getUsuarios(@QueryParam("callback") String callback) {
    	String retorno = BLANK_RETURN;
		try {
			List<Usuario> usuarios = usuarioService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.ID_REFERENCES);
			xStream.alias("usuario", Usuario.class);
			
			retorno = xStream.toXML(usuarios);
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
    public String cadastrarUsuario(String json){
    	String retorno = BLANK_RETURN;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Usuario usuario = new Usuario();
    		usuario.setNome(jsonObject.getString(PARAM_NOME));
    		usuario.setEmail(jsonObject.getString(PARAM_EMAIL));
    		usuario.setSenha(jsonObject.getString(PARAM_SENHA));
    		
			usuarioService.create(usuario);
		} catch (ServiceException e) {
			//TODO Mostrar como vai ser mostrado a mensagem de erro para o cliente
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return retorno;
    }
    
    @DELETE
    public String removerUsuario(String json){
    	return null;
    }
}