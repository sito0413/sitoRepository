package frameWork.core;

class Textlet {
	final String text;
	final TextletType textletType;
	
	Textlet(final TextletType textletType, final String text) {
		this.textletType = textletType;
		this.text = text == null ? "" : text;
	}
}