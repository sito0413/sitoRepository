package frameWork.base.mail;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import frameWork.base.core.event.queue.EventQueue;

public class SendMail {
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
	
	private static EventQueue eventQueue = new EventQueue();
	
	public static void invoke(final String title, final String mailBody, final String to) throws Exception {
		invoke(title, mailBody, new String[] {
			to
		});
	}
	
	public static void invoke(final String title, final String mailBody, final String to, final String cc)
	        throws Exception {
		invoke(title, mailBody, new String[] {
			to
		}, new String[] {
			cc
		});
	}
	
	public static void invoke(final String title, final String mailBody, final String to, final String cc,
	        final String bcc) throws Exception {
		invoke(title, mailBody, new String[] {
			to
		}, new String[] {
			cc
		}, new String[] {
			bcc
		});
	}
	
	public static void invoke(final String title, final String mailBody, final String[] to) throws Exception {
		invoke(title, mailBody, to, new String[] {});
	}
	
	public static void invoke(final String title, final String mailBody, final String[] to, final String[] cc)
	        throws Exception {
		invoke(title, mailBody, to, cc, new String[] {});
	}
	
	public static void invoke(final String title, final String mailBody, final String[] to, final String[] cc,
	        final String[] bcc) throws Exception {
		final Address[] ato = new Address[to.length];
		for (int i = 0; i < to.length; i++) {
			ato[i] = new InternetAddress(to[i]);
		}
		
		final Address[] acc = new Address[cc.length];
		for (int i = 0; i < to.length; i++) {
			acc[i] = new InternetAddress(cc[i]);
		}
		
		final Address[] abcc = new Address[bcc.length];
		for (int i = 0; i < to.length; i++) {
			abcc[i] = new InternetAddress(bcc[i]);
		}
		eventQueue.putEvent(new SendMailEvent(title, mailBody, ato, acc, abcc));
	}
	
	public static void invoke(final String title, final String mailBody, final Address to) throws Exception {
		invoke(title, mailBody, new Address[] {
			to
		});
	}
	
	public static void invoke(final String title, final String mailBody, final Address to, final Address cc)
	        throws Exception {
		invoke(title, mailBody, new Address[] {
			to
		}, new Address[] {
			cc
		});
	}
	
	public static void invoke(final String title, final String mailBody, final Address to, final Address cc,
	        final Address bcc) throws Exception {
		invoke(title, mailBody, new Address[] {
			to
		}, new Address[] {
			cc
		}, new Address[] {
			bcc
		});
	}
	
	public static void invoke(final String title, final String mailBody, final Address[] to) throws Exception {
		invoke(title, mailBody, to, new Address[] {});
	}
	
	public static void invoke(final String title, final String mailBody, final Address[] to, final Address[] cc)
	        throws Exception {
		invoke(title, mailBody, to, cc, new Address[] {});
	}
	
	public static void invoke(final String title, final String mailBody, final Address[] to, final Address[] cc,
	        final Address[] bcc) throws Exception {
		eventQueue.putEvent(new SendMailEvent(title, mailBody, to, cc, bcc));
	}
}
