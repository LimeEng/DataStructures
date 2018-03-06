package stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayStack<T> implements Stack<T> {

	protected List<T> contents;

	public ArrayStack(Collection<T> c) {
		this();
		push(c);
	}

	public ArrayStack() {
		this.contents = new ArrayList<>();
	}

	@Override
	public boolean push(T t) {
		if (t == null) {
			return false;
		}
		return contents.add(t);
	}

	@Override
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
		return contents.get(contents.size() - 1);
	}

	@Override
	public T pop() {
		if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
		return contents.remove(contents.size() - 1);
	}

	@Override
	public boolean rotate(int inclusive, int exclusive, int shift) {
		if (shift % size() == 0 || size() < 2) {
			return false;
		}
		Collections.rotate(contents.subList(contents.size() - exclusive, contents.size() - inclusive), shift);
		return true;
	}

	@Override
	public boolean reverse(int inclusive, int exclusive) {
		if (exclusive > size() || inclusive < 0 || inclusive > exclusive) {
			throw new IllegalArgumentException("Illegal bounds");
		}
		int elementsToReverse = exclusive - inclusive;
		if (elementsToReverse < 2) {
			return false;
		}
		Collections.reverse(contents.subList(contents.size() - exclusive, contents.size() - inclusive));
		return true;
	}

	@Override
	public int size() {
		return contents.size();
	}

	@Override
	public void clear() {
		contents.clear();
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public String toString() {
		return getPrettyString();
	}

	@Override
	public Iterator<T> iterator() {
		return new StackIterator();
	}

	// TODO: Test this iterator
	private class StackIterator implements Iterator<T> {

		private ListIterator<T> iter = contents.listIterator(contents.size());

		@Override
		public boolean hasNext() {
			return iter.hasPrevious();
		}

		@Override
		public void remove() {
			iter.remove();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Stack underflow");
			}
			return iter.previous();
		}
	}

	@Override
	public boolean contains(Object o) {
		return contents.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		return contents.containsAll(c);
	}

	@Override
	public boolean remove(Object o) {
		return contents.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		return contents.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return contents.retainAll(c);
	}

	@Override
	public Object[] toArray() {
		Object[] array = contents.toArray();
		reverseArray(array);
		return array;
	}

	@Override
	public <E> E[] toArray(E[] a) {
		E[] array = contents.toArray(a);
		reverseArray(array);
		return array;
	}

	private <E> void reverseArray(E[] array) {
		Collections.reverse(Arrays.asList(array));
	}

	@Override
	public int hashCode() {
		return contents.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (!(obj instanceof Stack)) {
			return false;
		}
		Stack<?> other = (Stack<?>) obj;
		if (other.size() != this.size()) {
			return false;
		}

		Iterator<T> t1 = this.iterator();
		Iterator<?> t2 = other.iterator();

		while (t1.hasNext()) {
			T o1 = t1.next();
			Object o2 = t2.next();

			if (!o1.equals(o2)) {
				return false;
			}
		}
		return true;
	}
}
