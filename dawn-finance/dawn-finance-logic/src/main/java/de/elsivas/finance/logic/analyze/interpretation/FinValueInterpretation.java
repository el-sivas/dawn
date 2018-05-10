package de.elsivas.finance.logic.analyze.interpretation;

import java.util.Date;

import de.elsivas.finance.data.model.Wertpapier;

public interface FinValueInterpretation {
	
	Date getDate();
	
	Wertpapier getWertpapier();

	int getBollinger();
	
	int getWMA38();
	
	int getWMA200();
	
	int getWMA38to200();
	
	int getMACD();
	
	int getRSI();
	
	int getSSTOC();
	
	int getTrendWeek();
	
	int getTrendMonth();
	
	int getTrend3Month();
	
	int getTrendYear();
	
}
