package main.dicom;

import java.util.Iterator;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.util.TagUtils;

public class ListDicomHeader {

	   public ListDicomHeader() {

	   }
	   
	   public static void main(String[] args) {

	   }
	   
	   public void listHeader(DicomObject object) {
		   Iterator<DicomElement> iter = object.datasetIterator();
		   while(iter.hasNext()) {
		      DicomElement element = iter.next();
		      int tag = element.tag();
		      if (tag == Tag.PatientName)
		      
		      try {
		         String tagName = object.nameOf(tag);
		         String tagAddr = TagUtils.toString(tag);          
		         String tagValue = object.getString(tag);    
		         
		         System.out.println(tagAddr + tagName +" ["+ tagValue+"]");
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }  
		}
	}