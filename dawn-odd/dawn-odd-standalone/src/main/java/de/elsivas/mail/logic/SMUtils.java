package de.elsivas.mail.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class SMUtils {

	private static final String E_MAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";

	public static String extractMail(String s) throws SMLogicException {
		Matcher matcher = Pattern.compile(E_MAIL_REGEX).matcher(s);
		while (matcher.find()) {
			return matcher.group();
		}
		throw new SMLogicException("No email address found.");
	}

	public static Collection<Address> toAdresses(Collection<String> adresses) throws AddressException {
		final Collection<Address> addresses = new ArrayList<>();
		for (String string : adresses) {
			addresses.add(new InternetAddress(string));
		}
		return addresses;	
	}
}
