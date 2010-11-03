package main.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SimpleAboutDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SimpleAboutDialog(JFrame parent) {
		super(parent, "About Dialog", true);

		String message = "Simple DICOM Store SCU\n" + "based on dcm4che\n" + "Author: Fabien Gatell-Rica";
		JOptionPane pane = new JOptionPane(message);
		getContentPane().add(pane, "Center");

		JPanel p2 = new JPanel();
		JButton ok = new JButton("OK");
		p2.add(ok);
		getContentPane().add(p2, "South");

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});

		setSize(250, 150);
	}

	public void showCentered() {
		Frame parent = (Frame)getParent();
		Dimension dim = parent.getSize();
		Point     loc = parent.getLocationOnScreen();

		Dimension size = getSize();

		loc.x += (dim.width  - size.width)/2;
		loc.y += (dim.height - size.height)/2;

		if (loc.x < 0) loc.x = 0;
		if (loc.y < 0) loc.y = 0;

		Dimension screen = getToolkit().getScreenSize();

		if (size.width  > screen.width)
			size.width  = screen.width;
		if (size.height > screen.height)
			size.height = screen.height;

		if (loc.x + size.width > screen.width)
			loc.x = screen.width - size.width;

		if (loc.y + size.height > screen.height)
			loc.y = screen.height - size.height;

		setBounds(loc.x, loc.y, size.width, size.height);

		setVisible(true);
	}
}