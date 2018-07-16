package util;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIUtil {
	public static void setButtonString(JButton b, String tip) {
		b.setText(tip);
		b.setToolTipText(tip);
		b.setVerticalTextPosition(JButton.BOTTOM);
		b.setHorizontalTextPosition(JButton.CENTER);
	}
	
	public static void showPanel(JPanel p) {
		JFrame f = new JFrame();
		f.setSize(500, 500);
		f.setLocationRelativeTo(null);
		JPanel cp = new JPanel();
		f.setContentPane(cp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		Component[] cs = p.getComponents();
		for(Component c : cs) {
			cp.remove(c);
		}
		cp.add(p);
		
		cp.updateUI();
	}

	public static void showPanel(JFrame f, JPanel p) {
		f.setContentPane(p);
		f.setVisible(true);
	}

}
