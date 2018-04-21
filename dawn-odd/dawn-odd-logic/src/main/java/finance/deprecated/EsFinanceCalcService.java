package finance.deprecated;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface EsFinanceCalcService {

	BigDecimal calcSMA(Map<Date, Double> history, int days);
	
	BigDecimal calcWMA(Map<Date, Double> history, int days);
	
	BigDecimal calcEMA(Map<Date, Double> history, int days);

}
