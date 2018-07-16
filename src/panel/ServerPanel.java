package panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frame.MainFrame;
import internet.InternetFactory;
import internet.Server;
import internet.InternetFactory.Port;
import launch.BootUp;
import listener.BackButtonListener;
import properties.Property;
import util.GUIUtil;

public class ServerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static ServerPanel instance = new ServerPanel();
	public JButton btn_back = new JButton();
	public JLabel lab_serverip = new JLabel();

	private ServerPanel() {
		// 开启服务端
		startServer();

		Property.setAttribute("name", "Administrator");
		GUIUtil.setButtonString(btn_back, "返回");
		btn_back.setSize(100, 50);

		lab_serverip.setText(Property.LOCAL_IP);

		addListener();

		this.setLayout(new BorderLayout());

		JPanel p_top = new JPanel(new BorderLayout());
		ChatPanel chat = ChatPanel.instance;
		ChatPanel.instance.isServer = true;

		p_top.add(lab_serverip, BorderLayout.CENTER);
		p_top.add(btn_back, BorderLayout.EAST);

		this.add(p_top, BorderLayout.NORTH);
		this.add(chat, BorderLayout.CENTER);
	}

	private void addListener() {
		btn_back.addActionListener(new BackButtonListener());
	}

	private void startServer() {
		boolean isConnected = false;
		try {
			for (InternetFactory.Port p : Port.values()) {
				try {
					BootUp.server = InternetFactory.createServer(p);
					BootUp.server.createServer();
					isConnected = true;
					break;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isConnected = false;
					continue;
				}
			}
			if(!isConnected) throw new Exception("连接中断..." + Property.LINE_SEPARATOR);;
		} catch (Exception e) {
			ChatPanel.instance.ta_chat.append(e.getMessage());
		}
	}

	public static void reload() {
		instance = new ServerPanel();
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(MainFrame.instance, ServerPanel.instance);
	}
}
