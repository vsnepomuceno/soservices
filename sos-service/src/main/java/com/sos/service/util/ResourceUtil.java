package com.sos.service.util;

import java.util.ResourceBundle;

public class ResourceUtil {

	private final static ResourceBundle RESOURCE_PROPERTY_BUNDLE = ResourceBundle.getBundle("resource");
	
	public static String getResourceProperty(String chave) {
		return RESOURCE_PROPERTY_BUNDLE.getString(chave);
	}
}