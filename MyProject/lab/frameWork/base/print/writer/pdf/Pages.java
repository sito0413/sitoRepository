package frameWork.base.print.writer.pdf;

import java.util.ArrayList;
import java.util.List;

import frameWork.base.print.writer.pdf.PDF.Mainbody.Obj;
import frameWork.base.print.writer.pdf.PDF.Mainbody.Page;

class Pages extends Obj {
	List<Page> pageList;
	
	public Pages() {
		index = 2;
		pageList = new ArrayList<>();
	}
	
	@Override
	String getValue() {
		final StringBuilder builder = new StringBuilder();
		for (final Page page : pageList) {
			builder.append(page.index).append(" 0 R ");
		}
		return "<<\r\n/Kids [" + builder.toString() + "]\r\n/Count " + pageList.size() + "\r\n/Type /Pages\r\n>>";
	}
	
	public void addPage(final Page page) {
		pageList.add(page);
	}
}