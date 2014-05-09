package com.sos.api.util;

public class CallBackUtil {

	public static String checarCallback(String callback, String retorno){
		if (callback != null) {
			retorno = callback + "(" +retorno+ ")";
		}
		return retorno;
	}
}
