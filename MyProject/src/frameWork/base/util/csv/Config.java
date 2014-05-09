package frameWork.base.util.csv;

public class Config {
	private char separator;
	private char quotation;
	private String encoding;
	private String lineSeparator;
	private String escape;
	
	public Config() {
		this.quotation = '"';
		this.separator = ',';
		this.encoding = "MS932";
		this.lineSeparator = "\r\n";
		this.escape = "\"\"";
	}
	
	public char getSeparator() {
		return separator;
	}
	
	public synchronized void setSeparator(final char separator) {
		this.separator = separator;
	}
	
	public char getQuotation() {
		return quotation;
	}
	
	public synchronized void setQuotation(final char quotation) {
		this.quotation = quotation;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public synchronized void setEncoding(final String encoding) {
		this.encoding = encoding;
	}
	
	public String getLineSeparator() {
		return lineSeparator;
	}
	
	public synchronized void setLineSeparator(final String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
	
	public String getEscape() {
		return escape;
	}
	
	public synchronized void setEscape(final String escape) {
		this.escape = escape;
	}
	
	@Override
	public synchronized Config clone() {
		final Config config = new Config();
		config.separator = separator;
		config.encoding = encoding;
		config.lineSeparator = lineSeparator;
		config.quotation = quotation;
		return config;
	}
}