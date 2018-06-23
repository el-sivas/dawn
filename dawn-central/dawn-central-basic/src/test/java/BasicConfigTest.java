import org.apache.commons.lang3.BooleanUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.elsivas.basic.BasicConfig;

public class BasicConfigTest {

	@Ignore
	@Test
	public void test() {
		BasicConfig.set("test", String.valueOf(true));
		Assert.assertTrue(BooleanUtils.isTrue(Boolean.valueOf(BasicConfig.get("test"))));
		BasicConfig.set("test", String.valueOf(false));
		Assert.assertFalse(BooleanUtils.isTrue(Boolean.valueOf(BasicConfig.get("test"))));
	}

}
