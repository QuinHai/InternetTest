package internet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import panel.ChatPanel;
import properties.Property;
import util.InternetUtil;
import util.TestUtil;

/**
 * ȱ�ݣ����볤�ȿ��ܳ����޶�
 * @author song1
 *
 */

public class Client extends Thread {
	private volatile boolean exit = false; 
	
	private Integer port;
	private String ip;
	private Socket s;
	
	private InputStream socketInputStream;
	private OutputStream socketOutputStream;
	private PrintWriter out;
	private BufferedInputStream bufIn;
	
	public Client(String ip, Integer port){
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * ����������Ϣ���߳�
	 * @throws Exception
	 */
	public void createConnect() throws Exception{
		
		//����socket����ȡ���������
		s = new Socket(ip, port);
		socketInputStream = s.getInputStream();
		socketOutputStream = s.getOutputStream();
		
		//�����߳�
		exit = false;
		this.start();
		
		TestUtil.Log("Client create");
	}
	
	/**
	 * ����������Ϣ�̣߳���������ֹ��Ϣ
	 * @throws IOException
	 */
	public void destroyConnect() throws IOException {
		if(!exit) {
			uploadMessage(ip + Property.EXIT);
			exit = true;
		}
	}
	
	public void uploadMessage(String msg) throws IOException{
		if(out == null)
			out = new PrintWriter(socketOutputStream, true);
		out.write(msg);
		out.flush();
	}
	
	public void receiveMessage() throws IOException {
		if(bufIn == null)
			bufIn = new BufferedInputStream(s.getInputStream());
		byte[] buf = new byte[1024];
		int len = bufIn.read(buf);
		String msg = "";
		if(len != -1)
			msg = new String(buf,0, len);
		//����ӷ������˽��ܵ���ֹ��Ϣ����ôҲ�ж�
		if(InternetUtil.checkExit(msg, ip)) {
			exit = true;
			ChatPanel.instance.appendChatMessage("�����������ѶϿ�...");
		}
		else if(msg != null && msg != "")
			ChatPanel.instance.appendChatMessage(msg);
	}
	
	@Override
	public void run() {
		while(!exit)
		{
			try {
				receiveMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			TestUtil.Log("Client close");
			//�ر�socket����
			socketInputStream.close();
			socketOutputStream.close();
			if(out != null)
				out.close();
			if(bufIn != null)
				bufIn.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
