package de.elsivas;

import org.junit.Before;
import org.junit.Test;

import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.download.FinDownloadUtils;

public class FinDownloadUtilsTest {

	private FinProperties properties;

	@Test
	public void test() {

		final StringBuilder sb = new StringBuilder();
		FinDownloadUtils.download(properties, sb);
		System.out.println(sb);
	}

	@Before
	public void init() {
		String[] args = "-p onvista -w /tmp -d dax".split(" ");		
		properties = FinDownloadUtils.parseArgsToProperties(args);

	}

}
