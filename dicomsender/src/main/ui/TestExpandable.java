package main.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class TestExpandable
{
	public void buildGUI()
	{
		JTextArea ta = new JTextArea("Hello World",20,50);
		final JScrollPane sp = new JScrollPane(ta);
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(300,100));
		JLabel lbl = new JLabel("Warning! Warning!",UIManager.getIcon("OptionPane.warningIcon"),JLabel.LEFT);
		JButton btn1 = new JButton("OK");
		final JToggleButton btn2 = new JToggleButton("<<Details");
		btn2.setMargin(new Insets(0,0,0,0));
		btn1.setPreferredSize(btn2.getPreferredSize());
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(btn1);
		p1.add(btn2);
		p.add(lbl,BorderLayout.NORTH);
		p.add(p1,BorderLayout.SOUTH);
		sp.setPreferredSize(new Dimension(300,100));

		final JSplitPane spt = new JSplitPane(JSplitPane.VERTICAL_SPLIT,p,null);
		spt.setDividerSize(0);

		final JDialog d = new JDialog();
		d.setModal(true);
		d.getContentPane().add(spt);
		d.pack();
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				spt.setBottomComponent(btn2.isSelected()? sp:null);
				d.pack();
			}
		});

		d.setVisible(true);
		System.exit(0);
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new TestExpandable().buildGUI();
			}
		});
	}
}

