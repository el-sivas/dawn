package de.elsivas.webapp.base;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {

	private Date date;
	
	public DateFormatter(final Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
