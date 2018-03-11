package heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayHeap<T extends Comparable<? super T>> implements Heap<T> {

	private final List<T> contents;
	private final Comparator<T> comp;
	private final int order;

	public ArrayHeap(int order) {
		this(order, (arg0, arg1) -> arg0.compareTo(arg1));
	}

	public ArrayHeap(int order, Comparator<T> comp) {
		if (order < 1) {
			throw new IllegalArgumentException("The order must be positive");
		}
		this.contents = new ArrayList<>();
		this.order = order;
		this.comp = comp;
	}

	@Override
	public boolean offer(T t) {
		if (t == null) {
			return false;
		}
		boolean success = contents.add(t);
		siftUp(size() - 1);
		return success;
	}

	@Override
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException("Heap underflow");
		}
		return getRoot();
	}

	@Override
	public T poll() {
		if (isEmpty()) {
			throw new NoSuchElementException("Heap underflow");
		}

		T previousRoot = setRoot(getLast());
		siftDown(0);
		return previousRoot;
	}

	private T getLast() {
		return contents.get(contents.size() - 1);
	}

	private T getRoot() {
		return contents.get(0);
	}

	private T setRoot(T item) {
		return contents.set(0, item);
	}

	private void siftUp(int index) {
		T element = contents.get(index);
		int parentPos = getParent(index);

		while (parentPos >= 0) {
			T parent = contents.get(parentPos);
			if (element.compareTo(parent) < 0) {
				swap(index, parentPos);
				// contents.set(index, parent);
				index = parentPos;
				parentPos = getParent(index);
			} else {
				break;
			}
		}
		// contents.set(index, element);
	}

	private void siftDown(int index) {
		while (index < size()) {
			T element = contents.get(index);
			int biggestChild = getBiggestChildOf(index);
			if (element.compareTo(contents.get(biggestChild)) > 0) {
				swap(index, biggestChild);
			} else {
				break;
			}
		}
	}

	private void swap(int i, int j) {
		Collections.swap(contents, i, j);
	}

	private List<T> getChildrenOf(int index) {
		return IntStream.range(0, order)
				.map(e -> getChild(index, e))
				.filter(e -> e < size())
				.mapToObj(contents::get)
				.collect(Collectors.toList());
	}

	private List<Integer> getIndexChildrenOf(int index) {
		return IntStream.range(0, order)
				.map(e -> getChild(index, e))
				.filter(e -> e < size())
				.boxed()
				.collect(Collectors.toList());
	}

	private int getBiggestChildOf(int index) {
		Optional<Integer> child = getIndexChildrenOf(index).stream()
				.max((arg0, arg1) -> {
					return comp.compare(contents.get(arg0), contents.get(arg1));
				});
		return child.get();
	}

	private int getParent(int index) {
		return (index - 1) / order;
	}

	// When number == 0, the index of the first child is returned.
	private int getChild(int index, int number) {
		return (order * index) + number + 1;
	}

	@Override
	public int size() {
		return contents.size();
	}

	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}
}
