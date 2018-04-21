package finance.calc;

import java.util.Comparator;

public class DataReverseComparator implements Comparator<Data> {

	@Override
	public int compare(Data o1, Data o2) {
		return o2.getDate().compareTo(o1.getDate());
	}

}
