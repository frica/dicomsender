package main.ui;

/*
The MIT License

Copyright (c) 2007 halfdecent.net

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;

/**
 * Provides a bar of recessed panels that can be used as a status bar.
 * 
 * @author mjs
 *
 */
public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	private StatusBarPanel[] panels = null;

	/**
	 * This is the default constructor. The default panel array 
	 * is an array of two unspecified, unfixed panels;
	 */
	public StatusBar() {
		super();
		this.panels = new StatusBarPanel[] { new StatusBarPanel(), new StatusBarPanel() };
		initialize();
	}

	/**
	 * Allows you to set the StatusBarPanel array;
	 * @param panels 
	 */
	public StatusBar(StatusBarPanel[] panels) {
		super();
		this.panels = panels;
		initialize();
	}
	
	/**
	 * shared by two constructors.
	 */
	private void initialize() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets = new Insets(0, 0, 0, 2);
		this.setLayout(new GridBagLayout());
		for (int i=0; i<panels.length; i++) {
			if (i==panels.length-1) constraints.insets=new Insets(0, 0, 0, 0);
			if (i==1) constraints.anchor=GridBagConstraints.CENTER;
			constraints.ipadx = (panels[i].getPanelWidth()==-1) ? 0 : panels[i].getPanelWidth();
			if (panels[i].isWidthFixed()) {
				constraints.fill=GridBagConstraints.NONE;
				constraints.weightx = 0;
			} else {
				constraints.fill=GridBagConstraints.HORIZONTAL;
				constraints.weightx = 100;
			}
			constraints.gridx=i;
			this.add(panels[i], constraints);
		}
	}

	public StatusBarPanel[] getPanels() {
		return panels;
	}

	public void setPanels(StatusBarPanel[] panels) {
		this.panels = panels;
		initialize();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
