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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * A recessed JPanel decorated with two extra fields, for use in a StatusBar container.
 * The panelWidth field indicates the default width of the status bar and defaults to -1 (unspecified).
 * The widthFixed field indicates whether or not you wish the panel to stretch when the 
 * status bar is resized and defaults to true;
 *  
 * @author mjs
 *
 */
public class StatusBarPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int panelWidth = -1;
	private boolean widthFixed = false;
	public StatusBarPanel() {
		super();
		//this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	public int getPanelWidth() {
		return panelWidth;
	}
	public void setPanelWidth(int panelWidth) {
		this.panelWidth = panelWidth;
	}
	public boolean isWidthFixed() {
		return widthFixed;
	}
	public void setWidthFixed(boolean widthFixed) {
		this.widthFixed = widthFixed;
	}
}
