package de.elsivas.data.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;

public class ShareValuePeriodFileDaoTest {

	private Map<String, String> config = new HashMap<>();
	private ShareValuePeriodFileDao instance;

	@Before
	public void init() {
		FinConfig.init(config);
		instance = ShareValuePeriodFileDao.getInstance();
	}

	@Test
	public void testSave() {
		instance.save(create());
	}
	
	@Test
	public void testLoad() {
		final Set<ShareValuePeriod> loadAll = instance.loadAll();
	}

	private ShareValuePeriod create() {
		return new ShareValuePeriod() {

			@Override
			public Date getDate() {
				return DateUtils.toDate(LocalDate.now().minusDays((long) (Math.random() * 100)));
			}

			@Override
			public BigDecimal getHighest() {
				return BigDecimal.valueOf(1000 + Math.random() * 100);
			}

			@Override
			public BigDecimal getLowest() {
				return BigDecimal.valueOf(750);
			}

			@Override
			public BigDecimal getFirst() {
				return BigDecimal.valueOf(850);
			}

			@Override
			public BigDecimal getLast() {
				return BigDecimal.valueOf(950);
			}

			@Override
			public BigDecimal getVolume() {
				return BigDecimal.valueOf(1000000);
			}

			@Override
			public Wertpapier getValue() {
				return Wertpapier.DAX;
			}

		};
	}

}
