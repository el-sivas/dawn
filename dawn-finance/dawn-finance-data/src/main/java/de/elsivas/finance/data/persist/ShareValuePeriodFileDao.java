package de.elsivas.finance.data.persist;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.filedao.CsvFileDao;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.ShareValuePeriod.Value;

public class ShareValuePeriodFileDao {

	private static final Log LOG = LogFactory.getLog(ShareValuePeriodFileDao.class);

	private static ShareValuePeriodFileDao instance;

	private ShareValuePeriodFileDao() {

	}

	public static ShareValuePeriodFileDao getInstance() {
		if (instance == null) {
			instance = new ShareValuePeriodFileDao();
		}
		return instance;
	}

	public void save(ShareValuePeriod svp) {
		final Set<ShareValuePeriod> all = loadAll();
		all.add(svp);
		saveAll(all);
	}
	
	public void saveAll(Set<ShareValuePeriod> all, final String filename) {
		saveAllInternal(all, filename);
	}

	public void saveAll(Set<ShareValuePeriod> all) {
		final String filename = filename(FinConfig.get(FinConfig.SHARE_VALUE_DB_FILE));
		saveAllInternal(all, filename);
	}

	private void saveAllInternal(Set<ShareValuePeriod> all, final String filename) {
		final Value[] values = ShareValuePeriod.Value.values();
		final List<Value> asList = Arrays.asList(values);
		final List<String> title = asList.stream().map(e -> e.toString()).collect(Collectors.toList());
		final Csv csv = Csv.createEmpty(title);

		final List<ShareValuePeriod> valids = all.stream().filter(e -> ShareValuePeriodValidator.isValid(e))
				.collect(Collectors.toList());

		final List<ShareValuePeriod> invalids = new ArrayList<>(all);
		invalids.removeAll(valids);

		if (invalids.size() > 0) {
			LOG.error("Invalid datasets: " + invalids.size());
		}

		for (ShareValuePeriod shareValuePeriod : valids) {
			csv.add(ShareValuePeriodSerializer.serialize(shareValuePeriod));
		}

		CsvFileDao.write(filename, csv);
	}

	private String filename(final String filename) {
		return FinConfig.get(FinConfig.WORKDIR) + "/" + filename;
	}

	public Set<ShareValuePeriod> loadAll() {
		final String filename = filename(FinConfig.get(FinConfig.SHARE_VALUE_DB_FILE));
		if (!new File(filename).exists()) {
			return new TreeSet<>();
		}
		final Csv csv = CsvFileDao.read(filename);

		Set<ShareValuePeriod> all = new TreeSet<>();
		while (csv.hasNext()) {
			all.add(ShareValuePeriodSerializer.deserialize(csv.next()));
		}
		return all;
	}

}
