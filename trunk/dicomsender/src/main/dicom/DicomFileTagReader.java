package main.dicom;

import java.io.File;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

public class DicomFileTagReader {

	public DicomFileTagReader(){
		
	}
	
	public static void main(String[] args) {
		   DicomObject object = null;  
		   try {
		      DicomInputStream dis = new DicomInputStream(new File("C:\\Users\\Fabien\\Documents\\java\\CETAUTOMATIX\\Cardiovascular Heart-Cardiac Function\\cine_retro_aortic valve/IM-0001-0001.dcm"));
		      object = dis.readDicomObject();
		      dis.close();
		   } catch (Exception e) {
		      System.out.println(e.getMessage());
		      System.exit(0);
		   }
		   ListDicomHeader list = new ListDicomHeader();
		   list.listHeader(object);
		}

}





