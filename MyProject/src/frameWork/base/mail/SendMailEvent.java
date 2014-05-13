package frameWork.base.mail;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import frameWork.base.core.event.Event;
import frameWork.base.core.fileSystem.FileSystem;

public class SendMailEvent implements Event {
	private final String title;
	private final String mailBody;
	private final Address[] to;
	private final Address[] cc;
	private final Address[] bcc;
	
	public SendMailEvent(final String title, final String mailBody, final Address[] to, final Address[] cc,
	        final Address[] bcc) {
		super();
		this.title = title;
		this.mailBody = mailBody;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
	}
	
	@Override
	public void run() {
		final Properties props = new Properties();
		props.put("mail.smtp.host", FileSystem.Config.MAIL_SMTP_HOST);
		props.put("mail.smtp.port", FileSystem.Config.MAIL_SMTP_PORT);
		props.put("mail.smtp.ssl.enable", FileSystem.Config.MAIL_SMTP_SSL);
		props.put("mail.smtp.starttls.enable", FileSystem.Config.MAIL_SMTP_TLS);
		
		try {
			Authenticator authenticator = null;
			if (FileSystem.Config.MAIL_SMTP_AUTH) {
				props.put("mail.smtp.auth", true);
				authenticator = new Authenticator() {
					private final PasswordAuthentication pa = new PasswordAuthentication(
					        FileSystem.Config.MAIL_SMTP_USER, FileSystem.Config.MAIL_SMTP_PASSWORD);
					
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return pa;
					}
				};
			}
			
			final Session session = Session.getInstance(props, authenticator);
			session.setDebug(false);
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FileSystem.Config.MAIL_SENDER, FileSystem.Config.MAIL_SENDER_NAME,
			        FileSystem.Config.MAIL_ENCODING));
			message.setRecipients(Message.RecipientType.TO, to);
			message.setRecipients(Message.RecipientType.CC, cc);
			message.setRecipients(Message.RecipientType.BCC, bcc);
			message.setSubject(title, FileSystem.Config.MAIL_ENCODING);
			message.setSentDate(Calendar.getInstance().getTime());
			message.setText(mailBody, FileSystem.Config.MAIL_ENCODING);
			message.setHeader("Content-Type", "text");
			Transport.send(message);
		}
		catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
	}
}