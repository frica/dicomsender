package main.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MyExpandablePanel extends TestExpandable {

	public void buildGUI()
	{
		final JPanel jp = new JPanel(new BorderLayout());
		JTextField tf = new JTextField("Data");
		jp.add(tf, BorderLayout.WEST);
//		final JScrollPane sp = new JScrollPane(jp);
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(300,100));
		
		JLabel lbl = new JLabel("Test ! Test !",UIManager.getIcon("OptionPane.warningIcon"),JLabel.LEFT);
		JButton btn1 = new JButton("OK");
		final JToggleButton btn2 = new JToggleButton("<<Details");
		btn2.setMargin(new Insets(0,0,0,0));
		btn1.setPreferredSize(btn2.getPreferredSize());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(btn1);
		p1.add(btn2);
		p.add(lbl,BorderLayout.NORTH);
		p.add(p1,BorderLayout.SOUTH);
		//sp.setPreferredSize(new Dimension(300,100));
		
		final JSplitPane spt = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p,null);
		spt.setDividerSize(5);

		final JDialog d = new JDialog();
		d.setModal(true);
		d.getContentPane().add(spt);
		d.pack();
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				spt.setBottomComponent(btn2.isSelected()? jp:null);
				d.pack();
			}
		
		
//		JTextArea ta = new JTextArea("Hello World",20,50);
//		final JScrollPane sp = new JScrollPane(ta);
//		JPanel p = new JPanel(new BorderLayout());
//		p.setPreferredSize(new Dimension(300,100));
//		JLabel lbl = new JLabel("Warning! Warning!",UIManager.getIcon("OptionPane.warningIcon"),JLabel.LEFT);
//		JButton btn1 = new JButton("OK");
//		final JToggleButton btn2 = new JToggleButton("<<Details");
//		btn2.setMargin(new Insets(0,0,0,0));
//		btn1.setPreferredSize(btn2.getPreferredSize());
//		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		p1.add(btn1);
//		p1.add(btn2);
//		p.add(lbl,BorderLayout.NORTH);
//		p.add(p1,BorderLayout.SOUTH);
//		sp.setPreferredSize(new Dimension(300,100));
//
//		final JSplitPane spt = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p,null);
//		spt.setDividerSize(0);
//
//		final JDialog d = new JDialog();
//		d.setModal(true);
//		d.getContentPane().add(spt);
//		d.pack();
//		d.setLocationRelativeTo(null);
//		d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//		btn2.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent ae){
//				spt.setBottomComponent(btn2.isSelected()? sp:null);
//				d.pack();
//			}
		});

		d.setVisible(true);
		System.exit(0);
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MyExpandablePanel().buildGUI();
			}
		});
	}
}
