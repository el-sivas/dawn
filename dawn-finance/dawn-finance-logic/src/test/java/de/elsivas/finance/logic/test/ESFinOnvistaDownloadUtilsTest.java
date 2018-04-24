package de.elsivas.finance.logic.test;

import org.junit.Test;

import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.portals.onvista.FinOnvistaDownloadUtils;

public class ESFinOnvistaDownloadUtilsTest {
	
	@Test
	public void test() {
		System.out.println(FinOnvistaDownloadUtils.downloadToFile(Wertpapier.DAX));
	}

}
