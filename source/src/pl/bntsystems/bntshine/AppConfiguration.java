package pl.bntsystems.bntshine;

public class AppConfiguration {
	private static final int DEFAULT_GLOBAL_OFF_METHOD = 0;
	private static final String WWW = "http://www.bntsystems.pl";
	private static final String MAIL_ADDRESS = "dwybranczyk@bntsystems.pl";
	private static final String MAIL_SUBJECT = "Pomoc BNT Shine";
	private static final String PHONE_NUMBER = "100000000";
	private static final String DEFAULT_IP = "192.168.0.123";
	private static final String DEFAULT_MASK = "255.255.255.0";
	private static final boolean DEFAULT_MODE = true; //true - manual, false - automatic
	private static final int PAGES_COUNT = 1;
	
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

	public static int getDefaultGlobalOffMethod() {
		return DEFAULT_GLOBAL_OFF_METHOD;
	}

	public static int getPagesCount() {
		return PAGES_COUNT;
	}
}
