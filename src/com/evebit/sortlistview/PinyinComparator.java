package com.evebit.sortlistview;

import java.util.Comparator;

/**
 * author tianbing*/

public class PinyinComparator implements Comparator<SortModer> {

	@Override
	public int compare(SortModer o1, SortModer o2) {
		if (o1.getFirstletter().equals("@")
				|| o2.getFirstletter().equals("#")) {
			return -1;
		} else if (o1.getFirstletter().equals("#")
				|| o2.getFirstletter().equals("@")) {
			return 1;
		} else {
			return o1.getFirstletter().compareTo(o2.getFirstletter());
		}
	}

}
