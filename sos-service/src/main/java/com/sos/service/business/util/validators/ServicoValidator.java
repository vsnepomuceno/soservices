package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Servico;
import com.sos.service.util.MessageUtil;

public class ServicoValidator {

	private static final String SERVICO_NULO = "exception.servico_nulo";
	private static final String SERVICO_DESCRICAO_OBRIGATORIA = "exception.servico_descricao_obrigatoria";
	private static final String SERVICO_TIPO_SERVICO_OBRIGATORIO = "exception.servico_tipo_servico_obrigatorio";
	private static final String SERVICO_PRESTADOR_OBRIGATORIO = "exception.servico_prestador_obrigatorio";
	private static final String SERVICO_VALOR_NAO_POSITIVO = "exception.servico_valor_nao_positivo";
//	private static final String SERVICO_TIPO_SERVICO_NAO_VALORADO = "exception.servico_tipo_servico_nao_valorado";
	private static final String SERVICO_TIPO_SERVICO_NAO_VALORADO_OBRIGATORIO = "exception.servico_tipo_servico_nao_valorado_obrigatorio";
	
	public static ResultadoValidacao validarCamposServico(Servico servico){
		List<String> msgs = new ArrayList<String>();
		boolean valido = false;
		if(servico != null){
			valido = validarDescricao(servico, msgs) && validarTipoServico(servico, msgs) 
					&& validarValor(servico, msgs) && validarPrestador(servico, msgs);
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_NULO));
		}
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
		if(servico.getTipoServico().isValorado() != null){
			if(servico.getTipoServico().isValorado()){
				if(servico.getValor() == null || servico.getValor() <= 0){
					valido = false;
					msgs.add(MessageUtil.getMessageFromBundle(SERVICO_VALOR_NAO_POSITIVO));
				}
			}else{
				servico.setValor(null);
			}
		}else{
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_TIPO_SERVICO_NAO_VALORADO_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarPrestador(Servico servico, List<String> msgs){
		boolean valido = true;
		if(servico.getPrestador() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(SERVICO_PRESTADOR_OBRIGATORIO));
		}
		return valido;
	}
}
