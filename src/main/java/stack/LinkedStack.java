package stack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedStack<T> implements Stack<T> {

	protected LinkedList<T> contents;

	public LinkedStack() {
		this(Collections.emptyList());
	}

	public LinkedStack(Collection<T> c) {
		this.contents = new LinkedList<>();
		push(c);
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
		return contents.descendingIterator();
	}

	@Override
	public boolean contains(Object o) {
		return contents.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return contents.containsAll(c);
	}

	@Override
	public boolean remove(Object o) {
		return contents.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
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
		return Objects.hash(contents);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArrayStack) {
			LinkedStack<?> stack = (LinkedStack<?>) obj;
			return Objects.equals(contents, stack.contents);
		}
		return false;
	}
}
