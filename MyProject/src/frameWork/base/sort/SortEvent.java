package frameWork.base.sort;

import frameWork.base.core.event.Event;

class SortEvent implements Event {
	final Sort sort;
	int from;
	int to;
	int len;
	
	SortEvent(final Sort sort, final int from, final int to, final int len) {
		this.sort = sort;
		this.from = from;
		this.to = to;
		this.len = len;
	}
	
	@Override
	public void run() {
		do {
			final int index = Sort.getPivotIndex(sort.sortables, this);
			if (index == -1) {
				sort.remaining.addAndGet(-len);
				return;
			}
			final int nextTo = Sort.swap(sort.sortables, index, this);
			if (to != nextTo) {
				final int len1 = nextTo - from;
				final int len2 = to - nextTo;
				if (Sort.MIN_RANGE < (len1)) {
					if (Sort.MIN_RANGE < len2) {
						if (60 < len2) {
							if (60 < len1) {
								Sort.EVENT_QUEUE.putEvent(new SortEvent(sort, from, nextTo, len1));
								Sort.EVENT_QUEUE.putEvent(recycle(nextTo, to, len2));
								return;
							}
							Sort.EVENT_QUEUE.putEvent(new SortEvent(sort, nextTo, to, len2));
							recycle(from, nextTo, len1);
							continue;
						}
						Sort.EVENT_QUEUE.putEvent(new SortEvent(sort, from, nextTo, len1));
						recycle(nextTo, to, len2);
						continue;
					}
					final int oldTo = to;
					if (Sort.DOUBLE_RANGE < len1) {
						Sort.EVENT_QUEUE.putEvent(recycle(from, nextTo, len1));
						Sort.shortcutSort(sort.sortables, nextTo, oldTo, len2, sort.remaining);
						return;
					}
					recycle(from, nextTo, len1);
					Sort.shortcutSort(sort.sortables, nextTo, oldTo, len2, sort.remaining);
					continue;
				}
				else if (Sort.MIN_RANGE < len2) {
					final int oldFrom = from;
					if (Sort.DOUBLE_RANGE < len2) {
						Sort.EVENT_QUEUE.putEvent(recycle(nextTo, to, len2));
						Sort.shortcutSort(sort.sortables, oldFrom, nextTo, len1, sort.remaining);
						return;
					}
					recycle(nextTo, to, len2);
					Sort.shortcutSort(sort.sortables, oldFrom, nextTo, len1, sort.remaining);
					continue;
				}
				else {
					Sort.shortcutSort(sort.sortables, from, nextTo, len1, sort.remaining);
					Sort.shortcutSort(sort.sortables, nextTo, to, len2, sort.remaining);
					return;
				}
			}
			Sort.shortcutSort(sort.sortables, from, to, len, sort.remaining);
			return;
		}
		while (true);
		
	}
	
	Event recycle(final int f, final int t, final int l) {
		this.from = f;
		this.to = t;
		this.len = l;
		return this;
	}
}