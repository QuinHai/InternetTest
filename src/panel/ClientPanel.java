package panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frame.MainFrame;
import internet.InternetFactory;
import internet.InternetFactory.Port;
import launch.BootUp;
import listener.BackButtonListener;
import properties.Property;
import util.GUIUtil;

public class ClientPanel extends JPanel{

	public static ClientPanel instance = new ClientPanel();
	public JButton btn_back = new JButton();
	public JLabel lab_serverip = new JLabel();
	public JLabel lab_name = new JLabel();
	
	private ClientPanel() {
		startClient();
		GUIUtil.setButtonString(btn_back, "返回");
		
		lab_serverip.setText((String)Property.getAttribute("ip"));
		lab_name.setText((String)Property.getAttribute("name"));
		
		addListener();
		
		JPanel p_name = new JPanel(new FlowLayout());
		p_name.add(lab_name);
		
		JPanel p_top = new JPanel(new BorderLayout());
		p_top.add(lab_serverip, BorderLayout.WEST);
		p_top.add(p_name, BorderLayout.CENTER);
		p_top.add(btn_back, BorderLayout.EAST);
		ChatPanel.instance.isServer = false;
		
		this.setLayout(new BorderLayout());
		this.add(p_top, BorderLayout.NORTH);
		this.add(ChatPanel.instance);
	}
	
	public static void reload() {
		instance = new ClientPanel();
	}
	
	private void startClient() {
		boolean isConnected = false;
		try {
			for (InternetFactory.Port p : Port.values()) {
				try {
					BootUp.client = InternetFactory.createClient((String)Property.getAttribute("ip"), p);
					BootUp.client.createConnect();
					isConnected = true;
					break;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isConnected = false;
					continue;
				}
			}
			if(!isConnected) throw new Exception("连接中断..." + Property.LINE_SEPARATOR);
		} catch (Exception e) {
			ChatPanel.instance.ta_chat.append(e.getMessage());
		}
	}
	
	private void addListener() {
		btn_back.addActionListener(new BackButtonListener());
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(MainFrame.instance, ClientPanel.instance);
	}

}
