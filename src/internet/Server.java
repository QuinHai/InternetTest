package internet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import panel.ChatPanel;
import properties.Property;
import util.InternetUtil;
import util.TestUtil;

public class Server extends Thread {
	private volatile boolean exit = false;
	private volatile Integer clientCount = 0;

	private Integer port;
	
	private ServerSocket ss;

	private List<ConnetedClient> clients = new LinkedList<ConnetedClient>();

	public Server(Integer port) {
		this.port = port;
	}

	/**
	 * �������������߳�,�ȴ��ͻ�������
	 * @throws Exception
	 */
	public void createServer() throws Exception {
		// ����serversocket
		ss = new ServerSocket(port);

		// �����ȴ����ӵ��߳�
		exit = false;
		this.start();
		TestUtil.Log("Server create");
	}

	/**
	 * ������ֹ��Ϣ�����ر��߳�
	 * @throws IOException
	 */
	public void destroyServer() throws IOException {
		if(!exit) {
			sendMessage(Property.LOCAL_IP + Property.EXIT);
			exit = true;
		}
	}

	/**
	 * �������û��ַ���Ϣ
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessage(String msg) throws IOException {
		if (!clients.isEmpty()) {
			for (ConnetedClient i : clients) {
				if(i.out == null)
					i.out = new PrintWriter(i.socketOutputStream, true);
				i.out.write(msg);
				i.out.flush();
			}
		}
	}

	/**
	 * ��ȡ���������û���ip
	 * @return
	 */
	public List<String> getClientIp() {
		List<String> clientIpList = new LinkedList<String>();
		if (!clients.isEmpty()) {
			for (ConnetedClient i : clients) {
				clientIpList.add(i.s.getInetAddress().getHostAddress());
			}
		}
		return clientIpList;
	}

	@Override
	public void run() {
		while (!exit) {
			try {
				// �ȴ��͑��˽���
				Socket s = ss.accept();
				//����һ���������û�����
				clientCount++;
				ConnetedClient cc = new ConnetedClient(s,clientCount);
				
				TestUtil.Log("ConnectedClient create :" + clientCount);
				// ��socket����list���й���
				clients.add(cc);
				// ��ÿ������Ŀͻ��˿���һ�����߳�
				cc.createClientThread();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ��һ�رտͻ����̼߳�����
		for (ConnetedClient i : clients) {
			i.closeClientThread();
		}
		//�رշ�������serversocket
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class ConnetedClient {
		public Socket s;
		public Integer id;
		public String ip;

		public InputStream socketInputStream;
		public OutputStream socketOutputStream;
		public PrintWriter out;
		public BufferedInputStream bufIn;

		private ClientRunnable clientRunnable;

		public ConnetedClient(Socket s, Integer id) {
			this.s = s;
			this.id = id;
			ip = s.getInetAddress().getHostAddress();
			
			try {
				socketInputStream = s.getInputStream();
				socketOutputStream = s.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void closeConnection() throws IOException {
			socketInputStream.close();
			socketOutputStream.close();
			if (out != null)
				out.close();
			if (bufIn != null)
				bufIn.close();
			s.close();
		}
		
		public void closeClientThread() {
			clients.remove(this);
			clientRunnable.exit = true;
		}
		
		public void createClientThread(){
			clientRunnable = new ClientRunnable();
			new Thread(clientRunnable).start();
		}
		
		class ClientRunnable implements Runnable {
			private volatile boolean exit = false;

			public ClientRunnable() {
			}

			public void  receiveMessage() throws IOException {
				if(bufIn == null)
					bufIn = new BufferedInputStream(socketInputStream);
				byte[] buf = new byte[1024];
				int len = bufIn.read(buf);
				String msg = "";
				if(len != -1)
					msg = new String(buf, 0, len);
				// ���ܵ���ֹ��Ϣ���Ͽ�����
				if (InternetUtil.checkExit(msg, ip)) {
					exit = true;
					ChatPanel.instance.appendChatMessage(ip + "�����ѶϿ�...");
				}
					
				// ���ܿͻ�����Ϣ,�������пͻ��˷ַ�
				else if (msg != null && msg != "") {
					ChatPanel.instance.appendChatMessage(msg);
					sendMessage(msg);
				}
			}
			@Override
			public void run() {
				while (!exit) {
					try {
						// ѭ��������Ϣ
						receiveMessage();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					TestUtil.Log("ConnectedClient close: " + id);
					// �ر�socket�����Ӧ��
					closeConnection();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
