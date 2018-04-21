package finance.deprecated;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class EsFinanceDownloaderAlphaVantageImpl implements EsFinanceDownloader{

	private String s = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=1min&apikey=KIEKE2T46J78XFPM&datatype=csv";
	
	@Override
	public void download() throws IOException, URISyntaxException {
		final URL website = new URL(url());
		try (InputStream in = website.openStream()) {
		    Files.copy(in, getTmpFile(), StandardCopyOption.REPLACE_EXISTING);
		}
		s.length();
	}
	
	private String url() {
		final StringBuilder sb = new StringBuilder();
		sb.append("https://www.alphavantage.co/query?");
		sb.append("function=TIME_SERIES_INTRADAY&");
		sb.append("symbol=MSFT&");
		sb.append("interval=30min&");
		sb.append("apikey=KIEKE2T46J78XFPM&");

		sb.append("datatype=csv");
		
		return sb.toString();
	}
	
	private Path getTmpFile() throws URISyntaxException {
		return new File("/tmp/finance.txt").toPath();
	}	
}
