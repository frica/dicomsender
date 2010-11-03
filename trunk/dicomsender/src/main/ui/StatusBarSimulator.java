package main.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class StatusBarSimulator {

    public static void main(String[] args) { 
        
        JFrame frame = new JFrame( );
        frame.setBounds(200,200, 600, 200);
        frame.setTitle("Status bar simulator");

        Container contentPane = frame.getContentPane( );    
        contentPane.setLayout(new BorderLayout( ));

        JStatusPanel statusBar = new JStatusPanel( );

        JLabel leftLabel = 
            new JLabel("Your application is about to self destruct.");
        statusBar.setMainLeftComponent(leftLabel); 

        JLabel dateLabel = new JLabel("12/31/99");
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        statusBar.addRightComponent(dateLabel, 30);

        JLabel timeLabel = new JLabel("11:59 PM");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        statusBar.addRightComponent(timeLabel, 30);

        contentPane.add(statusBar, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
        frame.show( );
    }
}
