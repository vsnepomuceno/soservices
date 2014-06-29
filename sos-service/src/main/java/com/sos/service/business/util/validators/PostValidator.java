package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Post;
import com.sos.service.util.MessageUtil;

public class PostValidator{

	private static final String POST_NULO = "exception.post_nulo";
	private static final String POST_MSG_INVALIDA = "exception.post.msg.invalid";
	
	
	public static ResultadoValidacao validarCamposPost(Post post){
		List<String> msgs = new ArrayList<String>();
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
		return new ResultadoValidacao(valido, msgs);
	}

	
}