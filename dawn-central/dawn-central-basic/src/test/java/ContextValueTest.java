import org.junit.Ignore;
import org.junit.Test;

import de.elsivas.basic.BasicConfig;
import de.elsivas.basic.ConfigValue;

public class ContextValueTest {

	private final String context = "test";

	@Ignore
	@Test
	public void test() {
		final ConfigValue configValue = BasicConfig.get(context, "value-b");
		BasicConfig.set(context, "value-b", "A");
		final ConfigValue configValue2 = BasicConfig.get(context, "value-b");

	}

	@Ignore
	@Test
	public void test2() {
		final ConfigValue configValue = BasicConfig.get(context, "value-b");
		BasicConfig.set(context, "value-b", "A");
		final ConfigValue configValue2 = BasicConfig.get(context, "value-b");
	}
}
