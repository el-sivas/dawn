package de.elsivas.x.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Before;
import org.junit.Test;

import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.config.FinConfig;
import de.elsivas.finance.logic.portals.Portal;
import de.elsivas.finance.x.FinanceXDownloader;

public class FincanceXDownloaderTest {
	
	private FinanceXDownloader financeXDownloader;
	private Map<String, String> config = new HashMap<>();
	
	@Before
	public void init() {
		Configurator.setLevel("de.elsivas", Level.DEBUG);
		financeXDownloader = new FinanceXDownloader();
		config.put("portal", Portal.ONVISTA.toString());
		config.put("download-list", Wertpapier.DAX.toString());
		FinConfig.init(config);
	}
	
	@Test
	public void test() {
		financeXDownloader.run();
	}

}
