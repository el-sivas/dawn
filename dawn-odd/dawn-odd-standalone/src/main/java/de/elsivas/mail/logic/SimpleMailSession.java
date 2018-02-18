package de.elsivas.mail.logic;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

public class SimpleMailSession implements Closeable {

	private static final String PROTOCOL_IMAP = "imap";

	private Session session;

	private Store store;

	private SimpleMailSession(final Session session, final Store store) {
		this.session = session;
		this.store = store;
	}

	public static SimpleMailSession getInstance(final String imapHost, final String user, final String password, final String smtpHost)
			throws MessagingException {
		
		final Properties properties = new Properties();
		properties.put("mail.imap.host", imapHost);
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.auth", "true");
		final Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		};		
		final Session session = Session.getInstance(properties, auth);		
		final Store store = session.getStore(PROTOCOL_IMAP);
		store.connect();
		return new SimpleMailSession(session, store);
	}

	@Override
	public void close() throws IOException {
		try {
			store.close();
		} catch (MessagingException e) {
			throw new IOException(e);	
		}
	}
	
	public SimpleMailFolder openFolder(String folderName, int mode) throws MessagingException {
		final SimpleMailFolder folder = new SimpleMailFolder(store.getFolder(folderName));
		folder.open(mode);
		return folder;
	}
	
	public Message createMessage() {
		return new MimeMessage(session);
	}

}
