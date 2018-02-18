import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class HexTest {

	private static final Log LOG = LogFactory.getLog(HexTest.class);

	@Test
	public void test() {
		int i = 0;
		while (i < 16) {
			assertOk(i++);
		}
	}

	private void assertOk(int i) {
		assertEquals(i, toInt(toHex(i)));
	}

	private String toHex(int i) {
		String result = Integer.toHexString(i);
		LOG.info(i + " -> " + result + "(hex)");
		return result;
	}

	private int toInt(String hex) {
		int result = (int) Long.parseLong(hex, 16);
		LOG.info(hex + " -> " + result + "(10)");
		return result;
	}

}
