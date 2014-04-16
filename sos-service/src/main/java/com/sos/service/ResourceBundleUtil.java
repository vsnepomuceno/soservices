package com.sos.service;

import java.util.ResourceBundle;

public class ResourceBundleUtil {

	private final static ResourceBundle mensagemBundle = ResourceBundle.getBundle("messageResource");
	private final static ResourceBundle resourcePropertyBundle = ResourceBundle.getBundle("resource");
	
	public static String getMensagem(String chave) {
		return mensagemBundle.getString(chave);
	}
	
	public static String getResourceProperty(String chave) {
		return resourcePropertyBundle.getString(chave);
	}
}