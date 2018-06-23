package de.elsivas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.BaseCalcUtils;
import de.elsivas.basic.DateUtils;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.logic.calc.FinBaseCalcUtils;

public class OutputControllerUtils {

	protected static final int SCALE = 0;
	protected static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	public static Collection<FinData> createAll(LocalDate from, CurrentDataHolder cdh, Integer... days) {
		final ArrayList<ShareValuePeriod> list = new ArrayList<>(cdh.getDataOrderedDecreasing());
		return Arrays.asList(days).stream().map(e -> create(list.get(e), list, e)).collect(Collectors.toList());
	}

	public static FinData create(ShareValuePeriod p, Collection<ShareValuePeriod> all, int offset) {
		return new FinData() {

			@Override
			public int getOffset() {
				return offset;
			}

			@Override
			public BigDecimal getPrice() {
				if (p == null) {
					return null;
				}
				return p.getLast().setScale(SCALE, ROUNDING_MODE);
			}

			@Override
			public Date getDate() {
				if (p == null) {
					return DateUtils.toDate(LocalDate.of(1970, 1, 1));
				}
				return p.getDate();
			}

			@Override
			public BigDecimal getWma4() {
				if (all.isEmpty()) {
					return BigDecimal.ZERO;
				}
				return wma(4, all, DateUtils.toLocalDate(p.getDate())).setScale(SCALE, ROUNDING_MODE);
			}

			@Override
			public BigDecimal getWma13() {
				if (all.isEmpty()) {
					return BigDecimal.ZERO;
				}
				return wma(13, all, DateUtils.toLocalDate(p.getDate())).setScale(SCALE, ROUNDING_MODE);
			}

			@Override
			public BigDecimal getWma38() {
				if (all.isEmpty()) {
					return BigDecimal.ZERO;
				}
				return wma(38, all, DateUtils.toLocalDate(p.getDate())).setScale(SCALE, ROUNDING_MODE);
			}

			@Override
			public BigDecimal getWma200() {
				if (all.isEmpty()) {
					return BigDecimal.ZERO;
				}
				return wma(200, all, DateUtils.toLocalDate(p.getDate())).setScale(SCALE, ROUNDING_MODE);
			}
		};
	}

	private static BigDecimal wma(final int i, Collection<ShareValuePeriod> all, LocalDate from) {
		if (all.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return FinBaseCalcUtils.calcWMA(all, from.minusDays(i), from);
	}

	public static Collection<FinDataDetail> createDetail(CurrentDataHolder cdh, Collection<Integer> days) {
		return createDetail(0, cdh, days);
	}

	public static Collection<FinDataDetail> createDetail(int offset, CurrentDataHolder cdh, Collection<Integer> days) {
		final List<FinDataDetailImpl> c = new ArrayList<>();
		days.forEach(e -> c.add(create(e + offset, cdh)));

		final List<FinDataDetailImpl> revert = new ArrayList<>();
		revert.addAll(c);
		Collections.reverse(revert);

		for (int i = 1; i < revert.size(); i++) {
			final FinDataDetailImpl next = revert.get(i - 1);
			final FinDataDetailImpl current = revert.get(i);
			current.setNext(next);
		}
		return c.stream().map(e -> (FinDataDetail) e).collect(Collectors.toList());
	}

	private static FinDataDetailImpl create(Integer e, CurrentDataHolder cdh) {
		return instance().new FinDataDetailImpl(e, cdh);
	}

	public static OutputControllerUtils instance() {
		return new OutputControllerUtils();
	}

	public class FinDataDetailImpl implements FinDataDetail, Comparable<FinDataDetailImpl> {

		private FinDataDetailImpl next;

		private int i;

		private CurrentDataHolder cdh;

		@Override
		public LocalDate getDate() {
			return DateUtils.toLocalDate(new ArrayList<>(cdh.getDataOrderedDecreasing()).get(i).getDate());
		}

		public FinDataDetailImpl(int i, CurrentDataHolder cdh) {
			this.i = i;
			this.cdh = cdh;
		}

		@Override
		public BigDecimal getWma4to38() {
			final List<ShareValuePeriod> dataOrderedDecreasing = cdh.getDataOrderedDecreasing();
			if (dataOrderedDecreasing.isEmpty()) {
				return BigDecimal.ZERO;
			}
			final BigDecimal wma4 = wma(4, dataOrderedDecreasing, getDate());
			final BigDecimal wma38 = wma(38, cdh.getDataOrderedDecreasing(), getDate());
			return wma4.subtract(wma38).setScale(SCALE, ROUNDING_MODE);
		}

		@Override
		public BigDecimal getWma13to38() {
			final List<ShareValuePeriod> dataOrderedDecreasing = cdh.getDataOrderedDecreasing();
			if (dataOrderedDecreasing.isEmpty()) {
				return BigDecimal.ZERO;
			}
			final BigDecimal wma13 = wma(13, dataOrderedDecreasing, getDate());
			final BigDecimal wma38 = wma(38, cdh.getDataOrderedDecreasing(), getDate());
			return wma13.subtract(wma38).setScale(SCALE, ROUNDING_MODE);
		}

		@Override
		public BigDecimal getWma38to200() {
			final List<ShareValuePeriod> dataOrderedDecreasing = cdh.getDataOrderedDecreasing();
			if (dataOrderedDecreasing.isEmpty()) {
				return BigDecimal.ZERO;
			}
			final BigDecimal wma38 = wma(38, dataOrderedDecreasing, getDate());
			final BigDecimal wma200 = wma(200, cdh.getDataOrderedDecreasing(), getDate());
			return wma38.subtract(wma200).setScale(SCALE, ROUNDING_MODE);
		}

		@Override
		public String getSignal() {
			final StringBuffer sb = new StringBuffer();
			if (getNext() == null) {
				return null;
			}
			sb.append(signal4to38());
			sb.append(signal13to38());
			sb.append(signal38to200());
			return sb.toString();

		}

		private String signal38to200() {
			final boolean wma38to200Positive = BaseCalcUtils.isBigger(getWma38to200(), BigDecimal.ZERO);
			final boolean nextWma38to200Positive = BaseCalcUtils.isBigger(getNext().getWma38to200(), BigDecimal.ZERO);
			if (wma38to200Positive && !nextWma38to200Positive) {
				return "+(38-200)";
			}
			if (!wma38to200Positive && nextWma38to200Positive) {
				return "-(38-200)";
			}
			return StringUtils.EMPTY;
		}

		private String signal13to38() {
			final boolean wma13to38Positive = BaseCalcUtils.isBigger(getWma13to38(), BigDecimal.ZERO);
			final boolean nextWma13to38Positive = BaseCalcUtils.isBigger(getNext().getWma13to38(), BigDecimal.ZERO);
			if (wma13to38Positive && !nextWma13to38Positive) {
				return "+(13-38)";
			}
			if (!wma13to38Positive && nextWma13to38Positive) {
				return "-(13-38)";
			}
			return StringUtils.EMPTY;
		}

		private String signal4to38() {
			final boolean wma4to38Positive = BaseCalcUtils.isBigger(getWma4to38(), BigDecimal.ZERO);
			final boolean nextWma4to38Positive = BaseCalcUtils.isBigger(getNext().getWma4to38(), BigDecimal.ZERO);
			if (wma4to38Positive && !nextWma4to38Positive) {
				return "+(4-38)";
			}
			if (!wma4to38Positive && nextWma4to38Positive) {
				return "-(4-38)";
			}
			return StringUtils.EMPTY;
		}

		public FinDataDetailImpl getNext() {
			return next;
		}

		public void setNext(FinDataDetailImpl next) {
			this.next = next;
		}

		@Override
		public int compareTo(FinDataDetailImpl o) {
			return this.getDate().compareTo(o.getDate());
		}

		@Override
		public int getDaysAgo() {
			return DateUtils.daysDifference(getDate(), LocalDate.now());
		}

	}

	public static Collection<Integer> upTo(int i) {
		final Collection<Integer> c = new ArrayList<>();
		int l = 0;
		while (l < i) {
			c.add(l++);
		}
		return c;
	}

}
