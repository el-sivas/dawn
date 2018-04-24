import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import de.elsivas.mail.logic.SMLogicException;
import de.elsivas.mail.logic.SMUtils;

public class EmailTest {
	
	private static final Log LOG = LogFactory.getLog(EmailTest.class);
	
	@Test
	public void test() throws SMLogicException {
		LOG.info(SMUtils.extractMail("   mail@mail.de   "));
		LOG.info(SMUtils.extractMail("asdf   x@y.z   asdf"));
		LOG.info(SMUtils.extractMail("asdf    asdf"));
		LOG.info(SMUtils.extractMail("Sebastian Lipp <mail@sebastianlipp.de>"));
	}

}
