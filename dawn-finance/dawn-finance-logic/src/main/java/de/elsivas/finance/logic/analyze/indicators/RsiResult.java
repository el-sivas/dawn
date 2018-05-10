package de.elsivas.finance.logic.analyze.indicators;

import java.math.BigDecimal;

import de.elsivas.basic.BaseCalcUtils;

public class RsiResult {
	
	public BigDecimal sumUp = BigDecimal.ZERO;
	
	public BigDecimal sumDown = BigDecimal.ZERO;
	
	public int countUp = 0;
	
	public int countDown = 0;
	
	public void add(RsiResult resultToAdd) {
		sumUp = BaseCalcUtils.add(sumUp, resultToAdd.sumUp);
		sumDown = BaseCalcUtils.add(sumDown, resultToAdd.sumDown);
		countUp += resultToAdd.countUp;
		countDown += resultToAdd.countDown;
	}

}
