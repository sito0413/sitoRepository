package frameWork.base.print.writer.pdf;

import java.util.ArrayList;
import java.util.List;

import frameWork.base.print.writer.pdf.PDF.Mainbody.Obj;
import frameWork.base.print.writer.pdf.PDF.Mainbody.Page;

@SuppressWarnings("nls")
public class PDF {
	public static void main(final String[] args) {
		final PDF pdf = new PDF();
		pdf.mainbody.addPage(new Page());
		System.out.println(pdf);
	}
	
	Header header = new Header();
	Mainbody mainbody = new Mainbody();
	
	static class Mainbody {
		static class Obj {
			private final String value;
			int index = 0;
			
			public Obj() {
				this.value = "";
			}
			
			public Obj(final String value) {
				this.value = value;
			}
			
			String getValue() {
				return value;
			}
			
			@Override
			public String toString() {
				return index + " 0 obj" + "\r\n" + value + "\r\n" + "endobj" + "\r\n";
			}
			
			public String toByteCode() {
				return index + " 0 obj" + "\r\n" + getValue() + "\r\n" + "endobj" + "\r\n";
			}
			
			public void setIndex(final int index) {
				this.index = index;
			}
			
			public int getIndex() {
				return index;
			}
		}
		
		static class Page extends Obj {
			List<Obj> objList = new ArrayList<>();
			
			public Page() {
				objList.add(new Obj(
				        "<<\r\n/Font\r\n<<\r\n/Fo\r\n<<\r\n/BaseFont /Times-Italic\r\n/Subtype /Type1\r\n/Type /Font\r\n>>\r\n>>\r\n>>"));
				objList.add(new Obj("<<" + "\r\n" + "/Length 65" + "\r\n" + ">>" + "\r\n" + "stream" + "\r\n"
				        + "1. 0. 0. 1. 50. 700. cm" + "\r\n" + "BT" + "\r\n" + " /FO 14. Tf" + "\r\n"
				        + " (Hello, World) Tj" + "\r\n" + "ET" + "\r\n" + "endstream"));
			}
			
			@Override
			String getValue() {
				return "<<" + "\r\n" + "/Parent 2 0 R" + "\r\n" + "/Resources 4 0 R" + "\r\n"
				        + "/MediaBox [0 0 612 792]" + "\r\n" + "/Contents [5 0 R]" + "\r\n" + "/Type /Pages" + "\r\n"
				        + ">>";
			}
		}
		
		int index = 3;
		Catalog catalog = new Catalog();
		Pages pages = new Pages();
		
		public void addPage(final Page page) {
			pages.addPage(page);
			page.setIndex(index++);
			for (final Obj obj : page.objList) {
				obj.setIndex(index++);
			}
		}
		
		public String toByteCode() {
			final StringBuilder builder = new StringBuilder();
			builder.append(catalog.toByteCode());
			builder.append(pages.toByteCode());
			for (final Page page : pages.pageList) {
				builder.append(page.toByteCode());
				for (final Obj obj : page.objList) {
					builder.append(obj.toByteCode());
				}
			}
			return builder.toString();
		}
	}
	
	class CrossReferenceTable {
		private final Header header;
		private final Mainbody mainbody;
		
		public CrossReferenceTable(final Header header, final Mainbody mainbody) {
			this.header = header;
			this.mainbody = mainbody;
		}
		
		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder("xref\r\n0 ").append(mainbody.index).append("\r\n")
			        .append(String.format("%010d", 0)).append(" 65535 f").append("\r\n");
			int offset = header.toByteCode().getBytes().length;
			
			builder.append(String.format("%010d", offset)).append(" 00000 n").append("\r\n");
			offset += mainbody.catalog.toByteCode().getBytes().length;
			builder.append(String.format("%010d", offset)).append(" 00000 n").append("\r\n");
			offset += mainbody.pages.toByteCode().getBytes().length;
			for (final Page page : mainbody.pages.pageList) {
				builder.append(String.format("%010d", offset)).append(" 00000 n").append("\r\n");
				offset += page.toString().getBytes().length;
				for (final Obj obj : page.objList) {
					builder.append(String.format("%010d", offset)).append(" 00000 n").append("\r\n");
					offset += obj.toString().getBytes().length;
				}
			}
			return builder.toString();
		}
	}
	
	class Trailer {
		private final CrossReferenceTable crossReferenceTable;
		
		public Trailer(final CrossReferenceTable crossReferenceTable) {
			this.crossReferenceTable = crossReferenceTable;
		}
		
		@Override
		public String toString() {
			return "trailer" + "\r\n" + "<<" + "\r\n" + "/Root " + crossReferenceTable.mainbody.catalog.index + " 0 R"
			        + "\r\n" + "/Size " + (crossReferenceTable.mainbody.index) + "\r\n" + ">>" + "\r\n" + "startxref"
			        + "\r\n" + crossReferenceTable.toString().getBytes().length + "\r\n" + "%%EOF" + "\r\n";
		}
	}
	
	@Override
	public String toString() {
		// TODO 自動生成されたメソッド・スタブ
		final CrossReferenceTable crossReferenceTable = new CrossReferenceTable(header, mainbody);
		return header.toByteCode() + mainbody.toByteCode() + crossReferenceTable.toString()
		        + new Trailer(crossReferenceTable).toString();
	}
	
}
