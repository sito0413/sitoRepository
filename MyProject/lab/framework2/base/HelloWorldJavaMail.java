package framework2.base;

import frameWork.base.mail.SendMail;

public class HelloWorldJavaMail {
	
	public static void main(final String[] args) throws Exception {
		SendMail.invoke("test", "test", new String[] {
			"sito0413@yahoo.co.jp"
		}, new String[] {
			"sito0413@yahoo.co.jp"
		}, new String[] {
			"sito0413@yahoo.co.jp"
		});
		try {
			Thread.sleep(5000);
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
}
