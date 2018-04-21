package de.elsivas.odd.data.finance;

import java.io.File;

public class EsFinanceDaoFileImpl implements EsFinanceDao {
	
	private File file;
	
	public EsFinanceDaoFileImpl(final String fileName) {
		file = new File(fileName);
	}

}
