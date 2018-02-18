package de.elsivas.mail.logic;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class SimpleMailFolder implements Closeable {

	private Folder folder;

	private int mode;

	public SimpleMailFolder(final Folder f) {
		this.folder = f;
	}

	private Folder getFolder() {
		return folder;
	}

	public void copy(Collection<Message> messages, SimpleMailFolder folder) throws MessagingException {
		this.folder.copyMessages(messages.toArray(new Message[0]), folder.getFolder());
	}

	public void open(int mode) throws MessagingException {
		this.mode = mode;
		folder.open(mode);
	}

	public Collection<Message> getMessages() throws MessagingException {
		return Arrays.asList(folder.getMessages());
	}

	@Override
	public void close() throws IOException {
		if (Folder.READ_WRITE == mode) {
			close(true);
		} else {
			close(false);
		}
	}

	private void close(boolean expunge) throws IOException {
		try {
			folder.close(expunge);
		} catch (MessagingException e) {
			throw new IOException();
		}
	}

	public SimpleMailFolder getSubfolder(String subfolderName) throws MessagingException {
		Folder subfolder = this.folder.getFolder(subfolderName);
		if (!subfolder.exists()) {
			subfolder.create(Folder.HOLDS_MESSAGES);
		}
		return new SimpleMailFolder(subfolder);
	}
}
