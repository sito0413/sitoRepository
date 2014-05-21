package frameWork.base.print.writer.pdf;

class Header {
	String version = "1.2";
	
	public String toByteCode() {
		return "%PDF-" + version + "\r\n\r\n";
	}
}