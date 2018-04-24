package de.elsivas.finance.data.persist;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.filedao.CsvFileDao;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.ShareValuePeriod.Value;

public class ShareValuePeriodFileDao {

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

	private void saveAll(Set<ShareValuePeriod> all) {
		final Value[] values = ShareValuePeriod.Value.values();
		final List<Value> asList = Arrays.asList(values);
		final List<String> title = asList.stream().map(e -> e.toString()).collect(Collectors.toList());
		final Csv csv = Csv.createEmpty(title);
		for (ShareValuePeriod shareValuePeriod : all) {
			csv.add(ShareValuePeriodSerializer.serialize(shareValuePeriod));
		}

		CsvFileDao.write(filename(), csv);

	}

	private String filename() {
		return FinConfig.get(FinConfig.WORKDIR) + "/" + FinConfig.get(FinConfig.SHARE_VALUE_DB_FILE);
	}

	public Set<ShareValuePeriod> loadAll() {
		final String filename = filename();
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
