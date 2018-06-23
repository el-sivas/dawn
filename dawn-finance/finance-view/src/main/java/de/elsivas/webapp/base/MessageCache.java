package de.elsivas.webapp.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import de.elsivas.webapp.base.GuiMessage.Severity;

public class MessageCache {

	private static MessageCache instance;

	public Collection<GuiMessage> messages = new TreeSet<>();

	public static MessageCache getInstance() {
		synchronized (MessageCache.class) {
			if (instance == null) {
				instance = new MessageCache();
			}
		}
		return instance;
	}

	public void addError(final String message) {
		addMessage(message, Severity.ERROR);
	}

	public void addWarn(final String message) {
		addMessage(message, Severity.WARN);
	}

	public void addInfo(final String message) {
		addMessage(message, Severity.INFO);
	}

	private void addMessage(final String message, final Severity severity) {
		final GuiMessage m = new GuiMessage();
		m.setSeverity(severity);
		m.setMessage(message);
		messages.add(m);
	}

	public Collection<GuiMessage> getMessages() {
		return new ArrayList<>(messages);
	}

	public void clearMessages() {
		messages.clear();
	}

}
