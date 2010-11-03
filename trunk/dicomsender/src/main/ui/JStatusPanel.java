package main.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;

public class JStatusPanel extends JPanel {

	private JPanel contentPanel;
	private FormLayout layout;
	private Integer layoutCoordinateX = 1;
	private Integer layoutCoordinateY = 1; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JStatusPanel( ){
		
        setPreferredSize(new Dimension(getWidth( ), 23));
        JLabel resizeIconLabel = new JLabel("icon"); 
        //resizeIconLabel.setOpaque(false);

        JPanel rightPanel = new JPanel(new BorderLayout( ));
        rightPanel.setOpaque(false);
        rightPanel.add(resizeIconLabel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);

        contentPanel = new JPanel( );
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.CENTER);

        layout = new FormLayout(
                "2dlu, pref:grow",
                "3dlu, fill:10dlu, 2dlu");

        contentPanel.setLayout(layout); 

    }
	
	public void setMainLeftComponent(JComponent component){
        contentPanel.add(component, new CellConstraints(2, 2));
    }
	
	public void addRightComponent(JComponent component, int dialogUnits){ 
		layout.appendColumn(new ColumnSpec("2dlu")); 
		layout.appendColumn(new ColumnSpec(dialogUnits + "dlu"));

		layoutCoordinateX++;
		contentPanel.add( 
				new SeparatorPanel(Color.GRAY, Color.WHITE), 
				new CellConstraints(layoutCoordinateX, layoutCoordinateY)
		); 
		
		layoutCoordinateX++;
		contentPanel.add(
				component,
				new CellConstraints(layoutCoordinateX, layoutCoordinateY)
		);
	}
	
	public void paintComponent(Graphics g) { 
        super.paintComponent(g);

        int y = 0;
        g.setColor(new Color(156, 154, 140));
        g.drawLine(0, y, getWidth( ), y);
        y++;
        g.setColor(new Color(196, 194, 183));
        g.drawLine(0, y, getWidth( ), y);
        y++;
        g.setColor(new Color(218, 215, 201));
        g.drawLine(0, y, getWidth( ), y);
        y++;
        g.setColor(new Color(233, 231, 217));
        g.drawLine(0, y, getWidth( ), y);
        
        y = getHeight( ) - 3;
        g.setColor(new Color(233, 232, 218));
        g.drawLine(0, y, getWidth( ), y);
        y++;
        g.setColor(new Color(233, 231, 216));
        g.drawLine(0, y, getWidth( ), y);
        y = getHeight( ) - 1;
        g.setColor(new Color(221, 221, 220));
        g.drawLine(0, y, getWidth( ), y);

    }
	
	public class SeparatorPanel extends JPanel { 
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Color leftColor; 
		private Color rightColor;

		public SeparatorPanel(Color left, Color right) {
			this.leftColor = left;
			this.rightColor = right;
			setOpaque(false);
		}

		protected void paintComponent(Graphics g) {
			g.setColor(leftColor);
			g.drawLine(0,0, 0,getHeight( ));
			g.setColor(rightColor);
			g.drawLine(1,0, 1,getHeight( ));

		}
	}
}



