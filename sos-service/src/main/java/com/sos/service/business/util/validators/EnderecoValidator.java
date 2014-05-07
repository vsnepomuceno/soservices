package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Endereco;
import com.sos.service.util.MessageUtil;

public class EnderecoValidator{

	private static final String ENDERECO_NULO = "exception.endereco_nulo";
	private static final String ENDERECO_LOGRADOURO_OBRIGATORIO = "exception.endereco_logradouro_obrigatorio";
	private static final String ENDERECO_NUMERO_OBRIGATORIO = "exception.endereco_numero_obrigatorio";
	private static final String ENDERECO_CEP_OBRIGATORIO = "exception.endereco_cep_obrigatorio";
	private static final String ENDERECO_CIDADE_OBRIGATORIO = "exception.endereco_cidade_obrigatoria";
	private static final String ENDERECO_ESTADO_OBRIGATORIO = "exception.endereco_estado_obrigatorio";
	
	public static ResultadoValidacao validarCamposEndereco(Endereco endereco){
		List<String> msgs = new ArrayList<String>();
		boolean valido = false;
		
		if(endereco != null){
			valido = validarLogradouro(endereco, msgs) && validarNumero(endereco, msgs) && validarCEP(endereco, msgs)
					&& validarCidade(endereco, msgs) && validarEstado(endereco, msgs);
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(ENDERECO_NULO));
		}
		
		return new ResultadoValidacao(valido, msgs);
	}
	
	private static boolean validarLogradouro(Endereco endereco, List<String> msgs){
		boolean valido = true;
		if(endereco.getLogradouro() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(ENDERECO_LOGRADOURO_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarNumero(Endereco endereco, List<String> msgs){
		boolean valido = true;
		if(endereco.getNumero() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(ENDERECO_NUMERO_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarCEP(Endereco endereco, List<String> msgs){
		boolean valido = true;
		if(endereco.getCep() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(ENDERECO_CEP_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarCidade(Endereco endereco, List<String> msgs){
		boolean valido = true;
		if(endereco.getCidade() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(ENDERECO_CIDADE_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarEstado(Endereco endereco, List<String> msgs){
		boolean valido = true;
		if(endereco.getEstado() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(ENDERECO_ESTADO_OBRIGATORIO));
		}
		return valido;
	}
}