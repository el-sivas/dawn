package de.elsivas.finance.logic;

import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.finance.data.model.ShareValuePeriod;

public interface FinCsvLineParser {
	
	ShareValuePeriod parse(CsvLine csvLine);

}
