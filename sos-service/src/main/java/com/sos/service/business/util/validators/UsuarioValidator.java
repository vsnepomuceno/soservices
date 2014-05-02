package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Prestador;
import com.sos.service.util.MessageUtil;

public class UsuarioValidator {

	private static final String USUARIO_NOME_OBRIGATORIO = "exception.usuario_nome_obrigatorio";
	private static final String USUARIO_EMAIL_OBRIGATORIO = "exception.usuario_email_obrigatorio";
	private static final String USUARIO_SENHA_OBRIGATORIO = "exception.usuario_senha_obrigatoria";
	
	protected static ResultadoValidacao validarCamposUsuario(Prestador prestador, boolean editar){
		List<String> msgs = new ArrayList<String>();
		boolean valido = validarNome(prestador, msgs) && validarEmail(prestador, msgs) && validarSenha(prestador, msgs);
		return new ResultadoValidacao(valido, msgs);
	}
	
	private static boolean validarNome(Prestador prestador, List<String> msgs){
		boolean valido = true;
		if(prestador.getNome() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_NOME_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarEmail(Prestador prestador, List<String> msgs){
		boolean valido = true;
		if(prestador.getEmail() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_EMAIL_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarSenha(Prestador prestador, List<String> msgs){
		boolean valido = true;
		if(prestador.getSenha() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_SENHA_OBRIGATORIO));
		}
		return valido;
	}
}