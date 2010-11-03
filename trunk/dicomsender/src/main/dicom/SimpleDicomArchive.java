package main.dicom;

public class SimpleDicomArchive {

	private String hostName;
	private String ipAddress;
	private String AET;
	
	public SimpleDicomArchive(String hostName, String ipAddress, String AET) {
		this.setHostName(hostName);
		this.setIpAddress(ipAddress);
		this.setAET(AET);
	}
	
	public SimpleDicomArchive(String hostName, String ipAddress) {
		this.setHostName(hostName);
		this.setIpAddress(ipAddress);
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setAET(String AET) {
		this.AET = AET;
	}

	public String getAET() {
		return AET;
	}
}
