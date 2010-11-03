package main.dicom;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomException(String error, Exception e) {
		super(error, e);
//		Dcm4cheImageSender.log.equals(error);
//		Dcm4cheImageSender.log.equals(e.getMessage());
	}
	
	public CustomException(String error) {
		super(error);
//		Dcm4cheImageSender.log.equals(error);
	}
	
	public CustomException(Exception e) {
		super(e);
//		Dcm4cheImageSender.log.equals(e.getMessage());
	}

	public CustomException(Throwable t) {
		super(t);
//		Dcm4cheImageSender.log.equals(t.getMessage());
	}
}
