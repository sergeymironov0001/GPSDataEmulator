package foo;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Frame extends JFrame {
	public Frame() {
		super();
		initialize();
	}

	private void initialize() {
		JTextArea textArea = new JTextArea(5000, 5000);
		textArea.setAutoscrolls(true);

		MouseMotionListener doScrollRectToVisible = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
				((JTextArea) e.getSource()).scrollRectToVisible(r);
			}
		};
		textArea.addMouseMotionListener(doScrollRectToVisible);

		// JPanel panel = new JPanel(new BorderLayout());
		// panel.setPreferredSize(new Dimension(500, 500));
		JScrollPane scrollPanel = new JScrollPane(textArea);

		scrollPanel.setAutoscrolls(true);
		setContentPane(scrollPanel);
	}
}
