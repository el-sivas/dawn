package de.elsivas.finance.logic.imports;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;
import de.elsivas.finance.logic.FinProperties;

public class FinImportUtils {

	private static final ShareValuePeriodFileDao SHARE_VALUE_PERIOD_FILE_DAO = ShareValuePeriodFileDao.getInstance();

	public static void importData(FinProperties properties, Protocolant protocolant) {
		final String workdir = properties.getWorkdir();
		final File file = new File(workdir);
		final List<File> allFiles = Arrays.asList(file.listFiles());
		final String importFilePrefix = FinConfig.get(FinConfig.IMPORT_FILE_PREFIX);
		final List<File> importFiles = allFiles.stream().filter(e -> e.getName().startsWith(importFilePrefix))
				.collect(Collectors.toList());
		final Set<ShareValuePeriod> allImported = new TreeSet<>();
		for (File importFile : importFiles) {
			final Set<ShareValuePeriod> loadAll = SHARE_VALUE_PERIOD_FILE_DAO.loadAll(importFile.getAbsolutePath());
			final List<ShareValuePeriod> newbies = loadAll.stream().filter(e -> !allImported.contains(e))
					.collect(Collectors.toList());

			allImported.addAll(newbies);
		}

		final Set<ShareValuePeriod> wholeDb = SHARE_VALUE_PERIOD_FILE_DAO.findAllFromDatabase(workdir);

		final List<ShareValuePeriod> newbies = allImported.stream().filter(e -> !wholeDb.contains(e))
				.collect(Collectors.toList());

		protocolant.append("new: " + newbies.size());
		wholeDb.addAll(newbies);

		SHARE_VALUE_PERIOD_FILE_DAO.saveAll(wholeDb, workdir + "/" + FinConfig.get(FinConfig.SHARE_VALUE_DB_FILE),
				true);

	}

	public static Options getOptions() {
		final Options options = new Options();
		options.addOption(new Option(FinProperties.ARG_WORKDIR, true, "workdir"));
		return options;
	}

}
