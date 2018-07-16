package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.MainFrame;
import panel.ClientLoginPanel;
import panel.MainPanel;
import panel.ServerPanel;
import util.GUIUtil;

public class MainPanelButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		MainPanel mp = MainPanel.instance;
		JButton arg = (JButton) e.getSource();
		
		if(mp.btn_server == arg) {
			GUIUtil.showPanel(MainFrame.instance, ServerPanel.instance);
		}else if(mp.btn_client == arg) {
			GUIUtil.showPanel(MainFrame.instance, ClientLoginPanel.instance);
		}
	}
}
