package de.elsivas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import de.elsivas.finance.data.model.ShareValuePeriod;

public interface CurrentDataHolder {

	Collection<ShareValuePeriod> getData();

	default Collection<ShareValuePeriod> getDataOrderedIncreasing() {
		return getOrderedIncreasing(increasingComparator());
	}

	default List<ShareValuePeriod> getDataOrderedDecreasing() {
		return Collections.unmodifiableList(new ArrayList<>(getOrderedIncreasing(decreasingComparator())));
	}

	default Collection<ShareValuePeriod> getOrderedIncreasing(Comparator<ShareValuePeriod> c) {
		final TreeSet<ShareValuePeriod> treeSet = new TreeSet<>(c);
		treeSet.addAll(getData());
		return treeSet;
	}

	default Comparator<ShareValuePeriod> decreasingComparator() {
		final Comparator<ShareValuePeriod> comparator = new Comparator<ShareValuePeriod>() {

			@Override
			public int compare(ShareValuePeriod o1, ShareValuePeriod o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		};
		return comparator;
	}

	default Comparator<ShareValuePeriod> increasingComparator() {
		final Comparator<ShareValuePeriod> comparator = new Comparator<ShareValuePeriod>() {

			@Override
			public int compare(ShareValuePeriod o1, ShareValuePeriod o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		};
		return comparator;
	}

}
