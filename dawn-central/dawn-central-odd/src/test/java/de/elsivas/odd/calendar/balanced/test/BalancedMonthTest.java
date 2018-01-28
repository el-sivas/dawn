package de.elsivas.odd.calendar.balanced.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import de.elsivas.odd.calendar.balanced.BalancedMonth;

public class BalancedMonthTest extends Assert {
	
	@Test
	public void testSchaltMonat() {
		ArrayList<BalancedMonth> arrayList = new ArrayList<>();
		arrayList.addAll(Arrays.asList(BalancedMonth.values()));
		final List<BalancedMonth> collect = arrayList.stream().filter(e -> e.isSchaltMonat())
				.collect(Collectors.toList());
		
		assertEquals(1, collect.size());
	}

	@Test
	public void test365() {
		int i = 0;
		for (BalancedMonth balancedMonth : BalancedMonth.values()) {
			i += balancedMonth.getDaysOfMonth();
		}
		assertEquals(365, i);
	}

}
