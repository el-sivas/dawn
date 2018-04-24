package de.elsivas.mail.logic;

import java.util.Collection;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SMSendUtils {

	private static final Log LOG = LogFactory.getLog(SMSendUtils.class);

	public static void sendMail(final SMPattern pattern, final SimpleMailSession session) throws SMLogicException {
		try {
			sendMailInternal(pattern, session);
		} catch (final MessagingException e) {
			throw new SMLogicException(e);
		}
	}

	private static void sendMailInternal(final SMPattern pattern, final SimpleMailSession session)
			throws SMLogicException, MessagingException {
		
		final Message message = session.createMessage();
		
		final Address replyTo = pattern.getReplyTo();
		message.setSubject(pattern.getSubject());
		message.setFrom(pattern.getFrom());
		
		message.setReplyTo(new Address[] { replyTo });
		message.setSentDate(pattern.sendDate());

		addRecipients(message, pattern.getRecipients(), RecipientType.TO);
		addRecipients(message, pattern.getRecipientsCC(), RecipientType.CC);
		addRecipients(message, pattern.getRecipientsBCC(), RecipientType.BCC);

		setContent(message, pattern.getContent());

		LOG.info("Send: " + toSting(pattern));
		
		Transport.send(message);
	}

	public static String toSting(final SMPattern pattern) throws SMLogicException {
		final StringBuilder sb = new StringBuilder();
		sb.append("MAIL [");
		sb.append("'" + pattern.getSubject() + "'");
		sb.append(" ,from: '" + pattern.getFrom() + "'");
		sb.append(" ,to: " + pattern.getRecipients());
		sb.append(" ,cc: " + pattern.getRecipientsCC());
		sb.append(" ,bcc: " + pattern.getRecipientsBCC());
		sb.append("]");
		return sb.toString();
	}


	private static void setContent(final Message message, final Object content) throws MessagingException {
		if (content instanceof String) {
			message.setText((String) content);
		} else {
			message.setContent((Multipart) content);
		}
	}

	private static void addRecipients(final Message forwardMessage, final Collection<Address> allReciepients,
			final RecipientType type) throws MessagingException {
		for (final Address recipient : allReciepients) {
			forwardMessage.addRecipient(type, recipient);
		}
	}
}
