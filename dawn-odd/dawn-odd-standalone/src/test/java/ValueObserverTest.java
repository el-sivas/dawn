import org.junit.jupiter.api.Test;

import de.elsivas.odd.standalone.ValueObserver;

public class ValueObserverTest {
	
	@Test
	public void test() throws InterruptedException {
		ValueObserver.observe("Test", "Object-String");
		ValueObserver.observe("2", "Zwo");
		Thread.sleep(5000);
		ValueObserver.observe("Test", null);
		Thread.sleep(8000);
	}

}
