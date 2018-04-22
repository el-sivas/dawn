import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.filedao.KeyValueDao;

public class KeyValueDaoTest {

	private static final String FILENAME = "/tmp/key-value.txt";

	@Before
	public void init() {
		final Map<String, String> values = new HashMap<>();
		values.put("WKN", "846900");
		values.put("Type", "Index");
		values.put("Name", "DAX");
		KeyValueDao.write(FILENAME, values);
	}

	@Test
	public void test() {
		final Map<String, String> read = KeyValueDao.read(FILENAME);
		read.keySet().forEach(e -> System.out.println(e + ":" + read.get(e)));
	}

}
