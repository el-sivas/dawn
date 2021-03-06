package de.elsivas.finance.data.persist;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.EsRuntimeException;
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

	public static ShareValuePeriodFileDao instance() {
		if (instance == null) {
			instance = new ShareValuePeriodFileDao();
		}
		return instance;
	}

	public void save(ShareValuePeriod svp) {
		final Set<ShareValuePeriod> all = findAllFromDatabase();
		all.add(svp);
		saveReallyAll(all);
	}

	public void saveAll(Set<ShareValuePeriod> all, final String filename) {
		saveAll(all, filename, false);
	}

	public void saveAll(Set<ShareValuePeriod> all, final String filename, boolean overwrite) {
		if (new File(filename).exists() && !overwrite) {
			throw new EsRuntimeException("file already exists:" + filename);
		}
		saveAllInternal(all, filename);
	}

	/**
	 * Use with caution. Overwrites db-file at wrong usage!
	 */
	private void saveReallyAll(Set<ShareValuePeriod> reallyAll) {
		final String filename = filename(FinConfig.get(FinConfig.SHARE_VALUE_DB_FILE));
		saveAllInternal(reallyAll, filename);
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
		
		final List<ShareValuePeriod> reverted = new ArrayList<>();
		reverted.addAll(valids);
		Collections.reverse(reverted);

		for (ShareValuePeriod shareValuePeriod : reverted) {
			csv.add(ShareValuePeriodSerializer.serialize(shareValuePeriod));
		}

		CsvFileDao.write(filename, csv);
	}

	private String filename(final String filename) {
		return FinConfig.get(FinConfig.WORKDIR) + "/" + filename;
	}

	public Set<ShareValuePeriod> findAllFromDatabase(String workdir) {
		final String filename = workdir + "/" + FinConfig.get(FinConfig.SHARE_VALUE_DB_FILE);
		if (!new File(filename).exists()) {
			return new TreeSet<>();
		}
		return loadAll(filename);
	}

	@Deprecated // workdir only default
	public Set<ShareValuePeriod> findAllFromDatabase() {
		return findAllFromDatabase(FinConfig.get(FinConfig.WORKDIR));
	}

	public Set<ShareValuePeriod> loadAll(final String filename) {
		final Csv csv = CsvFileDao.read(filename);

		Set<ShareValuePeriod> all = new TreeSet<>();
		while (csv.hasNext()) {
			all.add(ShareValuePeriodSerializer.deserialize(csv.next()));
		}
		return all;
	}

}
