package properties;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Property {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator"); 
	public static final String EXIT = "::EXIT::0x4a2cb71";
	public static String LOCAL_IP;
	
	static{
		try {
			LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Map<String , Object> pros = new HashMap<String , Object>();
	
	private Property() {
		
	}
	
	public static void clear() {
		pros.clear();
	}
	
	public static void setAttribute(String key, Object obj) {
		pros.put(key, obj);
	}
	
	public static Object getAttribute(String key) {
		return pros.get(key);
	}
	
}
