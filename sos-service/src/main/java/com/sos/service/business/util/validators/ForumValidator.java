package com.sos.service.business.util.validators;

import java.util.ArrayList;
import java.util.List;

import com.sos.entities.Forum;
import com.sos.service.util.MessageUtil;

public class ForumValidator{

	private static final String FORUM_NULO = "exception.forum_nulo";
	
	
	public static ResultadoValidacao validarCamposForum(Forum forum){
		List<String> msgs = new ArrayList<String>();
		boolean valido = false;
		if(forum != null && forum.getPosts().size() > 0){
			valido = true;
		}else{
			msgs.add(MessageUtil.getMessageFromBundle(FORUM_NULO));
		}
		return new ResultadoValidacao(valido, msgs);
	}

	
}