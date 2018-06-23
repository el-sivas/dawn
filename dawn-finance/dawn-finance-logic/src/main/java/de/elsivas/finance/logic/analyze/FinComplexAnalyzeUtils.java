package de.elsivas.finance.logic.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.analyze.indicators.FinValueIndicators;
import de.elsivas.finance.logic.analyze.indicators.FinValueIndicatorsUtils;
import de.elsivas.finance.logic.analyze.interpretation.FinValueInterpretation;
import de.elsivas.finance.logic.analyze.interpretation.FinValueInterpretationUtils;

public class FinComplexAnalyzeUtils {

	public static Collection<FinComplexAnalyzeDay> analyze(Collection<ShareValuePeriod> all) {
		final Collection<FinValueIndicators> allIndicators = new ArrayList<>();
		all.forEach(e -> allIndicators.add(FinValueIndicatorsUtils.determine(e, all)));
		
		final Collection<FinValueInterpretation> allInterpretations = new ArrayList<>();
		allIndicators.forEach(e-> allInterpretations.add(FinValueInterpretationUtils.determine(e, all, allIndicators)));

		final Collection<FinComplexAnalyzeDay> allResults = new ArrayList<>();

		allInterpretations.forEach(e -> allResults.add(analyze(e)));
		return allResults;
	}

	private static FinComplexAnalyzeDay analyze(FinValueInterpretation interpretation) {
		return new FinComplexAnalyzeDay() {
			
			@Override
			public Date getDate() {
				return interpretation.getDate();
			}
			
			@Override
			public Wertpapier getWertpapier() {
				return interpretation.getWertpapier();
			}
			
			@Override
			public double getTrendYear() {
				return interpretation.getTrendYear() * 0.5;
			}
			
			@Override
			public double getTrendMonth() {
				return interpretation.getTrendMonth();
			}
			
			@Override
			public double getTrend3Month() {
				return interpretation.getTrend3Month() * 0.5;
			}
			
			@Override
			public double getTrendWeek() {
				return interpretation.getTrendWeek() * 0.5;
			}
			
			
			
			
			
		};
	}


}
