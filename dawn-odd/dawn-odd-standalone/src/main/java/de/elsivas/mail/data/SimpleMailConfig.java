package de.elsivas.mail.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleMailConfig {

	private static final String SEMICOLON = ";";

	private Collection<String> reciepients = new ArrayList<>();

	private String recipientsList;

	private String smtpHost;

	private String imapHost;

	private String user;

	private String password;

	private String noreplyAddress;

	private String primarylistMailAddress;

	@Deprecated
	public Collection<String> getAllReciepients() {
		return getReciepients();
	}

	@Deprecated
	private Collection<String> getReciepients() {
		return reciepients;
	}

	@Deprecated
	public void setReciepients(final Collection<String> reciepients) {
		this.reciepients = reciepients;
	}

	@Deprecated
	public void addRecipient(final String reciepient) {
		if (reciepients.contains(reciepient)) {
			return;
		}
		reciepients.add(reciepient);
	}

	@Deprecated
	public void removeRecipient(final String recipientToRemove) {
		final Collection<String> recipients = getRecipients();
		if (!recipients.contains(recipientToRemove)) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		for (final String recipientToStay : recipients) {
			sb.append(recipientToStay);
			sb.append(SEMICOLON);
		}
		setRecipientsList(sb.toString());
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(final String smtp) {
		this.smtpHost = smtp;
	}

	public String getImapHost() {
		return imapHost;
	}

	public void setImapHost(final String imap) {
		this.imapHost = imap;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String login) {
		this.user = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void addReciepient(final String newRecipient) {
		final Collection<String> recipients = getRecipients();
		if (recipients.contains(newRecipient)) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		for (final String string : recipients) {
			if(!sb.toString().isEmpty()) {
				
			}
			sb.append(string);
			sb.append(SEMICOLON);
		}
		sb.append(newRecipient);
		setRecipientsList(sb.toString());
	}

	@Override
	public String toString() {
		return "SimpleMailConfig [reciepients=" + reciepients + ", recipientsList=" + recipientsList + ", smtpHost="
				+ smtpHost + ", imapHost=" + imapHost + ", user=" + user + ", password=" + password
				+ ", noreplyAddress=" + noreplyAddress + ", primarylistMailAddress=" + primarylistMailAddress + "]";
	}

	public String getPrimarylistMailAddress() {
		return primarylistMailAddress;
	}

	public void setPrimarylistMailAddress(final String primarylistMailAddress) {
		this.primarylistMailAddress = primarylistMailAddress;
	}

	public String getNoreplyAddress() {
		return noreplyAddress;
	}

	public void setNoreplyAddress(final String noreplyAddress) {
		this.noreplyAddress = noreplyAddress;
	}

	public String getRecipientsList() {
		return recipientsList != null ? recipientsList : StringUtils.EMPTY;
	}

	public void setRecipientsList(final String recipientsList) {
		this.recipientsList = recipientsList;
	}

	public Collection<String> getRecipients() {
		final String[] split = getRecipientsList().split(SEMICOLON);
		return Arrays.asList(split);

	}
}
