package main.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ServerConfigFileReader {
	
	ArrayList<String> fileArray = new ArrayList<String>();
	
	public ServerConfigFileReader(File f) throws Exception{
		
		addDefaultServer();
		readFromFile(f);
	}

	private void readFromFile(File f) throws IOException {
		
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.out.println("WARNING Server list not found, only default server will be available!");
			return;
		}
		
		String line = "";
			
		 while ((line = buffer.readLine()) != null) {
			 
			 // skip lines with comments
			 if (line.trim().charAt(0) == '#'){
				 continue;
			 }
			 
			 String[] theline = line.split("\t");
			 fileArray.add(theline[0]);
			 fileArray.add(theline[1]);
		 }
		 
		 buffer.close();
	}

	private void addDefaultServer() {
		fileArray.add("localhost");
		fileArray.add("127.0.0.1");
	}
	
	public ArrayList<String> getArray(){
		return fileArray;
	}
}
