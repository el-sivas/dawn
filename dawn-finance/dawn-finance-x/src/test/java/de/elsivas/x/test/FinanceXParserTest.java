package de.elsivas.x.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Before;
import org.junit.Test;

import de.elsivas.finance.FinConfig;
import de.elsivas.finance.logic.FinParser;
import de.elsivas.finance.logic.portals.onvista.FinOnvistaDataParserUtils;

public class FinanceXParserTest {

	private FinParser parser;
	private Map<String, String> config = new HashMap<>();

	@Before
	public void init() {
		Configurator.setLevel("de.elsivas", Level.DEBUG);
		parser = FinOnvistaDataParserUtils.getInstance();
		FinConfig.init(config);
	}
	
	@Test
	public void test() {
		parser.parseAndSave();
	}

}
