package util;

import properties.Property;

public class InternetUtil {
	
	
	private InternetUtil() {
		
	}
	
	public static boolean checkExit(String msg, String ip) {
		return (msg.equals(ip + Property.EXIT ) || msg.equals(ip + Property.EXIT + Property.LINE_SEPARATOR));
	}
}
