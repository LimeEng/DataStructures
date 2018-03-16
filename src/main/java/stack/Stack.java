package stack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Stack<T> extends Collection<T> {

	/**
	 * Pushes an element onto the stack. Null values are ignored.
	 * 
	 * @param t
	 *            - value to be added.
	 * @return true if the value was added.
	 */
	boolean push(T t);

	/**
	 * Returns the value on top of the stack, but does not remove it.
	 * 
	 * @return the last added value.
	 * @throws NoSuchElementException
	 *             if the stack is empty.
	 */
	T peek();

	/**
	 * Returns and removes the value on top of the stack.
	 * 
	 * @return the last added value.
	 * @throws NoSuchElementException
	 *             if the stack is empty.
	 */
	T pop();

	/**
	 * Returns true if the stack has reached maximum capacity.
	 * 
	 * @return true if the stack has reached maximum capacity.
	 */
	boolean isFull();

	/**
	 * Rotates the entire stack by the specified amount.
	 * 
	 * @param shift
	 *            - the distance to rotate the list. There are no constraints on
	 *            this value; it may be zero, negative, or greater than
	 *            stack.size().
	 * @return true if the stack changed as a result of the call.
	 */
	default boolean rotate(int shift) {
		return rotateTop(size(), shift);
	}

	/**
	 * Rotates from the top of the stack to the specified index (exclusive). The
	 * top of the stack is treated as index 0.
	 * 
	 * @param exclusive
	 *            - how many elements that will be affected.
	 * @param shift
	 *            - the distance to rotate the list. There are no constraints on
	 *            this value; it may be zero, negative, or greater than
	 *            stack.size().
	 * @return true if the stack changed as a result of the call.
	 */
	default boolean rotateTop(int exclusive, int shift) {
		return rotate(0, exclusive, shift);
	}

	/**
	 * Rotates the stack from the specified starting index (inclusive) to the
	 * specified end index (exclusive), by the specified amount. The top of the
	 * stack is treated as index 0.
	 * 
	 * @param inclusive
	 *            - The starting index of the rotation operation.
	 * @param exclusive
	 *            - the end index of the rotation operation.
	 * @param shift
	 *            - the distance to rotate the list. There are no constraints on
	 *            this value; it may be zero, negative, or greater than
	 *            stack.size().
	 * @return true if the stack changed as a result of the call.
	 */
	default boolean rotate(int inclusive, int exclusive, int shift) {
		if (exclusive > size() || inclusive < 0 || inclusive > exclusive) {
			throw new IllegalArgumentException("Illegal bounds");
		}
		if (shift % size() == 0 || size() < 2) {
			return false;
		}
		List<T> contents = pop(size());
		Collections.rotate(contents.subList(inclusive, exclusive), shift);
		Collections.reverse(contents);
		push(contents);
		return true;
	}

	default boolean reverse(int inclusive, int exclusive) {
		if (exclusive > size() || inclusive < 0 || inclusive > exclusive) {
			throw new IllegalArgumentException("Illegal bounds");
		}
		int elementsToReverse = exclusive - inclusive;
		if (elementsToReverse < 2) {
			return false;
		}
		List<T> poppedElements = pop(inclusive);
		push(pop(elementsToReverse));
		ListIterator<T> iter = poppedElements.listIterator(poppedElements.size());
		while (iter.hasPrevious()) {
			push(iter.previous());
		}
		return true;
	}

	default boolean reverseTop(int exclusive) {
		return reverse(0, exclusive);
	}

	/**
	 * Reverses the stack.
	 * 
	 * @return true if the stack changed as a result of the call.
	 */
	default boolean reverse() {
		return reverseTop(size());
	}

	/**
	 * Attempts to swap the two elements on top of the stack.
	 * 
	 * @return true if the stack changed as a result of the call.
	 */
	default boolean swap() {
		return reverseTop(2);
	}

	/**
	 * Returns an Optional<Integer> describing the "depth" from the top to the
	 * first element matching the predicate, if any. The Optional<Integer> will
	 * contain the value 0 if the value was found on top of the stack. Returns
	 * an empty Optional <Integer> if the input parameter is null, or if the
	 * value was not found.
	 * 
	 * @param predicate
	 *            - the predicate that is used to test the elements.
	 * @return an Optional<Integer> describing the depth from the top.
	 */
	default Optional<Integer> search(Predicate<? super T> predicate) {
		if (predicate == null) {
			return Optional.empty();
		}
		int counter = 0;
		for (T item : this) {
			if (predicate.test(item)) {
				return Optional.of(counter);
			}
			counter++;
		}
		return Optional.empty();
	}

	/**
	 * Pops off (at most) k elements from the stack and returns the result as a
	 * list. Will not throw exceptions.
	 * 
	 * @param k
	 *            - the maximum amount of objects to pop off the stack.
	 * @return a list containing at most k objects.
	 */
	default List<T> pop(int k) {
		int size = (k < 0 || size() < k) ? size() : k;
		List<T> list = new ArrayList<>(size);
		int counter = 0;
		while (!isEmpty() && counter < k) {
			list.add(pop());
			counter++;
		}
		return list;
	}

	// TODO: Test and in JDK 9 convert to stream (takeWhile)
	default List<T> popWhile(Predicate<T> pred) {
		if (pred == null) {
			return new ArrayList<>();
		}
		List<T> poppedValues = new ArrayList<>();
		while (!isEmpty()) {
			if (pred.test(peek())) {
				poppedValues.add(pop());
			} else {
				break;
			}
		}
		return poppedValues;
	}

	// TODO: Test
	default List<T> peekWhile(Predicate<T> pred) {
		if (pred == null) {
			return new ArrayList<>();
		}
		List<T> peekedValues = new ArrayList<>();
		Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			T value = iter.next();
			if (pred.test(value)) {
				peekedValues.add(value);
			} else {
				break;
			}
		}
		return peekedValues;
	}

	/**
	 * Pushes the entire collection to the stack, rejecting any null values.
	 * Behaves as if individual values are pushed.
	 * 
	 * @param c
	 *            - the collection to add.
	 * @return true if the stack changed as a result of the call.
	 */
	default boolean push(Collection<? extends T> c) {
		if (c == null) {
			return false;
		}
		return c.stream()
				.filter(Objects::nonNull)
				.map(this::push)
				.reduce(false, (a, b) -> a | b);
	}

	/**
	 * 
	 * Collects (at most) k elements from the stack and returns the result as a
	 * list. The stack is unchanged after the call. Will not throw exceptions.
	 * 
	 * @param k
	 *            - the maximum amount of objects to retrieve from the stack.
	 * @return a list containing at most k objects.
	 */
	default List<T> peek(int k) {
		if (k < 0) {
			return new ArrayList<>();
		}
		return stream().limit(k)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Identical to push(e).
	 * 
	 * @param e
	 *            - the element to add.
	 * @return true if the stack changed as a result of the call.
	 */
	@Override
	default boolean add(T e) {
		return push(e);
	}

	/**
	 * Identical to push(c).
	 * 
	 * @param c
	 *            - the collection to add.
	 * @return true if the stack changed as a result of the call.
	 */
	@Override
	default boolean addAll(Collection<? extends T> c) {
		return push(c);
	}

	/**
	 * Checks if the given object is contained in the stack.
	 * 
	 * @param obj
	 *            - the object to find.
	 * @return true if the stack contains the specified object, i.e if at least
	 *         one element e returns true for e.equals(obj).
	 */
	@Override
	default boolean contains(Object obj) {
		if (obj == null) {
			return false;
		}
		return search(e -> e.equals(obj)).isPresent();
	}

	/**
	 * Checks if all the elements in the collection are contained in the stack.
	 * 
	 * @param c
	 *            - collection to be checked for containment in this collection.
	 * @return true if at least one element returns true for each object in the
	 *         collection e.equals(obj).
	 */
	@Override
	default boolean containsAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		return c.stream()
				.allMatch(this::contains);
	}

	@Override
	default boolean remove(Object o) {
		if (o == null) {
			return false;
		}
		List<T> contents = pop(size());
		Collections.reverse(contents);
		boolean changed = contents.remove(o);
		push(contents);
		return changed;
	}

	/**
	 * Removes from this list all of its elements that are contained in the
	 * specified collection.
	 *
	 * @param c
	 *            collection containing elements to be removed from this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws ClassCastException
	 *             if the class of an element of this list is incompatible with
	 *             the specified collection (<a href=
	 *             "Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if this list contains a null element and the specified
	 *             collection does not permit null elements (<a href=
	 *             "Collection.html#optional-restrictions">optional</a>), or if
	 *             the specified collection is null
	 * @see Collection#contains(Object)
	 */
	@Override
	default boolean removeAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		// TODO: Transform into a, preferably, stream based solution
		boolean changed = false;
		Iterator<?> iter = c.iterator();
		while (iter.hasNext()) {
			boolean change = remove(iter.next());
			if (change) {
				changed = change;
			}
		}
		return changed;
	}

	/**
	 * Retains only the elements in this list that are contained in the
	 * specified collection. In other words, removes from this list all of its
	 * elements that are not contained in the specified collection.
	 *
	 * @param c
	 *            collection containing elements to be retained in this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws ClassCastException
	 *             if the class of an element of this list is incompatible with
	 *             the specified collection (<a href=
	 *             "Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if this list contains a null element and the specified
	 *             collection does not permit null elements (<a href=
	 *             "Collection.html#optional-restrictions">optional</a>), or if
	 *             the specified collection is null
	 * @see Collection#contains(Object)
	 */
	@Override
	default boolean retainAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		List<T> contents = pop(size());
		Collections.reverse(contents);
		boolean changed = contents.retainAll(c);
		push(contents);
		return changed;
	}

	/**
	 * Returns whether or not the stack contains any elements.
	 * 
	 * @return true if the stack is empty.
	 */
	@Override
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns an array containing all of the elements in this collection.
	 * 
	 * @return an array containing all of the elements in this collection.
	 */
	@Override
	default Object[] toArray() {
		Object[] result = new Object[size()];
		int i = 0;
		for (T item : this) {
			result[i++] = item;
		}
		return result;
	}

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element); the runtime type of the returned
	 * array is that of the specified array. If the list fits in the specified
	 * array, it is returned therein. Otherwise, a new array is allocated with
	 * the runtime type of the specified array and the size of this list.
	 *
	 * <p>
	 * If the list fits in the specified array with room to spare (i.e., the
	 * array has more elements than the list), the element in the array
	 * immediately following the end of the collection is set to <tt>null</tt>.
	 * (This is useful in determining the length of the list <i>only</i> if the
	 * caller knows that the list does not contain any null elements.)
	 *
	 * @param a
	 *            the array into which the elements of the list are to be
	 *            stored, if it is big enough; otherwise, a new array of the
	 *            same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws ArrayStoreException
	 *             if the runtime type of the specified array is not a supertype
	 *             of the runtime type of every element in this list
	 * @throws NullPointerException
	 *             if the specified array is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	default <E> E[] toArray(E[] a) {
		int size = size();
		if (a.length < size) {
			a = (E[]) java.lang.reflect.Array.newInstance(a.getClass()
					.getComponentType(), size);
		}
		Object[] result = a;
		int i = 0;
		for (T item : this) {
			result[i++] = item;
		}

		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}

	/**
	 * Subclasses are encouraged to return this string in their toString method,
	 * to provide uniformity and lessen the burden on the implementing
	 * developer. The first "element" in the string is on top of the stack
	 * 
	 * @return a formatted string representing the stack.
	 */
	default String getPrettyString() {
		StringBuilder sb = new StringBuilder();
		Iterator<T> iter = iterator();
		sb.append("[");
		while (iter.hasNext()) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Identical to System.out.println(stack.toString()). Used exclusively for
	 * debugging.
	 */
	default void print() {
		System.out.println(toString());
	}
}
