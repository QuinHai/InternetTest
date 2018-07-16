package internet;

public class InternetFactory {
	
	public enum Port{
		 PORT_1(10008),
		 PORT_2(10010),
		 PORT_3(10012),
		 PORT_4(10015),
		 PORT_5(8001),
		 PORT_6(8004);
		
		private int port;
		
		private Port(int port) {
			this.port = port;
		}
		
		public int getValue() {
			return port;
		}
	}
	
	
	
	
	public static Client createClient(String ip, Port type) {
		switch(type) {
		case PORT_1: return new Client(ip, type.PORT_1.getValue());
		case PORT_2: return new Client(ip, type.PORT_2.getValue());
		case PORT_3: return new Client(ip, type.PORT_3.getValue());
		case PORT_4: return new Client(ip, type.PORT_4.getValue());
		case PORT_5: return new Client(ip, type.PORT_5.getValue());
		case PORT_6: return new Client(ip, type.PORT_6.getValue());
		default:
			return null;
		}
	}
	
	public static Server createServer(Port type) {
		switch(type) {
		case PORT_1: return new Server(type.PORT_1.getValue());
		case PORT_2: return new Server(type.PORT_2.getValue());
		case PORT_3: return new Server(type.PORT_3.getValue());
		case PORT_4: return new Server(type.PORT_4.getValue());
		case PORT_5: return new Server(type.PORT_5.getValue());
		case PORT_6: return new Server(type.PORT_6.getValue());
		default:
			return null;
		}
	}
	
	
}
