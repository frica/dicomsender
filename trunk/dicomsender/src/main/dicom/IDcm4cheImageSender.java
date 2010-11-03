package main.dicom;

import main.dicom.CustomException;

public interface IDcm4cheImageSender {

	public void send(String imagePath) throws CustomException;
	
//	public void send(Dataset dataset) throws IOException, TestsCommonException;
	
//	public void send(byte[] dcmByteArray) throws IOException, TestsCommonException;
	
//	public void send(List<Dataset> datasets) throws TestsCommonException;
}
