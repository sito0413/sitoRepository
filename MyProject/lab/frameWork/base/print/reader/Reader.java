package frameWork.base.print.reader;

import java.net.URI;

import frameWork.base.print.ReportException;
import frameWork.base.print.element.Page;

public interface Reader {
	
	Page readSheet(URI uri, String sheetName) throws ReportException;
	
}
