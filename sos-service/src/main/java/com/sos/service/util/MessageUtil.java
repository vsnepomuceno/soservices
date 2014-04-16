package com.sos.service.util;

import java.util.ResourceBundle;

public class MessageUtil {

	/**
	 * resource bundle instance
	 */
	public final static ResourceBundle bundleMessages = ResourceBundle.getBundle("messageResource");

	public static String getMessageFromBundle(String key) {
		return bundleMessages.getString(key);
	}
}