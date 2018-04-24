package de.elsivas.finance.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.basic.filedao.CsvFileDao;
import de.elsivas.finance.logic.model.ShareValuePeriod;

public class ShareValuePeriodBuilder {

	public Collection<ShareValuePeriod> build(final String isin, Date start, Date end, String fileName) {
		final Csv csv = CsvFileDao.read(fileName);
		final Collection<ShareValuePeriod> periods = new ArrayList<>();
		while (csv.hasNext()) {
			periods.add(build(csv.next()));

		}
		return periods;
	}

	private ShareValuePeriod build(CsvLine line) {
		BigDecimal lowest = line.getValue(ShareValuePeriod.Value.LOWEST.toString(), BigDecimal.class);
		BigDecimal highest = line.getValue(ShareValuePeriod.Value.HIGHEST.toString(), BigDecimal.class);
		BigDecimal first = line.getValue(ShareValuePeriod.Value.FIRST.toString(), BigDecimal.class);
		BigDecimal last = line.getValue(ShareValuePeriod.Value.LAST.toString(), BigDecimal.class);
		Date date = line.getValue(ShareValuePeriod.Value.LAST.toString(), Date.class);
		BigDecimal volume = line.getValue(ShareValuePeriod.Value.VOLUME.toString(), BigDecimal.class);
		Wertpapier value = value(line);

		// TODO Auto-generated method stub
		return create(lowest, highest, first, last, date, volume, value);
	}

	private Wertpapier value(CsvLine line) {
		final String isin = line.getValue(ShareValuePeriod.Value.ISIN.toString());
		for (Wertpapier wertpapier : Wertpapier.values()) {
			if (wertpapier.getIsin().equals(isin)) {
				return wertpapier;
			}
		}
		return null;
	}

	private ShareValuePeriod create(BigDecimal lowest, BigDecimal highest, BigDecimal first, BigDecimal last, Date date,
			BigDecimal volume, Wertpapier value) {

		return new ShareValuePeriod() {

			@Override
			public BigDecimal getLowest() {
				return lowest;
			}

			@Override
			public BigDecimal getLast() {
				return last;
			}

			@Override
			public BigDecimal getHighest() {
				return highest;
			}

			@Override
			public BigDecimal getFirst() {
				return first;
			}

			@Override
			public Date getDate() {
				return date;
			}

			@Override
			public BigDecimal getVolume() {
				return volume;
			}

			@Override
			public Wertpapier getValue() {
				return value;
			}
		};

	}

}
