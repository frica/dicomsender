package main.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;

import main.dicom.CustomException;
import main.dicom.Dcm4cheImageSender;
import main.dicom.SimpleDicomArchive;
import main.utils.PropertiesFileReader;
import main.utils.ServerConfigFileReader;
import main.utils.FileUtils;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.fonts.FontPolicy;
import org.pushingpixels.substance.api.fonts.FontSet;
import org.pushingpixels.substance.api.skin.ModerateSkin;

public class MainFrame implements ActionListener {

	private static final String DICOM_PORT_DEFAULT = "104";
	private static final String DICOM_PORT_TASKS = "105";
	
	private static String serverConfigLocation = System.getProperty("user.dir");
	private static String propertiesFileLocation = System.getProperty("user.dir");
	private static String defaultLocation = null; /*System.getProperty("user.dir");*/
	
	private static final String SERVER_CONFIG_FILE = serverConfigLocation + "\\server.txt";
	//private static final String SERVER_CONFIG_FILE = "C:\\Users\\Fabien\\Documents\\java\\server.txt";
	private static final String PROPERTIES_CONFIG_FILE =  propertiesFileLocation + "\\config.txt";
	
	private File defaultFile;
	//private File selectedFile = null;
	private static ArrayList<String> selectedFiles;
	private static PropertiesFileReader p;
	private ServerConfigFileReader fileReader;
	

	private DirectoryModel directoryModel;
	private ListSelectionModel listSelectionModel;

	// GUI components
	private JFrame 		mainFrame;
	private JMenuItem 	openMenuItem;
	private JMenuItem 	exitMenuItem;
	private JMenuItem 	aboutMenuItem;
	private JButton 	sendButton;
	private JComboBox 	serverCombobox;
	private JComboBox 	portCombobox;
	private JTable 		fileTable;
	private SimpleStatusBar 	statusBar;
	
	private static class WrapperFontSet implements FontSet {
		/**
		 * Extra size in pixels. Can be positive or negative.
		 */
		private int extra;

		/**
		 * The base Substance font set.
		 */
		private FontSet delegate;

		/**
		 * Creates a wrapper font set.
		 * 
		 * @param delegate
		 *            The base Substance font set.
		 * @param extra
		 *            Extra size in pixels. Can be positive or negative.
		 */
		public WrapperFontSet(FontSet delegate, int extra) {
			super();
			this.delegate = delegate;
			this.extra = extra;
		}

		/**
		 * Returns the wrapped font.
		 * 
		 * @param systemFont
		 *            Original font.
		 * @return Wrapped font.
		 */
		private FontUIResource getWrappedFont(FontUIResource systemFont) {
			return new FontUIResource(systemFont.getFontName(),
					systemFont.getStyle(), systemFont.getSize() + this.extra);
		}

		public FontUIResource getControlFont() {
			return this.getWrappedFont(this.delegate.getControlFont());
		}

		public FontUIResource getMenuFont() {
			return this.getWrappedFont(this.delegate.getMenuFont());
		}

		public FontUIResource getMessageFont() {
			return this.getWrappedFont(this.delegate.getMessageFont());
		}

		public FontUIResource getSmallFont() {
			return this.getWrappedFont(this.delegate.getSmallFont());
		}

		public FontUIResource getTitleFont() {
			return this.getWrappedFont(this.delegate.getTitleFont());
		}

		public FontUIResource getWindowTitleFont() {
			return this.getWrappedFont(this.delegate.getWindowTitleFont());
		}
	}
	
	private void createGUI() throws IOException {

		mainFrame = new JFrame("Dicom Store Scu");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// load the application icon
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
		
		JMenuBar jmb = createMenu();
		mainFrame.setJMenuBar(jmb);

		JPanel panel = new JPanel(new BorderLayout());
		JToolBar toolbar = createToolbar();
		panel.add(toolbar, BorderLayout.NORTH);

		JScrollPane pane = createTablePane();
		panel.add(pane);
		
//		DetailsPanel dp = new DetailsPanel();
//		panel.add(dp.mypane, BorderLayout.SOUTH);
		
		statusBar = createStatusBar();
		panel.add(statusBar, BorderLayout.SOUTH);

		mainFrame.setContentPane(panel);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	
	private SimpleStatusBar createStatusBar() {
		SimpleStatusBar statusBar = new SimpleStatusBar();
		statusBar.setMessage(defaultFile);
		return statusBar;
	}
	
//	private DetailsPanel createDetailPanel(){
//		DetailsPanel dp = new DetailsPanel();
//		//statusBar.setMessage(defaultFile);
//		return dp;
//	}
	
	private JScrollPane createTablePane() {

		defaultFile = new File(defaultLocation);

		// create a view of the elements that will be displayed
		directoryModel = new DirectoryModel();
		directoryModel.setDirectory(defaultFile);

		fileTable = new JTable(directoryModel);
		// TODO add a line to know when it has been sent
		// TODO multiselect of dataset

		// add the selection model to the table, allows to fire row selection
		// events
		listSelectionModel = fileTable.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new SharedListSelectionHandler());
		fileTable.setSelectionModel(listSelectionModel);

		fileTable.setShowHorizontalLines(true);
		fileTable.setShowVerticalLines(true);

		JScrollPane pane = new JScrollPane(fileTable);
		return pane;
	}

	private JToolBar createToolbar() {

		JToolBar toolbar = new JToolBar();
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		toolbar.add(sendButton);

		toolbar.addSeparator();
		JLabel labelServer = new JLabel("Server");
		toolbar.add(labelServer);
		toolbar.addSeparator();

		ArrayList<SimpleDicomArchive> serverArray = buildArchivesList();
		serverCombobox = initServerCombobox(serverArray);
		
		//check if the default server is in the server list
		boolean bDefaultInList = false;
		if (serverArray.contains(new SimpleDicomArchive(p.getDefaultServer(), "127.0.0.1"))) {
			bDefaultInList = true;	
		}

		if (!p.getDefaultServer().isEmpty() && bDefaultInList == true) {
			serverCombobox.setSelectedItem(p.getDefaultServer());
		}
		
		toolbar.add(serverCombobox, BorderLayout.CENTER);
		JLabel labelPort = new JLabel("Port");
		toolbar.addSeparator();
		toolbar.add(labelPort);
		portCombobox = new JComboBox(new String[] {DICOM_PORT_TASKS, DICOM_PORT_DEFAULT});
		
		// check if the default port is in the ports list
		if (!p.getDefaultport().isEmpty() && 
				(p.getDefaultport() == DICOM_PORT_DEFAULT || p.getDefaultport() == DICOM_PORT_TASKS)) {
			portCombobox.setSelectedItem(p.getDefaultport());
		}
		
		toolbar.addSeparator();
		toolbar.add(portCombobox, BorderLayout.CENTER);

		// TODO add a button for CEcho?

		return toolbar;
	}

	private JComboBox initServerCombobox(ArrayList<SimpleDicomArchive> serverArray) {
		JComboBox comboServer = new JComboBox();
		comboServer.removeAllItems();
		
		// fills in the combo with the names
		for (int i = 0; i < serverArray.size(); i++){
			comboServer.addItem(serverArray.get(i).getHostName());
		}
		return comboServer;
	}

	private ArrayList<SimpleDicomArchive> buildArchivesList() {
		
		try {
			fileReader = new ServerConfigFileReader(new File(SERVER_CONFIG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// builds list of servers
		ArrayList<SimpleDicomArchive> serverArray = new ArrayList<SimpleDicomArchive>();
		ArrayList<String> myArray = fileReader.getArray();
		for (int i = 0; i < fileReader.getArray().size(); i = i + 2) {
			SimpleDicomArchive archive = new SimpleDicomArchive(
					myArray.get(i), myArray.get(i + 1));
			serverArray.add(archive);
		}
		
		return serverArray;
	}

	private JMenuBar createMenu() {

		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		openMenuItem = new JMenuItem("Open directory");
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		menubar.add(fileMenu);

		JMenu optionsMenuItem = new JMenu("Options");

		// TODO add server dialog with hostname and IP + write in the file
		JMenuItem addServerMenuItem = new JMenuItem("Add server");
		optionsMenuItem.add(addServerMenuItem);
		// TODO add possibility to add new port, where to store?
		JMenuItem addPortMenuItem = new JMenuItem("Add port");
		optionsMenuItem.add(addPortMenuItem);
		menubar.add(optionsMenuItem);

		JMenu helpMenu = new JMenu("Help");
		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		menubar.add(helpMenu);

		openMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		addServerMenuItem.addActionListener(this);
		addPortMenuItem.addActionListener(this);
		
		return menubar;
	}

	// private JList createList() throws IOException {
	//
	// FileLister filelister = new FileLister(null, null);
	// DefaultListModel lm = new DefaultListModel();
	//
	// for (int i=0; i < filelister.list.getItemCount(); i++)
	// lm.addElement(filelister.list.getItem(i));
	//
	// JList list = new JList(lm);
	// list.setCellRenderer(new CustomCellRenderer());
	//
	// JScrollPane pane = new JScrollPane(list);
	//
	// DefaultListSelectionModel m = new DefaultListSelectionModel();
	// m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	// m.setLeadAnchorNotificationEnabled(false);
	// list.setSelectionModel(m);
	// list.setLayout(new BorderLayout());
	// return list;
	// }

	public void actionPerformed(ActionEvent e) {
		String comStr = e.getActionCommand();
		System.out.println(comStr + " Selected");

		if (e.getSource() == exitMenuItem) {
			System.exit(0);
		} else if (e.getSource() == aboutMenuItem) {
			
			SimpleAboutDialog aboutDialog = new SimpleAboutDialog(mainFrame);
			aboutDialog.showCentered();
			
		} else if (e.getSource() == openMenuItem) {
			
			defaultLocation = FileUtils.selectDirectory(defaultLocation);
			refreshGUI();
			
		} else if (e.getSource() == sendButton) {
			try {
				String selectedHostname = (String) serverCombobox.getSelectedItem();
				String selectedPort = (String) portCombobox.getSelectedItem();
				
				sendButton.setEnabled(false);
		        mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				Dcm4cheImageSender dcmSender = 
					Dcm4cheImageSender.getInstance(selectedHostname, Integer.parseInt(selectedPort));
				dcmSender.send(selectedFiles);
				
				sendButton.setEnabled(true);
				mainFrame.setCursor(Cursor.getDefaultCursor());

			} catch (CustomException e1) {
				e1.printStackTrace();
				statusBar.setMessage("Error sending data...");
				
				sendButton.setEnabled(true);
				mainFrame.setCursor(Cursor.getDefaultCursor());
				
			} finally {
				// if any other exception occurs
				sendButton.setEnabled(true);
				mainFrame.setCursor(Cursor.getDefaultCursor());
			}

		}
	}

	private void refreshGUI() {
		defaultFile = new File(defaultLocation);
		directoryModel.setDirectory(defaultFile);
		statusBar.setMessage(defaultFile);
	}

	public static void main(String args[]) throws Exception {

		// to make substance work it has to work on the EDT thread and
		// that's why the main is like this
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// doesn't stick to OS l&f for main frame
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);

					// supposed to help for flickering issues
					System.setProperty("sun.awt.noerasebackground", "true");

					// see
					// https://substance.dev.java.net/docs/skins/toneddown.html
					UIManager.setLookAndFeel(new CustomLookAndFeel(
							new ModerateSkin()));
					// SubstanceLookAndFeel.setSkin(new ModerateSkin());

					// see
					// https://substance.dev.java.net/docs/api/SetFontPolicy.html
					SubstanceLookAndFeel.setFontPolicy(null);
					final FontSet substanceCoreFontSet = SubstanceLookAndFeel
							.getFontPolicy().getFontSet("Substance", null);

					FontPolicy newFontPolicy = new FontPolicy() {
						public FontSet getFontSet(String lafName,
								UIDefaults table) {
							// adds 2 pixels to all fonts
							return new WrapperFontSet(substanceCoreFontSet, 2); 
						}
					};

					SubstanceLookAndFeel.setFontPolicy(newFontPolicy);

					// produces some NPE
					// SwingUtilities.updateComponentTreeUI(chooser);
					// SwingUtilities.updateComponentTreeUI(myframe);

					initializeProperties();
					
					MainFrame myframe = new MainFrame();
					myframe.createGUI();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void initializeProperties() throws IOException {
				
				try {
					p = new PropertiesFileReader(new File(PROPERTIES_CONFIG_FILE));
					defaultLocation = p.getDefaultFolder();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					
					// fallback for the default folder
					defaultLocation = System.getProperty("user.dir");
					System.out.println("WARNING Properties files not found, switching the default location to " + defaultLocation + "!");
				}
				
				selectedFiles = new ArrayList<String>();
			}
		});
	}

	class SharedListSelectionHandler implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {

			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			if (lsm.isSelectionEmpty()) {
				statusBar.setMessage("");
			} else {
				
				selectedFiles.clear();				
				final int[] indexSelected = fileTable.getSelectedRows();
				
				for (int i = 0; i < indexSelected.length; i++) {
					String path = (String) fileTable.getValueAt(indexSelected[i], 1);
					path = defaultLocation + "\\" + path;
					selectedFiles.add(path);
					System.out.println("INFO: file added " + path);
				}

				statusBar.setMessage(/*Integer.toString(lsm
						.getMinSelectionIndex())*/
						"Nbr of Files : "
						+ FileUtils.getTotalNumberofFilesinFolders(selectedFiles)
						+ " Size : "
						+ FileUtils.getFormattedSize(selectedFiles));
			}
		}
	}
}