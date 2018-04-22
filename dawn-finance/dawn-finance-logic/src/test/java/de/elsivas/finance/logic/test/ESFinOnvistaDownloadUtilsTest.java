package de.elsivas.finance.logic.test;

import org.junit.Test;

import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.download.ESFinOnvistaDownloadUtils;

public class ESFinOnvistaDownloadUtilsTest {
	
	@Test
	public void test() {
		System.out.println(ESFinOnvistaDownloadUtils.download(Wertpapier.DAX));
	}

}
