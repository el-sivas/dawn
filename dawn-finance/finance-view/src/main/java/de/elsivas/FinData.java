package de.elsivas;

import java.math.BigDecimal;
import java.util.Date;

public interface FinData {

	BigDecimal getPrice();

	Date getDate();

	BigDecimal getWma4();

	BigDecimal getWma38();

	BigDecimal getWma200();

	BigDecimal getWma13();

	int getOffset();

}
