package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Usuario;
import com.sos.service.util.MessageUtil;

public class UsuarioValidator {

	private static final String USUARIO_NULO = "exception.usuario_nulo";
	private static final String USUARIO_NOME_OBRIGATORIO = "exception.usuario_nome_obrigatorio";
	private static final String USUARIO_EMAIL_OBRIGATORIO = "exception.usuario_email_obrigatorio";
	private static final String USUARIO_SENHA_OBRIGATORIO = "exception.usuario_senha_obrigatoria";
	
	protected static ResultadoValidacao validarCamposUsuario(Usuario usuario){
		List<String> msgs = new ArrayList<String>();
		boolean valido = false;
		if(usuario != null){
			valido = validarNome(usuario, msgs) && validarEmail(usuario, msgs) && validarSenha(usuario, msgs);
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_NULO));
		}
		return new ResultadoValidacao(valido, msgs);
	}
	
	private static boolean validarNome(Usuario usuario, List<String> msgs){
		boolean valido = true;
		if(usuario.getNome() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_NOME_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarEmail(Usuario usuario, List<String> msgs){
		boolean valido = true;
		if(usuario.getEmail() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_EMAIL_OBRIGATORIO));
		}
		return valido;
	}
	
	private static boolean validarSenha(Usuario usuario, List<String> msgs){
		boolean valido = true;
		if(usuario.getSenha() == null){
			valido = false;
			msgs.add(MessageUtil.getMessageFromBundle(USUARIO_SENHA_OBRIGATORIO));
		}
		return valido;
	}
}