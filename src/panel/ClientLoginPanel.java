package panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.MainFrame;
import internet.InternetFactory;
import launch.BootUp;
import listener.BackButtonListener;
import properties.Property;
import util.GUIUtil;

public class ClientLoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public static ClientLoginPanel instance = new ClientLoginPanel();
	public JButton btn_submit = new JButton();
	public JButton btn_back   = new JButton();
	public JTextField tf_ip   = new JTextField();
	public JTextField tf_name = new JTextField();
	
	private ClientLoginPanel() {
		GUIUtil.setButtonString(btn_submit, "����");
		GUIUtil.setButtonString(btn_back, "����");
		
		JPanel p_fl = new JPanel(new FlowLayout());
		p_fl.add(btn_submit);
		p_fl.add(btn_back);
		
		addListener();
		
		this.setLayout(new GridLayout(9,1));
		this.add(new JLabel("������ip"));
		this.add(tf_ip);
		this.add(new JLabel("����"));
		this.add(tf_name);
		this.add(p_fl);
	}
	
	private void addListener() {
		//���ذ�ť����
		btn_back.addActionListener(new BackButtonListener());
		//�ύ��ť����
		btn_submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientLoginPanel cp = ClientLoginPanel.instance;
				String ip = cp.tf_ip.getText();
				String name = cp.tf_name.getText();
				
				Property.setAttribute("ip", ip);
				Property.setAttribute("name", name);
				
				// ��ת������ҳ��
				GUIUtil.showPanel(MainFrame.instance, ClientPanel.instance);
			}
		});
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(MainFrame.instance, ClientLoginPanel.instance);
	}
}
