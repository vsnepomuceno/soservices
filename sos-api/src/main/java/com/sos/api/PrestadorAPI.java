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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sos.entities.Endereco;
import com.sos.entities.Prestador;
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
    private final String PARAM_LOGRADOURO = "logradouro";
    private final String PARAM_NUMERO = "numero";
    private final String PARAM_COMPLEMENTO = "complemento";
    private final String PARAM_CEP = "cep";
    private final String PARAM_TELEFONE = "telefone";
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPrestadores() {
    	String retorno = BLANK_RETURN;
		try {
			List<Prestador> prestadores = prestadorService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			xStream.alias("prestadores", Prestador.class);
			xStream.omitField(Set.class, "credenciais");
			
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
    		Prestador prestador = new Prestador();
    		configurarPrestador(prestador, jsonObject);
    		
			prestadorService.create(prestador);
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
    @Path("{prestador}")
    public void removerTipoServico(@PathParam("prestador") Long codigo){
    	try {
			Prestador prestador = prestadorService.findByCodigo(codigo);
			if(prestador != null){
				prestadorService.delete(prestador);
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
    @Path("{prestador}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editarPrestador(@PathParam("prestador") Long codigo, String json){
    	String retorno = BLANK_RETURN;
    	try{
    		Prestador prestador = prestadorService.findByCodigo(codigo);
    		if(prestador != null){
    			JSONObject jsonObject = new JSONObject(json);
    			configurarPrestador(prestador, jsonObject);
    			
    			prestadorService.update(prestador);
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
		endereco.setComplemento(jsonObject.getString(PARAM_COMPLEMENTO));
		endereco.setCep(jsonObject.getString(PARAM_CEP));
		prestador.setEndereco(endereco);
    }
}