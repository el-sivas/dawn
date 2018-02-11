import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LoopTest {

	@Test
	public void test1() {
		List<Object> objekte = getObjekte();
		for (int i = 0; i < objekte.size(); i++) {
			print(objekte.get(i));
		}
	}

	@Test
	public void test2() {
		List<Object> objekte = getObjekte();
		for (Object object : objekte) {
			print(object);			
		}
	}

	@Test
	public void test3() {
		List<Object> objekte = getObjekte();
		objekte.forEach(e -> print(e));
	}

	private void print(Object object) {
		System.out.println(object);
	}

	private List<Object> getObjekte() {
		List<Object> objekte = new ArrayList<>();
		objekte.add(new Object() {
			@Override
			public String toString() {
				return "eins";
			}
		});
		objekte.add(new Object() {
			@Override
			public String toString() {
				return "zwei";
			}
		});
		objekte.add(new Object() {
			@Override
			public String toString() {
				return "drei";
			}
		});
		return objekte;
	}

}
