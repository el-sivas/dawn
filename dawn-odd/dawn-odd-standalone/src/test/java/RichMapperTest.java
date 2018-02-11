import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import de.elsivas.gol.multi.Rich;
import de.elsivas.gol.multi.RichMapper;

public class RichMapperTest {

	private static final Log LOG = LogFactory.getLog(RichMapperTest.class);

	@Test
	public void test() {
		LOG.info(toString(RichMapper.map(Rich.PLANT, 5)));
		LOG.info(toString(RichMapper.map(Rich.HERBIVORE, 1)));
		LOG.info(toString(RichMapper.map(Rich.CARNIVORE, 31)));
		LOG.info(RichMapper.hex(Rich.CARNIVORE, 31));
		LOG.info(RichMapper.hex(Rich.PLANT, 5));
		LOG.info(RichMapper.getCount(Rich.HERBIVORE, 40));
		
	}

	private String toString(int i) {
		return Integer.toBinaryString(i) + ":" + i;
	}

}
