package de.elsivas;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.elsivas.basic.DateUtils;

@ManagedBean
@SessionScoped
public class ViewFormatService {
	
	public String format(final Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
	
	public String format(final LocalDate localDate) {
		return format(DateUtils.toDate(localDate));
	}

}
