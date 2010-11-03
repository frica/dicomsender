package main.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class SimpleStatusBar extends JLabel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of StatusBar */
    public SimpleStatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("Ready");
        JSeparator sepLine= new JSeparator(JSeparator.HORIZONTAL);
        this.add(sepLine);

    }
    
    public void setMessage(String message) {
        setText(" " + message);        
    }        
    
    public void setMessage(File file) {
        setText(" " + file.getAbsolutePath());        
    } 
}