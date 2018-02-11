package de.elsivas.gol.multi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RichMapper {

	public static short map(Rich rich, int count) {
		int binaryRich = rich.binary();
		if (count >= Math.pow(2, 5)) {
			throw new RuntimeException("not supported count");
		}
		return (short) (binaryRich + count);
	}

	public static String hex(Rich rich, int count) {
		final String hexString = Integer.toHexString(map(rich, count));
		return hexString.length() < 2 ? "0" + hexString : hexString;
	}

	public static int getCount(final Rich target, final int i) {
		int ordinal = target.ordinal();
		List<Rich> collect = Arrays.asList(Rich.values()).stream().filter(e -> e.ordinal() <= ordinal)
				.collect(Collectors.toList());
		int cnt = i;
		for (final Rich rich : collect) {
			cnt -= rich.binary();
		}
		return cnt;
	}
}
