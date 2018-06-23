package de.elsivas.webapp.base;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractController implements MessageController {

	private static final Log LOG = LogFactory.getLog(AbstractController.class);

	private MessageCache messageCache = MessageCache.getInstance();

	protected void addError(final String message) {
		messageCache.addError(message);
	}

	protected void addWarn(final String message) {
		messageCache.addWarn(message);
	}

	protected void addInfo(final String message) {
		messageCache.addInfo(message);
	}

	@Override
	public String getMessages() {
		final Collection<GuiMessage> messages = messageCache.getMessages();

		if (messages.isEmpty()) {
			return StringUtils.EMPTY;
		}
		final StringBuilder sb = new StringBuilder();
		for (GuiMessage message : messages) {
			sb.append(message.getSeverity() + ": " + message.getMessage() + "<br />");
		}
		messageCache.clearMessages();
		return sb.toString();
	}

	// override if needed
	public void loadLazyValues() {
		LOG.info("load lazy values: " + getClass().getName());
	}

	// implement if needet
	public void save() {
		LOG.warn("no save action implemented.");
	}
}
