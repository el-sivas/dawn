package de.elsivas.finance.logic.analyze;

import java.util.Date;

import de.elsivas.finance.data.model.Wertpapier;

public interface FinComplexAnalyzeDay {
	
	public Date getDate();

	Wertpapier getWertpapier();

	double getTrendYear();

	double getTrendMonth();

	double getTrend3Month();

	double getTrendWeek();

}
