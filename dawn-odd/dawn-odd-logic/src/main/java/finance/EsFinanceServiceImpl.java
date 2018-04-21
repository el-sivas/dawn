package finance;

import de.elsivas.odd.data.finance.EsFinanceDao;

public class EsFinanceServiceImpl implements EsFinanceService {
	
	private EsFinanceDao esFinanceDao;
	private EsFinanceDownloader esFinanceDownloader;
	
	public EsFinanceServiceImpl(EsFinanceDao esFinanceDao) {
		this.esFinanceDao = esFinanceDao;
		this.esFinanceDownloader = new EsFinanceDownloaderAlphaVantageImpl();
	}
	
	

}
