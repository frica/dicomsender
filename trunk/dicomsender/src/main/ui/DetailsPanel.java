package main.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

public class DetailsPanel {

	public JSplitPane mypane = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimpleStatusBar statusBar;

	public DetailsPanel(){
		buildPanel();
	}

	private void buildPanel(){
		
		final JPanel jp = new JPanel(new BorderLayout());
		JTextField tf = new JTextField("Data");
		jp.add(tf, BorderLayout.WEST);
//		final JScrollPane sp = new JScrollPane(jp);
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(300,100));
		
		//JLabel lbl = new JLabel("Test ! Test !",UIManager.getIcon("OptionPane.warningIcon"),JLabel.LEFT);
		JButton btn1 = new JButton("OK");
		final JToggleButton btn2 = new JToggleButton("<<Details");
		btn2.setMargin(new Insets(0,0,0,0));
		btn1.setPreferredSize(btn2.getPreferredSize());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(btn1);
		p1.add(btn2);
		//p.add(lbl,BorderLayout.NORTH);
		p.add(p1,BorderLayout.SOUTH);
		//sp.setPreferredSize(new Dimension(300,100));
		
		final JSplitPane spt = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p,null);
		spt.setDividerSize(5);
		
//		final JPanel jp = new JPanel(new BorderLayout());
//		JTextField tf = new JTextField("Data");
//		jp.add(tf, BorderLayout.WEST);
//
//		JPanel p = new JPanel(new BorderLayout());
//		p.setPreferredSize(new Dimension(300,100));
//
//		statusBar = new StatusBar();
//		statusBar.setMessage("my details panel");
//		final JToggleButton btn2 = new JToggleButton("Details");
//		btn2.setMargin(new Insets(0,0,0,0));
//
////		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT)/*new BorderLayout()*/);
////		p1.add(btn2);
////		p1.add(statusBar, BorderLayout.WEST);
////		p.add(p1, BorderLayout.EAST);
////		//sp.setPreferredSize(new Dimension(300,100));
////
////		mypane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p, null);
//		mypane.setDividerSize(3);
//		
//		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		p1.add(btn2);
//		p.add(statusBar,BorderLayout.WEST);
//		p.add(p1,BorderLayout.EAST);
//		//sp.setPreferredSize(new Dimension(300,100));
//
//		mypane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p,null);

		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				mypane.setBottomComponent(btn2.isSelected()? jp:null);
				//mypane.pack();
			}
		});
		//return spt;
	}
}
