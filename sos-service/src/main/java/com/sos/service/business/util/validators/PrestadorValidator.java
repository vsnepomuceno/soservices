package com.sos.service.business.util.validators;

import java.util.List;

import com.sos.entities.Prestador;
import com.sos.service.util.MessageUtil;

public class PrestadorValidator extends UsuarioValidator{

	private static final String PRESTADOR_CPF_OBRIGATORIO = "exception.prestador_cpf_obrigatorio";
	private static final String PRESTRADOR_TELEFONE_OBRIGATORIO = "exception.prestador_telefone_obrigatorio";
	
	public static ResultadoValidacao validarCamposPrestador(Prestador prestador, boolean editar){
		ResultadoValidacao resultadoValidacaoUsuario = validarCamposUsuario(prestador, editar);
		boolean camposUsuarioValidado = resultadoValidacaoUsuario.isValido();
		List<String> msgs = resultadoValidacaoUsuario.getMsgs();

		ResultadoValidacao resultadoValidacaoEndereco = EnderecoValidator.validarCamposEndereco(prestador.getEndereco(), editar);
		boolean camposEnderecoValidados = resultadoValidacaoEndereco.isValido();
		msgs.addAll(resultadoValidacaoEndereco.getMsgs());
		
		boolean valido = validarCPF(prestador, msgs) && validarTelefone(prestador, msgs) && camposUsuarioValidado && camposEnderecoValidados;
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