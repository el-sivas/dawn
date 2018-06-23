import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;

import de.elsivas.basic.ConsoleUtils;

public class ConsoleUtilsTest {

	private static Log LOG = LogFactory.getLog(ConsoleUtilsTest.class);

	@Ignore
	@Test
	public void test() {
		LOG.error(ConsoleUtils.determineUserHome());

	}

}
