package stack;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedStack<T> implements Stack<T> {

	protected LinkedList<T> contents;

	public LinkedStack(Collection<T> c) {
		this();
		push(c);
	}

	public LinkedStack() {
		this.contents = new LinkedList<>();
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
		return contents.getLast();
	}

	@Override
	public T pop() {
		if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
		return contents.removeLast();
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
		if (inclusive < 0 || inclusive > exclusive || exclusive > size()) {
			return false;
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
	public boolean push(Collection<? extends T> c) {
		if (c == null) {
			return false;
		}
		return c.stream()
				.filter(Objects::nonNull)
				.map(contents::add)
				.reduce(false, (a, b) -> a | b);
	}

	@Override
	public Iterator<T> iterator() {
		return contents.descendingIterator();
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
		if (c == null) {
			return false;
		}
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
		for (int i = 0; i < array.length / 2; i++) {
			E temp = array[i];
			array[i] = array[array.length - i - 1];
			array[array.length - i - 1] = temp;
		}
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
