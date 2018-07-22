import java.math.BigDecimal;

import javax.swing.JOptionPane;

public class DialogTest {

	public static void main(final String[] args) {
		final long start = System.currentTimeMillis();
		final long take = 10000;
		while (System.currentTimeMillis() < start + take) {
			JOptionPane.showMessageDialog(null, "to: " + BigDecimal.valueOf(((start + take - System.currentTimeMillis())) / 1000.0));
		}

	}

}
