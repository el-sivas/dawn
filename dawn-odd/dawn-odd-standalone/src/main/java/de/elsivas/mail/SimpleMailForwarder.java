package de.elsivas.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.mail.data.SimpleMailConfig;
import de.elsivas.mail.data.SimpleMailConfigDaoUtils;
import de.elsivas.mail.logic.SMForwardUtils;
import de.elsivas.mail.logic.SMLogicException;
import de.elsivas.mail.logic.SMUtils;
import de.elsivas.mail.logic.SimpleMailConfigUtils;
import de.elsivas.mail.logic.SimpleMailFolder;
import de.elsivas.mail.logic.SimpleMailSession;

public class SimpleMailForwarder {

	private static final Log LOG = LogFactory.getLog(SimpleMailForwarder.class);

	private static final String VERSION = "0.3-SN";

	private static final String SUB_SUCCESS = "Subscription succesful. Send a mail with subject: 'UNSUBSCRIBE' to unsubscribe.";

	private static final String UNSUB_SUCCESS = "Unsubscription succesful. Send a mail with subject: 'SUBSCRIBE' to subscribe again.";

	private static final String SUBJECT_UNSUBSCRIBE = "UNSUBSCRIBE";

	private static final String SUBJECT_SUBSCRIBE = "SUBSCRIBE";

	private static Collection<String> CTRL_SUBJECTS = Arrays.asList(SUBJECT_SUBSCRIBE, SUBJECT_UNSUBSCRIBE);

	public static void main(final String[] args) {
		
		
		
		final long start = System.currentTimeMillis();
		LOG.info("SMF Version " + VERSION);
		final String pathname = args[0];
		if (fistStart(args)) {
			LOG.warn("Started in 'first' mode. Generate config!");
			SimpleMailConfigUtils.init(pathname);
			System.exit(0);
		}
		final SimpleMailConfig config = SimpleMailConfigDaoUtils.load(pathname);
		migrateRecipients(config);

		try {
			handleMessage(config);
		} catch (final SMLogicException e) {
			LOG.error("an error has occured", e);
			System.exit(1);
		}
		SimpleMailConfigDaoUtils.save(config, pathname);
		LOG.info("all done. finish. took (ms): " + (System.currentTimeMillis() - start));
	}

	private static void migrateRecipients(final SimpleMailConfig config) {
		final Collection<String> allReciepients = config.getAllReciepients();
		if(allReciepients.isEmpty()) {
			return;
		}
		for (final String string : allReciepients) {
			config.addReciepient(string);
		}
		
		
	}

	private static void handleMessage(final SimpleMailConfig config) throws SMLogicException {
		final String imapHost = config.getImapHost();
		final String user = config.getUser();
		final String password = config.getPassword();
		final String smtpHost = config.getSmtpHost();

		try (SimpleMailSession session = SimpleMailSession.getInstance(imapHost, user, password, smtpHost)) {
			handleMessages(session, config);
		} catch (IOException | MessagingException e) {
			throw new SMLogicException(e);
		}
	}

	private static void handleMessages(final SimpleMailSession session, final SimpleMailConfig config)
			throws MessagingException, SMLogicException {

		LOG.info("handle messages");

		try (SimpleMailFolder rootFolder = session.openFolder("INBOX", Folder.READ_WRITE)) {
			handleMessages(session, config, rootFolder);
		} catch (IOException | MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static void handleMessages(final SimpleMailSession session, final SimpleMailConfig config,
			final SimpleMailFolder rootFolder) throws SMLogicException {
		final Collection<Message> messages = new ArrayList<>();
		try {
			messages.addAll(rootFolder.getMessages());
		} catch (final MessagingException e) {
			throw new SMLogicException(e);
		}
		LOG.info(messages.size() + " messages found");

		final Collection<Message> ctrlMessages = messages.stream().filter(e -> CTRL_SUBJECTS.contains(subject(e)))
				.collect(Collectors.toList());

		final Collection<Message> selfSendedMails = SMForwardUtils.extractSelfSendedMessages(messages,
				config.getPrimarylistMailAddress(), config.getNoreplyAddress());

		final Collection<Message> forwardMessages = new ArrayList<>(messages);
		forwardMessages.removeAll(ctrlMessages);
		forwardMessages.removeAll(selfSendedMails);

		LOG.info(ctrlMessages.size() + " ctrl messages, " + forwardMessages.size() + " forward messages, "
				+ selfSendedMails.size() + " self sendet messages");

		forwardMessages(forwardMessages, session, config);
		handleCtrlMessages(ctrlMessages, config, session);

		try (SimpleMailFolder subfolder = rootFolder.getSubfolder("forwarded")) {
			LOG.info("copy forwardes messages...");
			copyMessages(forwardMessages, rootFolder, subfolder);
		} catch (MessagingException | IOException e) {
			throw new SMLogicException(e);
		}

		try (SimpleMailFolder subfolder = rootFolder.getSubfolder("ctrl")) {
			LOG.info("copy processed ctrl messages...");
			copyMessages(ctrlMessages, rootFolder, subfolder);
		} catch (MessagingException | IOException e) {
			throw new SMLogicException(e);
		}

		try (SimpleMailFolder subfolder = rootFolder.getSubfolder("selfsendet")) {
			LOG.info("copy self sendet messages...");
			copyMessages(selfSendedMails, rootFolder, subfolder);
		} catch (MessagingException | IOException e) {
			throw new SMLogicException(e);
		}

		deleteMessages(messages);
	}

	private static void handleCtrlMessages(final Collection<Message> ctrlMessages, final SimpleMailConfig config,
			final SimpleMailSession session) throws SMLogicException {

		if (ctrlMessages.isEmpty()) {
			LOG.info("no ctrl messages to process");
			return;
		}

		for (final Message ctrlMessage : ctrlMessages) {
			try {
				handleCtrlMessage(config, session, ctrlMessage);
			} catch (final MessagingException e) {
				throw new SMLogicException(e);
			}
		}
	}

	private static void handleCtrlMessage(final SimpleMailConfig config, final SimpleMailSession session,
			final Message ctrlMessage) throws MessagingException, SMLogicException {
		final String subject = ctrlMessage.getSubject();
		final String from = SMUtils.extractMail(ctrlMessage.getFrom()[0].toString());

		if (SUBJECT_SUBSCRIBE.equals(subject)) {
			config.addReciepient(from);

			LOG.info("Subscribed: " + ctrlMessage.getFrom()[0]);
			SMForwardUtils.sendOwn(session, "Subscribed!", SUB_SUCCESS, from, config.getNoreplyAddress(),
					config.getPrimarylistMailAddress());

		} else if (SUBJECT_UNSUBSCRIBE.equals(subject)) {
			config.removeRecipient(from);

			LOG.info("Unsubscribed: " + ctrlMessage.getFrom()[0]);
			SMForwardUtils.sendOwn(session, "Unsubscribed!", UNSUB_SUCCESS, from, config.getNoreplyAddress(),
					config.getPrimarylistMailAddress());

		}
	}

	private static String subject(final Message m) {
		try {
			return m.getSubject();
		} catch (final MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static void deleteMessages(final Collection<Message> processedMessages) throws SMLogicException {
		LOG.info("cleanup messages: " + processedMessages.size());

		for (final Message message : processedMessages) {
			try {
				message.setFlag(Flag.DELETED, true);
			} catch (final MessagingException e) {
				throw new SMLogicException(e);
			}
		}
	}

	private static void copyMessages(final Collection<Message> processedMessages, final SimpleMailFolder rootFolder,
			final SimpleMailFolder subfolder) throws SMLogicException {

		LOG.info("copy messages: " + processedMessages.size());

		try {
			subfolder.open(Folder.READ_WRITE);
		} catch (final MessagingException e) {
			throw new SMLogicException(e);
		}
		try {
			rootFolder.copy(processedMessages, subfolder);
		} catch (final MessagingException e) {
			throw new SMLogicException(e);
		}
	}

	private static void forwardMessages(final Collection<Message> messages, final SimpleMailSession session,
			final SimpleMailConfig config) throws SMLogicException {

		if (messages.isEmpty()) {
			LOG.info("no messages to forward");
			return;
		}

		final Collection<String> allReciepients = config.getRecipients();
		if (allReciepients.isEmpty()) {
			LOG.warn("No recipients!");
			return;
		}

		for (final Message originalMessage : messages) {
			SMForwardUtils.forwardMessage(session, config, originalMessage);
		}
	}

	private static boolean fistStart(final String[] args) {
		if (args.length < 2) {
			return false;
		}
		return "first".equals(args[1]);
	}

}
