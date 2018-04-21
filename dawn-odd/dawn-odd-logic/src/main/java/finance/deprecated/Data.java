package finance.deprecated;

import java.util.Date;

public class Data {
	
	private Double value;
	
	private Date date;
	
	public static Data create(Double value, Date date) {
		final Data data = new Data();
		data.setDate(date);
		data.setValue(value);
		return data;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
