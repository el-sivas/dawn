import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JOptionPane;

import org.junit.Test;

import de.elsivas.basic.BasicConfig;
import de.elsivas.basic.ConfigValue;
import de.elsivas.basic.DateUtils;

public class DialogBasicConfigTimeTest {

	@Test
	public void test() {
		final int takeTimeInMinutes = 1;
		final String valueOf = String
				.valueOf(DateUtils.toDate(LocalDateTime.now().plusMinutes(takeTimeInMinutes)).getTime());
		BasicConfig.set("test", "timeTo", valueOf);

		final ConfigValue configValue = BasicConfig.get("test", "timeTo");
		final Date date = new Date(configValue.asLong());
		final LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
		while (localDateTime.isAfter(LocalDateTime.now())) {
			
			JOptionPane.showMessageDialog(null, "test");
		}
	}
}
