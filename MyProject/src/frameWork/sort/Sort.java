package frameWork.sort;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

import frameWork.event.Event;
import frameWork.event.queue.EventQueue;

public class Sort {
	static final int MIN_RANGE = 40;//1以上必須
	static final int DOUBLE_RANGE = 60;//1以上必須
	static final EventQueue EVENT_QUEUE = new EventQueue();
	
	private static class SortEvent implements Event {
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
				final int index = getPivotIndex(sort.sortables, this);
				if (index == -1) {
					sort.remaining.addAndGet(-len);
					return;
				}
				final int nextTo = swap(sort.sortables, index, this);
				if (to != nextTo) {
					final int len1 = nextTo - from;
					final int len2 = to - nextTo;
					if (MIN_RANGE < (len1)) {
						if (MIN_RANGE < len2) {
							if (60 < len2) {
								if (60 < len1) {
									EVENT_QUEUE.putEvent(new SortEvent(sort, from, nextTo, len1));
									EVENT_QUEUE.putEvent(recycle(nextTo, to, len2));
									return;
								}
								EVENT_QUEUE.putEvent(new SortEvent(sort, nextTo, to, len2));
								recycle(from, nextTo, len1);
								continue;
							}
							EVENT_QUEUE.putEvent(new SortEvent(sort, from, nextTo, len1));
							recycle(nextTo, to, len2);
							continue;
						}
						final int oldTo = to;
						if (DOUBLE_RANGE < len1) {
							EVENT_QUEUE.putEvent(recycle(from, nextTo, len1));
							shortcutSort(sort.sortables, nextTo, oldTo, len2, sort.remaining);
							return;
						}
						recycle(from, nextTo, len1);
						shortcutSort(sort.sortables, nextTo, oldTo, len2, sort.remaining);
						continue;
					}
					else if (MIN_RANGE < len2) {
						final int oldFrom = from;
						if (DOUBLE_RANGE < len2) {
							EVENT_QUEUE.putEvent(recycle(nextTo, to, len2));
							shortcutSort(sort.sortables, oldFrom, nextTo, len1, sort.remaining);
							return;
						}
						recycle(nextTo, to, len2);
						shortcutSort(sort.sortables, oldFrom, nextTo, len1, sort.remaining);
						continue;
					}
					else {
						shortcutSort(sort.sortables, from, nextTo, len1, sort.remaining);
						shortcutSort(sort.sortables, nextTo, to, len2, sort.remaining);
						return;
					}
				}
				shortcutSort(sort.sortables, from, to, len, sort.remaining);
				return;
			}
			while (true);
			
		}
		
		private Event recycle(final int f, final int t, final int l) {
			this.from = f;
			this.to = t;
			this.len = l;
			return this;
		}
	}
	
	static void shortcutSort(final Sortable[] sortables, final int from, final int to, final int len,
	        final AtomicInteger remaining) {
		if (len == 2) {
			final Sortable buffer = sortables[from];
			if (buffer.index() > sortables[from + 1].index()) {
				sortables[from] = sortables[from + 1];
				sortables[from + 1] = buffer;
			}
		}
		else {
			for (int i = from + 1; i < to; i++) {
				final Sortable buffer = sortables[i];
				final int bufferIndex = buffer.index();
				int left = from;
				int right = i;
				if (bufferIndex <= sortables[from].index()) {
					for (int j = i; j > from; j--) {
						sortables[j] = sortables[j - 1];
					}
					sortables[from] = buffer;
				}
				else if (bufferIndex < sortables[i - 1].index()) {
					while (left < right) {
						final int mid = (left + right) >> 1;
						if (bufferIndex < sortables[mid].index()) {
							right = mid;
						}
						else {
							left = mid + 1;
						}
					}
					for (int j = i; j > left; j--) {
						sortables[j] = sortables[j - 1];
					}
					sortables[left] = buffer;
				}
			}
		}
		remaining.addAndGet(-len);
	}
	
	static int swap(final Sortable[] sortables, final int index, final SortEvent sortEvent) {
		final int pivot = sortables[index].index();
		int nextTo = index;
		int limit = sortEvent.to - 1;
		while (nextTo <= limit) {
			while ((sortables[nextTo].index() < pivot) && (nextTo <= limit)) {
				nextTo++;
			}
			while ((sortables[limit].index() >= pivot) && (nextTo <= limit)) {
				limit--;
			}
			if (nextTo <= limit) {
				final Sortable buffer = sortables[nextTo];
				sortables[nextTo++] = sortables[limit];
				sortables[limit--] = buffer;
			}
		}
		return nextTo;
		
	}
	
	static int getPivotIndex(final Sortable[] sortables, final SortEvent sortEvent) {
		final int first = sortables[sortEvent.from].index();
		for (int i = sortEvent.from + 1; i < sortEvent.to; i++) {
			final Sortable buffer = sortables[i];
			final int value = buffer.index();
			if (first < value) {
				return i;
			}
			else if (first > value) {
				return sortEvent.from;
			}
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Sortable> List<T> sort(final List<T> list) {
		final Sortable[] sortables = new Sortable[list.size()];
		int index = 0;
		for (final T t : list) {
			sortables[index++] = t;
		}
		index = 0;
		sort(sortables);
		for (final Sortable sortable : sortables) {
			list.set(index++, (T) sortable);
		}
		return list;
	}
	
	public static <T extends Sortable> T[] sort(final T[] ts) {
		new Sort(ts).sort();
		return ts;
	}
	
	public static void main(final String[] args) {
		JOptionPane.showMessageDialog(null, "are you ready");
		for (int k = 0; k < 10; k++) {
			System.gc();
			System.out.print((k) + " ");
			final int limit = 100000;
			Sortable[] ss = new Sortable[limit];
			for (int i = 0; i < limit; i++) {
				ss[i] = (new Sortable() {
					final int index;
					{
						this.index = ((int) (Math.random() * limit));
					}
					
					@Override
					public int index() {
						return index;
					}
					
					@Override
					public String toString() {
						return "" + index;
					}
				});
			}
			final long s = System.nanoTime();
			ss = sort(ss);
			System.out.print((((System.nanoTime() - s))));
			for (int i = 1; i < limit; i++) {
				if (ss[i].index() < ss[i - 1].index()) {
					System.out.print(" error ");
					break;
				}
			}
			System.out.print("\r\n");
		}
	}
	
	final AtomicInteger remaining;
	final Sortable[] sortables;
	
	private Sort(final Sortable[] sortables) {
		this.sortables = sortables;
		this.remaining = new AtomicInteger(sortables.length);
	}
	
	void sort() {
		final SortEvent sortEvent = new SortEvent(this, 0, sortables.length, sortables.length);
		do {
			final int index = getPivotIndex(sortables, sortEvent);
			if (index == -1) {
				remaining.addAndGet(-sortEvent.len);
			}
			else {
				final int nextTo = swap(sortables, index, sortEvent);
				if (sortEvent.to != nextTo) {
					final int len1 = nextTo - sortEvent.from;
					final int len2 = sortEvent.to - nextTo;
					if (MIN_RANGE < len1) {
						if (MIN_RANGE < len2) {
							if (len1 > len2) {
								EVENT_QUEUE.putEvent(new SortEvent(this, sortEvent.from, nextTo, len1));
								sortEvent.recycle(nextTo, sortEvent.to, len2);
								continue;
							}
							EVENT_QUEUE.putEvent(new SortEvent(this, nextTo, sortEvent.to, len2));
							sortEvent.recycle(sortEvent.from, nextTo, len1);
							continue;
						}
						shortcutSort(sortables, nextTo, sortEvent.to, len2, remaining);
						sortEvent.recycle(sortEvent.from, nextTo, len1);
						continue;
					}
					if (MIN_RANGE < len2) {
						shortcutSort(sortables, sortEvent.from, nextTo, len1, remaining);
						sortEvent.recycle(nextTo, sortEvent.to, len2);
						continue;
					}
					shortcutSort(sortables, sortEvent.from, nextTo, len1, remaining);
					shortcutSort(sortables, nextTo, sortEvent.to, len2, remaining);
					sync();
					return;
				}
				shortcutSort(sortables, sortEvent.from, sortEvent.to, sortEvent.len, remaining);
				sync();
				return;
			}
		}
		while (true);
	}
	
	private void sync() {
		while (this.remaining.get() > 0) {
			try {
				do {
					Thread.sleep(0, 1);
				}
				while (this.remaining.get() > 0);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
}