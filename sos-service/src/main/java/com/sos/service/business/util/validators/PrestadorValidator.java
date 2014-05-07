package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Prestador;
import com.sos.service.util.MessageUtil;

public class PrestadorValidator extends UsuarioValidator{

	private static final String PRESTADOR_NULO = "exception.prestador_nulo";
	private static final String PRESTADOR_CPF_OBRIGATORIO = "exception.prestador_cpf_obrigatorio";
	private static final String PRESTRADOR_TELEFONE_OBRIGATORIO = "exception.prestador_telefone_obrigatorio";
	
	public static ResultadoValidacao validarCamposPrestador(Prestador prestador){
		List<String> msgs = new ArrayList<String>();
		boolean valido = false;
		if(prestador != null){
			ResultadoValidacao resultadoValidacaoUsuario = validarCamposUsuario(prestador);
			boolean camposUsuarioValidado = resultadoValidacaoUsuario.isValido();
			msgs = resultadoValidacaoUsuario.getMsgs();
			
			ResultadoValidacao resultadoValidacaoEndereco = EnderecoValidator.validarCamposEndereco(prestador.getEndereco());
			boolean camposEnderecoValidados = resultadoValidacaoEndereco.isValido();
			msgs.addAll(resultadoValidacaoEndereco.getMsgs());
			
			valido = validarCPF(prestador, msgs) && validarTelefone(prestador, msgs) && camposUsuarioValidado && camposEnderecoValidados;
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(PRESTADOR_NULO));
		}
		
		return new ResultadoValidacao(valido, msgs);
	}
	
	private static boolean validarCPF(Prestador prestador, List<String> msgs){
		boolean valido = true;
		if(prestador.getCpf() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(PRESTADOR_CPF_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarTelefone(Prestador prestador, List<String> msgs){
		boolean valido = true;
		if(prestador.getTelefone() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(PRESTRADOR_TELEFONE_OBRIGATORIO));
		}
		return valido;
	}
}