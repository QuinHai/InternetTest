package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import frame.MainFrame;
import launch.BootUp;
import panel.ClientPanel;
import panel.MainPanel;
import panel.ServerPanel;

public class BackButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(BootUp.server != null)
				BootUp.server.destroyServer();
			if(BootUp.client != null)
				BootUp.client.destroyConnect();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	
		ServerPanel.reload();
		ClientPanel.reload();
		
		MainFrame mf = MainFrame.instance;
		mf.setContentPane(MainPanel.instance);
		mf.setVisible(true);
	}
}
