package com.sos.api.util;

import javax.ws.rs.core.Response;

public class CallBackUtil {

	public static Response setResponseError(int codigo, String mensagem, String callback){
		return Response.serverError().status(codigo).entity(mensagem).build();
	}

	public static Response setResponseOK(String mensagem, String mediaType, String callback){
		if (callback != null) {
			mensagem = callback + "(" +mensagem+ ")";
		}
		return Response.ok(mensagem, mediaType).build();	
	}
}