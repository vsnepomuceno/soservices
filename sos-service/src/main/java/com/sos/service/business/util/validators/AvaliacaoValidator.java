package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Avaliacao;
import com.sos.service.util.MessageUtil;

public class AvaliacaoValidator{

	private static final String AVALIACAO_NULA = "exception.avaliacao_nula";
	private static final String AVALIACAO_NOTA_MENOR_ZERO = "exception.avaliacao_nota_menor_zero";
	private static final String AVALIACAO_DEPOIMENTO_LIMITE_CARACTERES = "exception.avaliacao_depoimento_limite_caracteres";
	private static final String AVALIACAO_REPLICA_LIMITE_CARACTERES = "exception.avaliacao_replica_limite_caracteres";
	
	public static ResultadoValidacao validarCamposAvaliacao(Avaliacao avaliacao){
		List<String> msgs = new ArrayList<String>();
		boolean valido = false;
		if(avaliacao != null){
			valido = validarNota(avaliacao, msgs) && validarCamposTexto(avaliacao, msgs);
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(AVALIACAO_NULA));
		}
		return new ResultadoValidacao(valido, msgs);
	}

	private static boolean validarNota(Avaliacao avaliacao, List<String> msgs){
		boolean valido = false;
		
		if(avaliacao.getNota() != null && avaliacao.getNota() >= 0){
			valido = true;
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(AVALIACAO_NOTA_MENOR_ZERO));
		}
		return valido;
	}
	
	private static boolean validarCamposTexto(Avaliacao avaliacao, List<String> msgs){
		boolean valido = true;
		
		if(avaliacao.getDepoimento() == null || avaliacao.getDepoimento().trim().length() > 1000){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(AVALIACAO_DEPOIMENTO_LIMITE_CARACTERES));
		}
		
		if(valido && (avaliacao.getReplica() != null || avaliacao.getReplica().trim().length() > 1000)){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(AVALIACAO_REPLICA_LIMITE_CARACTERES));
		}

		return valido;
	}
}