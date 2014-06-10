package frameWork.base.print.writer;

import java.util.ArrayList;

import frameWork.base.print.ReportException;
import frameWork.base.print.element.Page;

public abstract class Writer extends ArrayList<Page> {
	public abstract void print() throws ReportException;
	
	public void preview() throws ReportException {
		throw new ReportException("未実装");
	}
}