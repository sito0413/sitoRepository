package sito.archive.connection.valueManager.text;

import java.io.File;

import sito.archive.Value;

class TextValue extends Value {
	protected final String value;
	
	public TextValue(final String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public void open(final File dirPath) {
		//NOP
	}
	
	@Override
	public void exe() {
		//NOP
	}
}