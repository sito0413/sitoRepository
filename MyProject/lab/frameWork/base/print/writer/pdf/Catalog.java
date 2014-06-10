package frameWork.base.print.writer.pdf;

import frameWork.base.print.writer.pdf.PDF.Mainbody.Obj;

class Catalog extends Obj {
	int pagesIndex;
	
	public Catalog() {
		index = 1;
		pagesIndex = 2;
	}
	
	@Override
	String getValue() {
		return "<<\r\n/Pages " + pagesIndex + " 0 R\r\n/Type /Catalog\r\n>>";
	}
	
}