package com.bnt.bntshine;

public final class AppConfiguration {
	private static String WWW = "http://www.bntsystems.pl";
	private static String MAIL_ADDRESS = "dwybranczyk@bntsystems.pl";
	private static String MAIL_SUBJECT = "Pomoc BNT Shine";
	private static String PHONE_NUMBER = "100000000";
	private static String DEFAULT_IP = "192.168.0.123";
	private static String DEFAULT_MASK = "255.255.255.0";
	private static boolean DEFAULT_MODE = true; //true - manual, false - automatic
	
	public static String getWWW() {
		return WWW;
	}
	
	public static String getMailAddress() {
		return MAIL_ADDRESS;
	}
	
	public static String getMailSubject() {
		return MAIL_SUBJECT;
	}
	
	public static String getPhoneNumber() {
		return PHONE_NUMBER;
	}

	public static String getDefaultIP() {
		return DEFAULT_IP;
	}
	
	public static String getDefaultMask() {
		return DEFAULT_MASK;
	}

	public static boolean getDefaultMode() {
		return DEFAULT_MODE;
	}
}
