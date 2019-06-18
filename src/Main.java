import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Ballz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Game pongPanel = new Game();
		frame.add(pongPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(true);
	    frame.setSize((int) (screenSize.width/2),screenSize.height);		
	    pongPanel.init(screenSize);
	    frame.setVisible(true);
		
	}

}
