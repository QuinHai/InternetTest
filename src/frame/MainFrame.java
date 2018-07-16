package frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import launch.BootUp;
import panel.MainPanel;
import panel.ServerPanel;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static MainFrame instance =  new MainFrame();
	
	private MainFrame() {
		this.setSize(500, 500);
		this.setTitle("Internet");
		this.setContentPane(MainPanel.instance);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if(BootUp.server != null)
						BootUp.server.destroyServer();
					if(BootUp.client != null)
						BootUp.client.destroyConnect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally{
					System.exit(0);
				}
			}
		});
	}
}
