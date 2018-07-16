package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import frame.MainFrame;
import listener.MainPanelButtonListener;
import util.GUIUtil;


public class MainPanel extends JPanel{

	private static final long serialVersionUID = 4655388486972447016L;
	public static MainPanel instance = new MainPanel();
	public JButton btn_server = new JButton();
	public JButton btn_client = new JButton();
	
	private MainPanel() {
		this.setLayout(null);
		GUIUtil.setButtonString(btn_server, "服务端");
		GUIUtil.setButtonString(btn_client, "客户端");
		
		btn_server.setBounds(150, 100, 200, 50);
		btn_client.setBounds(150, 152, 200, 50);
		MainPanelButtonListener listener = new MainPanelButtonListener();
		btn_server.addActionListener(listener);
		btn_client.addActionListener(listener);
		
		add(btn_client);
		add(btn_server);
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(MainFrame.instance, MainPanel.instance);
	}
}
