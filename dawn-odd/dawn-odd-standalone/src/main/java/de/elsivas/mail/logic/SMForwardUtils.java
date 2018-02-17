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

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.mail.data.SimpleMailConfig;

public class SMForwardUtils {

	private static final Log LOG = SimpleLogFactory.getLog(SMForwardUtils.class);

	private static final String PRIMARY_LIST_ADDRESS = "feuerwehr@sebastianlipp.de";

	private static final String SECONDARY_LIST_ADDRESS = "fw-list@sebastianlipp.de";

	private static final String FFO_PREFIX = "[FFO]";

	public static void forwardMessage(SimpleMailSession session, SimpleMailConfig config, Message originalMessage)
			throws SMLogicException {

		final Collection<String> allReciepients = config.getAllReciepients();
		for (String recipient : allReciepients) {
			try {
				sendMailToRecipient(session, originalMessage, recipient);
			} catch (Throwable t) {
				LOG.error("error on forwarding mail to: " + recipient, t);
			}
		}

	}

	private static void sendMailToRecipient(SimpleMailSession session, Message originalMessage, String recipient)
			throws SMLogicException {
		final SMPattern pattern = new SMPattern() {

			@Override
			public String getSubject() throws SMLogicException {
				try {
					return determineNewSubject(originalMessage.getSubject());
				} catch (MessagingException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Collection<Address> getRecipients() throws SMLogicException {
				try {
					return Arrays.asList(new InternetAddress(SECONDARY_LIST_ADDRESS));
				} catch (AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Collection<Address> getRecipientsBCC() throws SMLogicException {
				try {
					return SMUtils.toAdresses(Collections.singletonList(recipient));
				} catch (AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Address getFrom() throws SMLogicException {
				try {
					return originalMessage.getFrom()[0];
				} catch (MessagingException e) {
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
					return new InternetAddress(PRIMARY_LIST_ADDRESS);
				} catch (AddressException e) {
					throw new SMLogicException(e);
				}
			}
		};
		SMSendUtils.sendMail(pattern, session);
	}

	public static void sendOwn(SimpleMailSession session, String subject, String text, String to)
			throws SMLogicException {
		final SMPattern pattern = new SMPattern() {

			@Override
			public String getSubject() throws SMLogicException {
				return subject;
			}

			@Override
			public Collection<Address> getRecipients() throws SMLogicException {
				try {
					return Collections.singleton(new InternetAddress(to));
				} catch (AddressException e) {
					throw new SMLogicException(e);
				}
			}

			@Override
			public Address getFrom() throws SMLogicException {
				try {
					return new InternetAddress(PRIMARY_LIST_ADDRESS);
				} catch (AddressException e) {
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
					return new InternetAddress(SECONDARY_LIST_ADDRESS);
				} catch (AddressException e) {
					throw new SMLogicException(e);
				}
			}

		};
		SMSendUtils.sendMail(pattern, session);

	}

	public static Collection<Message> extractSelfSendedMessages(Collection<Message> messages) throws SMLogicException {
		try {
			return extractSelfSendedMessagesInternal(messages);
		} catch (MessagingException e) {
			throw new SMLogicException(e);
		}
	}

	private static Collection<Message> extractSelfSendedMessagesInternal(Collection<Message> messages)
			throws MessagingException {
		final Collection<Message> selfSendedMessages = new ArrayList<>();
		for (Message message : messages) {

			final Collection<String> recipientsTO = Arrays.asList(message.getRecipients(RecipientType.TO)).stream()
					.map(e -> extractMail(e)).collect(Collectors.toList());

			final Collection<String> replysTo = Arrays.asList(message.getReplyTo()).stream().map(e -> extractMail(e))
					.collect(Collectors.toList());

			if (recipientsTO.size() == 1 && recipientsTO.contains(SECONDARY_LIST_ADDRESS)) {
				if (replysTo.size() == 1 && replysTo.contains(PRIMARY_LIST_ADDRESS)) {
					if (message.getSubject().startsWith(FFO_PREFIX)) {
						selfSendedMessages.add(message);
					}
				}
			}
		}
		return selfSendedMessages;
	}

	private static String extractMail(Address a) {
		try {
			return SMUtils.extractMail(a.toString());
		} catch (SMLogicException e) {
			throw new SMRuntimeException(e);
		}
	}

	private static String determineNewSubject(String subject) {
		StringTokenizer st = new StringTokenizer(subject, FFO_PREFIX);
		StringBuffer sb = new StringBuffer();
		sb.append(FFO_PREFIX + " ");
		while (st.hasMoreElements()) {
			sb.append(st.nextToken().trim() + " ");
		}
		return sb.toString();
	}

}
