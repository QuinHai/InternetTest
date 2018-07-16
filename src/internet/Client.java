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
 * 缺陷：输入长度可能超出限度
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
	 * 开启接受信息的线程
	 * @throws Exception
	 */
	public void createConnect() throws Exception{
		
		//开启socket并获取输入输出流
		s = new Socket(ip, port);
		socketInputStream = s.getInputStream();
		socketOutputStream = s.getOutputStream();
		
		//开启线程
		exit = false;
		this.start();
		
		TestUtil.Log("Client create");
	}
	
	/**
	 * 结束接受信息线程，并发送终止信息
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
		//如果从服务器端接受到终止信息，那么也中断
		if(InternetUtil.checkExit(msg, ip)) {
			exit = true;
			ChatPanel.instance.appendChatMessage("服务器连接已断开...");
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
			//关闭socket及流
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
