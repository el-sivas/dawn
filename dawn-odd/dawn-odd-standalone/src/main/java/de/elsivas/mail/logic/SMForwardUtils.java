package de.elsivas.mail.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.mail.data.SimpleMailConfig;

public class SMForwardUtils {

	private static final Log LOG = LogFactory.getLog(SMForwardUtils.class);

	private static final String FFO_PREFIX = "[FFO]";

	public static void forwardMessage(final SimpleMailSession session, final SimpleMailConfig config,
			final Message originalMessage) throws SMLogicException {

		final Collection<String> allReciepients = config.getRecipients();
		for (final String recipient : allReciepients) {
			try {
				sendMailToRecipient(session, originalMessage, recipient, config.getNoreplyAddress(),
						config.getPrimarylistMailAddress());
			} catch (final Throwable t) {
				LOG.error("error on forwarding mail to: " + recipient, t);
			}
		}

	}

	private static void sendMailToRecipient(final SimpleMailSession session, final Message originalMessage,
			final String recipient, final String noreplyAdress, final String primaryListAddress)
			throws SMLogicException {
		final SMPattern pattern = new SMPattern() {

			@Override
			public String getSubject() throws SMLogicException {
				try {
					return determineNewSubject(originalMessage.getSubject());
				} catch (final MessagingException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Collection<Address> getRecipients() throws SMLogicException {
				try {
					return Arrays.asList(new InternetAddress(noreplyAdress));
				} catch (final AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Collection<Address> getRecipientsBCC() throws SMLogicException {
				try {
					return SMUtils.toAdresses(Collections.singletonList(recipient));
				} catch (final AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Address getFrom() throws SMLogicException {
				try {
					return originalMessage.getFrom()[0];
				} catch (final MessagingException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Object getContent() throws SMLogicException {
				try {
					return originalMessage.getContent();
				} catch (IOException | MessagingException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Address getReplyTo() throws SMLogicException {
				try {
					return new InternetAddress(primaryListAddress);
				} catch (final AddressException e) {
					throw new SMLogicException(e);
				}
			}
		};
		SMSendUtils.sendMail(pattern, session);
	}

	public static void sendOwn(final SimpleMailSession session, final String subject, final String text,
			final String to, final String noreply, final String primary) throws SMLogicException {
		final boolean deactivatedOwnMails = true;
		if (deactivatedOwnMails) {
			return;
		}

		final SMPattern pattern = new SMPattern() {

			@Override
			public String getSubject() throws SMLogicException {
				return subject;
			}

			@Override
			public Collection<Address> getRecipients() throws SMLogicException {
				try {
					return Collections.singleton(new InternetAddress(to));
				} catch (final AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Address getFrom() throws SMLogicException {
				try {
					return new InternetAddress(primary);
				} catch (final AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Object getContent() throws SMLogicException {
				return text;
			}

			@Override
			public Address getReplyTo() throws SMLogicException {
				try {
					return new InternetAddress(noreply);
				} catch (final AddressException e) {
					throw new SMLogicException(e);
				}
			}

		};
		SMSendUtils.sendMail(pattern, session);

	}

	public static Collection<Message> extractSelfSendedMessages(final Collection<Message> messages,
			final String primarylistMailAddress, final String noreply) throws SMLogicException {
		try {
			return extractSelfSendedMessagesInternal(messages, primarylistMailAddress, noreply);
		} catch (final MessagingException e) {
			throw new SMLogicException(e);
		}
	}

	private static Collection<Message> extractSelfSendedMessagesInternal(final Collection<Message> messages,
			final String primarylistMailAddress, final String noreply) throws MessagingException {
		final Collection<Message> selfSendedMessages = new ArrayList<>();
		for (final Message message : messages) {

			final Collection<String> recipientsTO = Arrays.asList(message.getRecipients(RecipientType.TO)).stream()
					.map(e -> extractMail(e)).collect(Collectors.toList());

			final Collection<String> replysTo = Arrays.asList(message.getReplyTo()).stream().map(e -> extractMail(e))
					.collect(Collectors.toList());

			if (recipientsTO.size() == 1 && recipientsTO.contains(noreply)) {
				if (replysTo.size() == 1 && replysTo.contains(primarylistMailAddress)) {
					if (message.getSubject().startsWith(FFO_PREFIX)) {
						selfSendedMessages.add(message);
					}
				}
			}
		}
		return selfSendedMessages;
	}

	private static String extractMail(final Address a) {
		try {
			return SMUtils.extractMail(a.toString());
		} catch (final SMLogicException e) {
			throw new SMRuntimeException(e);
		}
	}

	private static String determineNewSubject(final String subject) {
		final StringTokenizer st = new StringTokenizer(subject, FFO_PREFIX);
		final StringBuffer sb = new StringBuffer();
		sb.append(FFO_PREFIX + " ");
		while (st.hasMoreElements()) {
			sb.append(st.nextToken().trim() + " ");
		}
		return sb.toString();
	}

}
