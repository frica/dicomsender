package main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {

	public Properties p = null;
	
	public  PropertiesFileReader(File f) throws FileNotFoundException, IOException {
		
		p = new Properties();
		p.load(new FileInputStream(f));
		p.list(System.out);
	}
	
	public String getDefaultFolder(){
		return p.getProperty("defaultfolder");
	}
	
	public String getDefaultport(){
		return p.getProperty("defaultport");
	}

	public String getDefaultServer() {
		return p.getProperty("defaultserver");
	}
}
