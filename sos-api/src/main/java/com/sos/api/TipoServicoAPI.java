package com.sos.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTiposServicos() {
    	String retorno = BLANK_RETURN;
		try {
			List<TipoServico> tiposServicos = tipoServicoService.findAllSortByName();
			
			XStream xStream = new XStream(new JettisonMappedXmlDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			xStream.alias("tiposServicos", TipoServico.class);
			
			retorno = xStream.toXML(tiposServicos);
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
    //TODO Verificar como o método vai pegar os parâmetros vindo do cliente
    public String cadastrarTipoServico(@PathParam("nome") String nome, @PathParam("valorado") Boolean valorado){
    	String retorno = BLANK_RETURN;
    	
    	TipoServico tipoServico = new TipoServico();
    	tipoServico.setValorado(valorado);
    	tipoServico.setNome(nome);
    	
    	try {
			tipoServicoService.create(tipoServico);
		} catch (ServiceException e) {
			//TODO Mostrar como vai ser mostrado a mensagem de erro para o cliente
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return retorno;
    }
}