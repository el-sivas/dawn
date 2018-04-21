import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import de.elsivas.odd.data.finance.EsFinanceDao;
import de.elsivas.odd.data.finance.EsFinanceDaoFileImpl;
import finance.EsFinanceDownloader;
import finance.EsFinanceDownloaderAlphaVantageImpl;
import finance.EsFinanceService;
import finance.EsFinanceServiceImpl;

public class EsFinanceServiceTest {
	
	private EsFinanceService esFinanceService;
	
	@Before
	public void init() {
		final EsFinanceDao esFinanceDao = new EsFinanceDaoFileImpl("/home/sivas/test/finance.csv");
		esFinanceService = new EsFinanceServiceImpl(esFinanceDao);
	}
	
	@Test
	public void test() throws IOException, URISyntaxException {
		final EsFinanceDownloader esFinanceDownloader = new EsFinanceDownloaderAlphaVantageImpl();
		esFinanceDownloader.download();
	}

}
