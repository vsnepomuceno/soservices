package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.TipoServico;
import com.sos.service.util.MessageUtil;

public class TipoServicoValidator {

	private static final String TIPO_SERVICO_NOME_OBRIGATORIO = "exception.tipo_servico_nome_obrigatorio";
	private static final String TIPO_SERVICO_VALORADO_OBRIGATORIO = "exception.tipo_servico_valorado_obrigatorio";
	
	public static ResultadoValidacao validarCamposTipoServico(TipoServico tipoServico){
		List<String> msgs = new ArrayList<String>();
		boolean valido = validarNome(tipoServico, msgs) && validarValorado(tipoServico, msgs);
		return new ResultadoValidacao(valido, msgs);
	}
	
	private static boolean validarNome(TipoServico tipoServico, List<String> msgs){
		boolean valido = true;
		if(tipoServico.getNome() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(TIPO_SERVICO_NOME_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarValorado(TipoServico tipoServico, List<String> msgs){
		boolean valido = true;
		if(tipoServico.isValorado() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(TIPO_SERVICO_VALORADO_OBRIGATORIO));
		}
		return valido;
	}
}