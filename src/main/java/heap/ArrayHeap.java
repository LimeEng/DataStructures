package heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayHeap<T> implements Heap<T> {

	private final List<T> contents;
	private final Comparator<T> comp;
	private final int order;

	public ArrayHeap(int order) {
		this(order, (arg0, arg1) -> ((Comparable<? super T>) arg0).compareTo((T) arg1));
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
		swap(0, contents.size() - 1);
		T previousRoot = removeLast();
		siftDown(0);
		return previousRoot;
	}

	private T removeLast() {
		return contents.remove(contents.size() - 1);
	}

	private T getRoot() {
		return contents.get(0);
	}

	private void siftUp(int index) {
		T element = contents.get(index);
		int parentPos = getParent(index);

		while (parentPos >= 0) {
			T parent = contents.get(parentPos);
			if (comp.compare(element, parent) < 0) {
				swap(index, parentPos);
				index = parentPos;
				parentPos = getParent(index);
			} else {
				break;
			}
		}
	}

	private void siftDown(int index) {
		while (hasAtLeastOneChild(index)) {
			T element = contents.get(index);
			int smallestChild = getSmallestChildOf(index);
			if (comp.compare(element, contents.get(smallestChild)) > 0) {
				swap(index, smallestChild);
				index = smallestChild;
			} else {
				break;
			}
		}
	}

	private void swap(int i, int j) {
		Collections.swap(contents, i, j);
	}

	private List<Integer> getIndexChildrenOf(int index) {
		return IntStream.range(0, order)
				.map(e -> getChild(index, e))
				.filter(e -> e < size())
				.boxed()
				.collect(Collectors.toList());
	}

	private int getSmallestChildOf(int index) {
		Optional<Integer> child = getIndexChildrenOf(index).stream()
				.min((arg0, arg1) -> {
					return comp.compare(contents.get(arg0), contents.get(arg1));
				});
		return child.get();
	}

	private boolean hasAtLeastOneChild(int index) {
		return getChild(index, 0) < size();
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
