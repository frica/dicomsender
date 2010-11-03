package main.utils;

import java.awt.List;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileLister {

	private final FilenameFilter filter = null;
	private File cwd;
	private String[] entries;
	public final List list;
	
	public FileLister(String directory, FilenameFilter filter) throws IOException {
		list = new List(12, false);
		if (directory == null) 
			directory = System.getProperty("user.dir");
		list_directory(directory);
	}
	// This method uses the list() method to get all entries in a directory
	// and then displays them in the List component. 
	public void list_directory(String directory) throws IOException {
		File dir = new File(directory);

		if (!dir.isDirectory()) 
			throw new IllegalArgumentException("FileLister: no such directory");
		list.removeAll();
		cwd = dir;

		entries = cwd.list(filter);
		for(int i = 0; i < entries.length; i++) {
			list.add(entries[i]);
			System.out.println(entries[i]);
		}
	}
	
	// This method uses various File methods to obtain information about
	// a file or directory.  Then it displays that info in a TextField.
	public String show_info(String filename) throws IOException {
		File f = new File(cwd, filename);
		String info;

		if (!f.exists()) 
			throw new IllegalArgumentException("FileLister.show_info(): " +
			"no such file or directory");

		if (f.isDirectory()) info = "Directory: ";
		else info = "File: ";

		info += filename + "    ";

		return info += (f.canRead()?"read   ":"       ") + 
		(f.canWrite()?"write   ":"        ") +
		f.length() + "   " +
		new java.util.Date(f.lastModified());
	}
	
}
