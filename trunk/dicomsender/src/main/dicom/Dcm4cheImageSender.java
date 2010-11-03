package main.dicom;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
//import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.dcm4che.data.Dataset;

//import com.agfa.agility.tests.common.dicom.dcm4che.utils.Dcm4CheUtils;
import main.dicom.CustomException;


public class Dcm4cheImageSender implements IDcm4cheImageSender {
	
    final static Logger log = Logger.getLogger(Dcm4cheImageSender.class);
    
	private static final String LOCALHOST = "localhost";

	private static final int DEFAULT_PORT = 104;
	
	private static final String DEFAULT_REMOTE_AETITLE = "DCM4CHEE";

	private static final String DEFAULT_DEVICE_NAME = "DCMSND";

	private DcmSnd dcmsnd = null;
	
	private final String remoteAETitle;
	private final String remoteHostAddress;
	private final int remotePort;
	
	private Dcm4cheImageSender(String deviceName, String remoteAETitle, String remoteHostName, int port) throws CustomException {
		
		dcmsnd = new DcmSnd(deviceName);
		
		this.remoteAETitle = remoteAETitle;
		this.remoteHostAddress = getHostAddress(remoteHostName);
		this.remotePort = port;
	}
	
	private Dcm4cheImageSender() throws CustomException {
		
		this(DEFAULT_DEVICE_NAME, DEFAULT_REMOTE_AETITLE, LOCALHOST, DEFAULT_PORT);
	}
	
	public static Dcm4cheImageSender getInstance() throws CustomException {
		
		return new Dcm4cheImageSender();
	}
	
	public static Dcm4cheImageSender getInstance(String deviceName) throws CustomException {
		
		return new Dcm4cheImageSender(deviceName, DEFAULT_REMOTE_AETITLE, LOCALHOST, DEFAULT_PORT);
	}
	
	public static Dcm4cheImageSender getInstance(String deviceName, String remoteAETitle, String remoteHostName, int port) 
		throws CustomException{
		
		return new Dcm4cheImageSender(deviceName, remoteAETitle, getHostAddress(remoteHostName), port);
	}
	
	public static Dcm4cheImageSender getInstance(String remoteHostName, int port) throws CustomException{
	
		return new Dcm4cheImageSender(DEFAULT_DEVICE_NAME, DEFAULT_REMOTE_AETITLE, remoteHostName, port);
	}
	
	public static Dcm4cheImageSender getInstance(String remoteAETitle, String remoteHostName, int port) throws CustomException{
	
		return new Dcm4cheImageSender(DEFAULT_DEVICE_NAME, remoteAETitle, remoteHostName, port);
	}
	
	public static Dcm4cheImageSender getInstance(String deviceName, String remoteAETitle) throws CustomException{
		
		return new Dcm4cheImageSender(deviceName, remoteAETitle, LOCALHOST, DEFAULT_PORT);
	}
	
	public String getRemoteAETitle() {
		
		return this.remoteAETitle;
	}
	
	public String getRemoteHostAdress() throws CustomException  {
		
		return this.remoteHostAddress;
	}
	
	public int getRemotePort() {
		
		return remotePort;
	}
	
	
	public void send(ArrayList<String> folderList) throws CustomException {
		
		for (int i = 0; i < folderList.size(); i++)
			sendImage(new File(folderList.get(i))); // strangely doesn't work very well exception when sending 2nd dataset
	}
	
	public void send(String imagePath) throws CustomException {
		System.out.println("image path: " + imagePath);
		sendImage(new File(imagePath));
	}
	
	public void send(File file) throws CustomException {
		System.out.println("INFO image path to send: " + file.getAbsolutePath());
		sendImage(file);
	}
	
	private void sendImage(File f) throws CustomException {
		
		final boolean multipleImages = (f.isDirectory() && f.listFiles().length > 1);
		
		dcmsnd.setCalledAET(remoteAETitle);
		dcmsnd.setRemoteHost(remoteHostAddress);
		dcmsnd.setRemotePort(remotePort);
		
		dcmsnd.setOfferDefaultTransferSyntaxInSeparatePresentationContext(false);
		dcmsnd.setSendFileRef(false);
		dcmsnd.setPackPDV(true);
		dcmsnd.setTcpNoDelay(true);
		
		if (multipleImages){		
			dcmsnd.addFile(f);
			
		} else if (f.isDirectory()) {
			dcmsnd.addImage(f.listFiles()[0]);
			
		} else {
			dcmsnd.addImage(f);
		}
		
		dcmsnd.configureTransferCapability();
		dcmsnd.setStorageCommitment(false);
				
		try {
			
			try {
				
				dcmsnd.open();
				
			} catch (Exception e) {
				String message = "Failed to establish association: " + e.getMessage();
                log.error(message, e);
                throw new CustomException(message);
			}
			
			if (multipleImages) {
				dcmsnd.send();
			} else {
				
				dcmsnd.sendImage();
			}
			
		} catch (Throwable t){
		    log.error(t);
			throw new CustomException(t);
			
		} finally {
			try {
			    dcmsnd.close();
	        } catch (Throwable t){
	            // make sure and wrap and log any exception coming out of the close
	            log.error(t);
	            throw new CustomException(t);
			}
		}
	}
	
	//public void send(Dataset dataset) throws IOException, TestsCommonException {
//		
//		File directory = new File("DCM");
//		
//		File f = Dcm4CheUtils.writeDatasetToFile(dataset, directory);
//		
//		sendImage(f.getAbsoluteFile());
//		
//		f.delete();
//	}
//	
//	public void send(byte[] dcmByteArray) throws IOException, TestsCommonException{
//		
//		try {
//			Dataset ds = Dcm4CheUtils.parseDcmIntoDataset(dcmByteArray);
//			send(ds);
//		} catch (IOException e) {
//		    log.error(e);
//			throw new TestsCommonException(e);
//		}
//	}
	
//	public void send(List<Dataset> datasets) throws TestsCommonException  {
//		
//		File directory = new File("DCM");
//		
//		for (Dataset ds: datasets) {
//			try {
//				Dcm4CheUtils.writeDatasetToFile(ds, directory);
//			} catch (IOException e) {
//			    log.error(e);
//				throw new TestsCommonException(e);
//			}
//		}
//		
//		sendImage(directory);
//		
//		Dcm4CheUtils.deleteDir(directory);
//	}
	
	private static String getHostAddress(String hostName) throws CustomException {
		try {
			
			if (hostName.isEmpty() /*StringUtils.isEmpty(hostName)*/ || hostName.equals(LOCALHOST)) {
				return InetAddress.getLocalHost().getHostAddress();
			}
			return InetAddress.getByName(hostName).getHostAddress();
			
		} catch (UnknownHostException e) {
			throw new CustomException(e);
		}
	}
	
//	public static void main(String[] args) throws TestsCommonException {
//	
//		Dcm4cheImageSender.getInstance().sendImage(new File(args[0]));
//		
//	}	
}