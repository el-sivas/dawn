package de.elsivas.mail.data;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleMailConfig {

	private Collection<String> reciepients = new ArrayList<>();

	@XmlTransient
	private String configFile;

	private String smtpHost;

	private String imapHost;

	private String user;

	private String password;
	
	private String primarylistMailAddress;
	
	public Collection<String> getAllReciepients() {
		return getReciepients();
	}

	private Collection<String> getReciepients() {
		return reciepients;
	}

	public void setReciepients(Collection<String> reciepients) {
		this.reciepients = reciepients;
	}

	public void addRecipient(String reciepient) {
		if (reciepients.contains(reciepient)) {
			return;
		}
		reciepients.add(reciepient);
	}
	
	public void removeRecipient(final String recipient) {
		reciepients.remove(recipient);
	}

	public String getConfigFile() {
		return configFile;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtp) {
		this.smtpHost = smtp;
	}

	public String getImapHost() {
		return imapHost;
	}

	public void setImapHost(String imap) {
		this.imapHost = imap;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String login) {
		this.user = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public void addReciepient(String string) {
		reciepients.add(string);
	}

	@Override
	public String toString() {
		return "SimpleMailConfig [reciepients=" + reciepients + ", configFile=" + configFile + ", smtpHost=" + smtpHost
				+ ", imapHost=" + imapHost + ", user=" + user + "]";
	}

	public String getPrimarylistMailAddress() {
		return primarylistMailAddress;
	}

	public void setPrimarylistMailAddress(String primarylistMailAddress) {
		this.primarylistMailAddress = primarylistMailAddress;
	}
}
