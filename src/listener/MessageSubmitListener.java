package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import launch.BootUp;
import panel.ChatPanel;
import properties.Property;

public class MessageSubmitListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String date = df.format(new Date());
		
		String msg = ChatPanel.instance.tf_message.getText();
		String info = "[ " + date + " ]" + Property.LOCAL_IP + ":  " + Property.getAttribute("name") + ":" + Property.LINE_SEPARATOR;
		
		msg = info + msg;
		//����Ƿ�����������Ϣ�ַ��������ͻ���
		//����ǿͻ��ˣ�����Ϣ�ϴ�������������ȡ��
		try {
			if(ChatPanel.instance.isServer) {
				ChatPanel.instance.appendChatMessage(msg);
				BootUp.server.sendMessage(msg);
			}else {
				BootUp.client.uploadMessage(msg);
			}
		}catch (Exception e1) {
			ChatPanel.instance.appendChatMessage("��Ϣ�ύʧ��..." + Property.LINE_SEPARATOR);
			ChatPanel.instance.appendChatMessage(e1.getMessage() + Property.LINE_SEPARATOR);
		}
		ChatPanel.instance.tf_message.setText("");
	}
}
