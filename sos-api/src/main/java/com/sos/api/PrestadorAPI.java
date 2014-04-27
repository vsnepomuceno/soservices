package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;
import com.sos.entities.Usuario;
import com.sos.service.business.PrestadorService;
import com.sos.service.util.exception.ServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@Path("prestador")
@Component
public class PrestadorAPI {

    @Autowired
    private PrestadorService prestadorService;

    private final String BLANK_RETURN = "{}";
    private final String PARAM_NOME = "nome";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_SENHA = "senha";
    private final String PARAM_CPF = "cpf";
    private final String PARAM_ENDERECO = "endereco";
    private final String PARAM_TELEFONE = "telefone";
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPrestadores() {
    	String retorno = BLANK_RETURN;
		try {
			List<Prestador> prestadores = prestadorService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			xStream.alias("tiposServicos", TipoServico.class);
			
			retorno = xStream.toXML(prestadores);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return retorno;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String cadastrarPrestador(String json){
    	String retorno = BLANK_RETURN;
    	try {
    		JSONObject jsonObject = new JSONObject(json);
    		Usuario usuario = new Prestador();
    		usuario.setNome(jsonObject.getString(PARAM_NOME));
    		usuario.setEmail(jsonObject.getString(PARAM_EMAIL));
    		usuario.setSenha(jsonObject.getString(PARAM_SENHA));
    		((Prestador)usuario).setCpf(PARAM_CPF);
    		((Prestador)usuario).setEndereco(PARAM_ENDERECO);
    		((Prestador)usuario).setTelefone(PARAM_TELEFONE);
    		
    		
			prestadorService.create((Prestador)usuario);
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
    public String removerPrestador(String json){
    	return null;
    }
}