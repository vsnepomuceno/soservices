package com.sos.service.util;

import java.util.ResourceBundle;

public class SettingsUtil {

	/**
	 * resource bundle instance
	 */
	public final static ResourceBundle PROPERTY_RESOURCE_BUNDLE = ResourceBundle.getBundle("resource");

	/**
	 * @param key - key property from the bundle
	 * @return message from resource bundle
	 */
	public static String getPropertyFromBundle(String key) {
		return PROPERTY_RESOURCE_BUNDLE.getString(key);
	}
}