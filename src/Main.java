import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Ballz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		Game pongPanel = new Game();
		frame.add(pongPanel, BorderLayout.CENTER);

		frame.setSize(1000, 2000);
		frame.setVisible(true);

	}

}
