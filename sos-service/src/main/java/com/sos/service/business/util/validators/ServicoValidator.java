package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Servico;
import com.sos.service.util.MessageUtil;

public class ServicoValidator {

	private static final String SERVICO_DESCRICAO_OBRIGATORIA = "exception.servico_descricao_obrigatoria";
	private static final String SERVICO_TIPO_SERVICO_OBRIGATORIO = "exception.servico_tipo_servico_obrigatorio";
	private static final String SERVICO_PRESTADOR_OBRIGATORIO = "exception.servico_prestador_obrigatorio";
	private static final String SERVICO_VALOR_OBRIGATORIO = "exception.servico_valor_obrigatorio";
	private static final String SERVICO_VALOR_NAO_POSITIVO = "exception.servico_valor_nao_positivo";
	
	public static ResultadoValidacao validarCamposServico(Servico servico, boolean editar){
		List<String> msgs = new ArrayList<String>();
		boolean valido = validarDescricao(servico, msgs) && validarTipoServico(servico, msgs) 
				&& validarValor(servico, msgs) && validarPrestador(servico, msgs);
		return new ResultadoValidacao(valido, msgs);
	}
	
	private static boolean validarDescricao(Servico servico, List<String> msgs){
		boolean valido = true;
		if(servico.getDescricao() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_DESCRICAO_OBRIGATORIA));
		}
		return valido;
	}
	
	private static boolean validarTipoServico(Servico servico, List<String> msgs){
		boolean valido = true;
		if(servico.getTipoServico() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_TIPO_SERVICO_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarValor(Servico servico, List<String> msgs){
		boolean valido = true;
		if(servico.getValor() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_VALOR_OBRIGATORIO));
		}else if(servico.getValor() <= 0){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_VALOR_NAO_POSITIVO));
		}
		return valido;
	}
	
	private static boolean validarPrestador(Servico servico, List<String> msgs){
		boolean valido = true;
		if(servico.getPrestador() == null || servico.getPrestador().getId() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_PRESTADOR_OBRIGATORIO));
		}
		return valido;
	}
}
