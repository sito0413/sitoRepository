package sito;

import java.util.ArrayList;
import java.util.List;

import sito.PDF.Mainbody.Obj;


@SuppressWarnings("nls")
public class PDF {
	public static void main(final String[] args) {
		System.out.println(new PDF());
	}
	
	class Header {
		class Version {
			String ver = "1.7";
			
			@Override
			public String toString() {
				return "%PDF-" + ver + "\r\n";
			}
		}
		
		@Override
		public String toString() {
			return new Version().toString() + "%" + new String(new byte[] {
				(byte) 128
			}) + "\r\n";
		}
	}
	
	class Mainbody {
		class Obj {
			private final String value;
			private int index = 0;
			
			public Obj(final String value) {
				this.value = value;
			}
			
			@Override
			public String toString() {
				return index + " 0 obj" + "\r\n" + value + "\r\n" + "endobj" + "\r\n";
			}
			
			public void setNo(final int index) {
				this.index = index;
			}
		}
		
		List<Obj> objList = new ArrayList<>();
		
		public Mainbody() {
			objList.add(new Obj("<<" + "\r\n" + "/Kids [2 0 R]" + "\r\n" + "/Count 1" + "\r\n" + "/Type /Pages"
			        + "\r\n" + ">>"));
			objList.add(new Obj("<<" + "\r\n" + "/Parent 1 0 R" + "\r\n" + "/Resources 3 0 R" + "\r\n"
			        + "/MediaBox [0 0 612 792]" + "\r\n" + "/Contents [4 0 R]" + "\r\n" + "/Type /Pages" + "\r\n"
			        + ">>"));
			objList.add(new Obj("<<" + "\r\n" + "/Font" + "\r\n" + "<<" + "\r\n" + "/Fo" + "\r\n" + "<<" + "\r\n"
			        + "/BaseFont /Times-Italic" + "\r\n" + "/Subtype /Type1" + "\r\n" + "/Type /Font" + "\r\n" + ">>"
			        + "\r\n" + ">>" + "\r\n" + ">>"));
			objList.add(new Obj("<<" + "\r\n" + "/Length 65" + "\r\n" + ">>" + "\r\n" + "stream" + "\r\n"
			        + "1. 0. 0. 1. 50. 700. cm" + "\r\n" + "BT" + "\r\n" + " /FO 36. Tf" + "\r\n"
			        + " (Hello, World) Tj" + "\r\n" + "ET" + "\r\n" + "endstream"));
			objList.add(new Obj("<<" + "\r\n" + "/Pages 1 0 R" + "\r\n" + "/Type /Catalog" + "\r\n" + ">>"));
		}
		
		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			int index = 1;
			for (final Obj obj : objList) {
				obj.setNo(index++);
				builder.append(obj);
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
			final StringBuilder builder = new StringBuilder("0 ").append(mainbody.objList.size() + 1).append("\r\n")
			        .append(String.format("%010d", 0)).append(" 65535 f").append("\r\n");
			int offset = header.toString().getBytes().length;
			for (final Obj obj : mainbody.objList) {
				builder.append(String.format("%010d", offset)).append(" 00000 n").append("\r\n");
				offset += obj.toString().getBytes().length;
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
			return "trailer" + "\r\n" + "<<" + "\r\n" + "/Root 5 0 R" + "\r\n" + "/Size "
			        + (crossReferenceTable.mainbody.objList.size() + 1) + "\r\n" + ">>" + "\r\n" + "startxref" + "\r\n"
			        + crossReferenceTable.toString().getBytes().length + "\r\n" + "%%EOF" + "\r\n";
		}
	}
	
	@Override
	public String toString() {
		// TODO 自動生成されたメソッド・スタブ
		final Header header = new Header();
		final Mainbody mainbody = new Mainbody();
		final CrossReferenceTable crossReferenceTable = new CrossReferenceTable(header, mainbody);
		return header.toString() + mainbody.toString() + crossReferenceTable.toString()
		        + new Trailer(crossReferenceTable).toString();
	}
}
