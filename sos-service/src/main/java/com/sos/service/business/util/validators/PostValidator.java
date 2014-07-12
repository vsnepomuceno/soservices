package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Post;
import com.sos.service.util.MessageUtil;

public class PostValidator{

	private static final String POST_NULO = "exception.post_nulo";
	private static final String POST_MSG_INVALIDA = "exception.post_msg_invalida";
	private static final String POST_USUARIO_NULO = "exception.post.usuario_nulo";
	private static final String POST_FORUM_NULO = "exception.post.forum_nulo";
	
	
	public static ResultadoValidacao validarCamposPost(Post post){
		List<String> msgs = new ArrayList<String>();
		boolean valido = validarMensagem(msgs, post) && validarUsuario(msgs, post) && validarForum(msgs, post);
		
		return new ResultadoValidacao(valido, msgs);
	}

	private static boolean validarMensagem(List<String> msgs, Post post){
		boolean valido = false;
		
		if(post != null){
			if ((post.getMensagem() != null && post.getMensagem().length() <= 1000)) {
				valido = true;
			} else {
				msgs.add(MessageUtil.getMessageFromBundle(POST_MSG_INVALIDA));
			}
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(POST_NULO));
		}
		return valido;
	}
	
	private static boolean validarUsuario(List<String> msgs, Post post){
		boolean valido = false;
		
		if(post != null){
			if ((post.getUsuario() != null)){
				valido = true;
			} else {
				msgs.add(MessageUtil.getMessageFromBundle(POST_USUARIO_NULO));
			}
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(POST_NULO));
		}
		return valido;
	}
	
	private static boolean validarForum(List<String> msgs, Post post){
		boolean valido = false;
		
		if(post != null){
			if ((post.getForum() != null)){
				valido = true;
			} else {
				msgs.add(MessageUtil.getMessageFromBundle(POST_FORUM_NULO));
			}
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(POST_NULO));
		}
		return valido;
	}
}