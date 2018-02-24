package stack;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

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
		return Objects.hash(contents);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleStack) {
			SimpleStack<?> stack = (SimpleStack<?>) obj;
			return Objects.equals(contents, stack.contents);
		}
		return false;
	}

}
