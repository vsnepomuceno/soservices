package com.sos.service.util;

import java.util.ResourceBundle;

public class MessageUtil {

	private final static ResourceBundle BUNDLE_MESSAGES = ResourceBundle.getBundle("messageResource");

	public static String getMessageFromBundle(String key) {
		return BUNDLE_MESSAGES.getString(key);
	}
}