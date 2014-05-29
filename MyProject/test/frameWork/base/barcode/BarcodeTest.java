package frameWork.base.barcode;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class BarcodeTest {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void getBarcodeFontTest1() {
		assertEquals(Barcode.getBarcodeFont(10).getSize(), 40);
	}
	
	@Test
	public void getBarcodeFontTest2() {
		assertEquals(Barcode.getBarcodeFont(0).getSize(), 0);
	}
	
	@Test
	public void createBarcodeTest1() {
		final String text = "test";
		assertEquals(Barcode.createBarcode(text), Barcode.EXTEND + text + Barcode.EXTEND);
	}
	
	@Test
	public void createBarcodeTest2() {
		final String text = "";
		assertEquals(Barcode.createBarcode(text), text);
	}
}
