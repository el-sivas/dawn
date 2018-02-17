package de.elsivas.mail.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.mail.Address;

public interface SMPattern {

	String getSubject() throws SMLogicException;

	Object getContent() throws SMLogicException;

	Collection<Address> getRecipients() throws SMLogicException;

	default Collection<Address> getRecipientsCC() {
		return Collections.emptyList();
	}

	default Collection<Address> getRecipientsBCC() throws SMLogicException {
		return Collections.emptyList();
	}

	default Address getReplyTo() throws SMLogicException {
		return null;
	}

	Address getFrom() throws SMLogicException;

	default Date sendDate() {
		return new Date();
	}
}
