package de.elsivas.finance.logic.test;

import org.junit.Test;

import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.portals.onvista.ESFinOnvistaDownloadUtils;

public class ESFinOnvistaDownloadUtilsTest {
	
	@Test
	public void test() {
		System.out.println(ESFinOnvistaDownloadUtils.downloadToFile(Wertpapier.DAX));
	}

}
