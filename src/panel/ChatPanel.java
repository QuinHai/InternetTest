package panel;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import frame.MainFrame;
import listener.MessageSubmitListener;
import properties.Property;
import util.GUIUtil;

public class ChatPanel extends JPanel{
	
	public static ChatPanel instance = new ChatPanel();
	public JButton btn_submit = new JButton();
	public JTextField tf_message = new JTextField();
	public JTextArea ta_chat = new JTextArea();
	public JScrollPane sp_chat = new JScrollPane(ta_chat);
	public boolean isServer;
	
	private ChatPanel() {
		GUIUtil.setButtonString(btn_submit, "Ã·Ωª");
		ta_chat.setEditable(false);
		sp_chat.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp_chat.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel p_below = new JPanel(new BorderLayout());
		p_below.add(tf_message, BorderLayout.CENTER);
		p_below.add(btn_submit, BorderLayout.EAST);
		
		addListener();
		
		this.setLayout(new BorderLayout());
		this.add(sp_chat, BorderLayout.CENTER);
		this.add(p_below, BorderLayout.SOUTH);
		
	}

	private void addListener() {
		MessageSubmitListener listener = new MessageSubmitListener();
		
		btn_submit.addActionListener(listener);
		tf_message.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					btn_submit.doClick();
					tf_message.setText("");
				}
			}
		});
	}

	public void appendChatMessage(String msg) {
		String old = ta_chat.getText();
		String now = old + Property.LINE_SEPARATOR + msg;
		ta_chat.setText(now);
		this.updateUI();
	}
	public static void main(String[] args) {
		GUIUtil.showPanel(MainFrame.instance, ChatPanel.instance);
	}
}
