package com.bnt.bntshine;

public final class AppConfiguration {
	private static String WWW = "http://www.bntsystems.pl";
	private static String MAIL_ADDRESS = "dwybranczyk@bntsystems.pl";
	private static String MAIL_SUBJECT = "Pomoc BNT Shine";
	private static String PHONE_NUMBER = "100000000";
	
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
}
