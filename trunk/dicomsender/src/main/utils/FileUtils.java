package main.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class FileUtils {

	/**
	 * 
	 * @param defaultLocation
	 * @return
	 */
	public static String selectDirectory(String defaultLocation) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(defaultLocation));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// disable the "All files" option.
		chooser.setAcceptAllFileFilterUsed(false); 

		final int result = chooser.showDialog(null, "Select directory");
		if (result == JFileChooser.APPROVE_OPTION) {
			defaultLocation = chooser.getSelectedFile().getAbsolutePath();
			System.out.println("getCurrentDirectory(): " + defaultLocation);
		} else {
			System.out.println("No Selection");
		}
		return defaultLocation;
	}	
	
	/**
	 * Return size in bytes
	 * @param file
	 * @return
	 */
	static public long getFolderSize(File file) {
		if (file.isFile())
			return file.length();
		File[] files = file.listFiles();
		long size = 0;
		if (files != null) {
			for (int i = 0; i < files.length; i++)
				size += getFolderSize(files[i]);
		}
		
		return size;
	}
	
	/**
	 * Return formatted file size
	 * output 1.23 GB or 1.35 MB or 6.2 KB
	 * @param f
	 * @return
	 */
	static public String getFormattedSize(File f){
		
		long sizeBytes = FileUtils.getFolderSize(f);
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		String formattedSize = "";
		if (sizeBytes/1024 > 0)
			formattedSize = fmt.format((double)sizeBytes / 1024) + " KB";
		if (sizeBytes / (1024*1024) > 0)
			formattedSize = fmt.format((double)sizeBytes / (1024*1024)) + " MB";
		if (sizeBytes/(1024*1024*1024) > 0)
			formattedSize = fmt.format((double)sizeBytes / (1024*1024*1024)) + " GB";
		
		return formattedSize;
	}
	
	/**
	 * Return formatted file size
	 * output 1.23 GB or 1.35 MB or 6.2 KB
	 * @param f
	 * @return
	 */
	static public String getFormattedSize(ArrayList<String> folderList){
		
		long sizeBytes = 0;
		for (int i = 0; i < folderList.size(); i++) {
			sizeBytes = sizeBytes + FileUtils.getFolderSize(new File(folderList.get(i)));
		}
		
		DecimalFormat fmt = new DecimalFormat("#.##");
		String formattedSize = "";
		if (sizeBytes/1024 > 0)
			formattedSize = fmt.format((double)sizeBytes / 1024) + " KB";
		if (sizeBytes / (1024*1024) > 0)
			formattedSize = fmt.format((double)sizeBytes / (1024*1024)) + " MB";
		if (sizeBytes/(1024*1024*1024) > 0)
			formattedSize = fmt.format((double)sizeBytes / (1024*1024*1024)) + " GB";
		
		return formattedSize;
	}

	/**
	 * Return number for files for a folder
	 * @param folder
	 * @return
	 */
	static public long getNumberofFilesinFolder(File folder) {
		if (folder.isFile())
			return 1;
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (!(files == null))
				return files.length;
			else
				return -1; // not a file but a dir with no files ?? windows
							// mystery: happens with "My Documents"
		} else
			return -2;
	}
	
	/**
	 * Return number of files in a list of folders
	 * @param folderList
	 * @return
	 */
	static public long getTotalNumberofFilesinFolders(ArrayList<String> folderList) {
		
		long totalFiles = 0;
		for (int i = 0; i < folderList.size(); i++) {
			totalFiles = totalFiles + getNumberofFilesinFolder(new File(folderList.get(i)));
		}
		
		return totalFiles;
	}
}
