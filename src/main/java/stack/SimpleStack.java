package stack;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SimpleStack<T> implements Stack<T> {

	protected LinkedList<T> contents;

	public SimpleStack(Collection<T> c) {
		this();
		push(c);
	}

	public SimpleStack() {
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
	public int size() {
		return contents.size();
	}

	@Override
	public Iterator<T> iterator() {
		return contents.descendingIterator();
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
