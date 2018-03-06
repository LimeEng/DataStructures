package stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class StackTest<T extends Stack<Integer>> {

	protected T stack;

	protected abstract T createInstance();

	@Before
	public void setUp() {
		stack = createInstance();
	}

	@After
	public void tearDown() {
		stack = null;
	}

	@Test
	public final void peekManyZeroInputNonEmptyStack() {
		stack.push(5);
		stack.push(4);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		List<Integer> list = stack.peek(0);
		assertTrue("Peeking on a stack with zero as argument should return an empty list", list.size() == 0);
		list.clear();
		assertTrue("Peeking should not alter the stack", stack.size() == 5);
	}

	@Test
	public final void peekManyNegativeInputNonEmptyStack() {
		stack.push(5);
		stack.push(4);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		List<Integer> list = stack.peek(-3);
		assertTrue("Peeking on a stack with a negative input should return an empty list", list.size() == 0);
		list.clear();
		assertTrue("Peeking should not alter the stack", stack.size() == 5);
	}

	@Test
	public final void peekManyNegativeInputEmptyStack() {
		List<Integer> list = stack.peek(-5);
		assertTrue("Peeking on an empty stack should return an empty list", list.size() == 0);
		list.clear();
		assertTrue("Peeking should not alter the stack", stack.size() == 0);
	}

	@Test
	public final void peekManyOnEmpty() {
		List<Integer> list = stack.peek(5);
		assertTrue("Peeking on an empty stack should return an empty list", list.size() == 0);
		list.clear();
		assertTrue("Peeking should not alter the stack", stack.size() == 0);
	}

	@Test
	public final void peekMany() {
		stack.push(5);
		stack.push(4);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		List<Integer> list = stack.peek(3);
		for (int i = 0; i < 3; i++) {
			assertTrue("List returned from peek(int k) returns wrong value", list.get(i) == (i + 1));
		}
		assertTrue("List returned from peek(int k) returns incorrect number of values", list.size() == 3);
		list.clear();
		assertTrue("Peeking should not alter the stack", stack.size() == 5);
	}

	@Test
	public final void emptyReverse() {
		boolean changed = stack.reverse();
		assertTrue("Reversing an empty stack should return false", !changed);
	}

	@Test
	public final void oneReverse() {
		stack.push(1);
		boolean changed = stack.reverse();
		assertTrue("Reversing a stack with a single element should return false", !changed);
	}

	@Test
	public final void twoReverse() {
		stack.push(1);
		stack.push(2);
		boolean changed = stack.reverse();
		assertEquals("Wrong element retrieved", new Integer(1), stack.pop());
		assertEquals("Wrong element retrieved", new Integer(2), stack.pop());
		assertTrue("Reversing a stack with two elements should return true", changed);
	}

	@Test
	public final void manyReverse() {
		fillStack(1, 6);
		boolean changed = stack.reverse();
		assertEquals("Wrong element retrieved", new Integer(1), stack.pop());
		assertEquals("Wrong element retrieved", new Integer(2), stack.pop());
		assertEquals("Wrong element retrieved", new Integer(3), stack.pop());
		assertEquals("Wrong element retrieved", new Integer(4), stack.pop());
		assertEquals("Wrong element retrieved", new Integer(5), stack.pop());
		assertTrue("Reversing a stack with two elements should return true", changed);
	}

	@Test
	public final void testNewStack() {
		assertTrue(stack.isEmpty());
		assertTrue(stack.size() == 0);
	}

	@Test
	public final void testPeek() {
		stack.push(1);
		int i = stack.peek();
		assertEquals("Wrong element retrieved", 1, i);
		assertTrue("Wrong size", stack.size() == 1);
	}

	@Test
	public final void testEmptyPeek() {
		boolean thrown = false;
		try {
			stack.peek();
		} catch (NoSuchElementException e) {
			thrown = true;
		}
		assertTrue("Peeking on an empty stack does not throw an exception", thrown);
	}

	@Test
	public final void testEmptyPop() {
		boolean thrown = false;
		try {
			stack.pop();
		} catch (NoSuchElementException e) {
			thrown = true;
		}
		assertTrue("Popping on an empty stack does not throw an exception", thrown);
	}

	@Test
	public final void testPushNull() {
		Integer test = null;
		boolean result = stack.push(test);
		assertFalse("Trying to push null returns true", result);
		assertTrue("Size changed when trying to push null", stack.isEmpty());
	}

	@Test
	public final void testPushNormal() {
		boolean success = stack.push(0);
		assertEquals("Size does not update when pushing item", 1, stack.size());
		assertTrue("Not indicating success on push", success);
	}

	@Test
	public final void testAddNull() {
		Integer test = null;
		boolean result = stack.add(test);
		assertFalse("Trying to add null returns true", result);
	}

	@Test
	public final void testAddNormal() {
		boolean success = stack.add(0);
		assertEquals("Size does not update when adding item", 1, stack.size());
		assertTrue("Not indicating success on add", success);
	}

	@Test
	public final void testPushEmptyCollection() {
		List<Integer> list = new ArrayList<>();
		boolean changed = stack.push(list);
		assertFalse("Pushing an empty collection indicates that something changed", changed);
		assertTrue("Size changed when trying to push an empty collection", stack.isEmpty());
	}

	@Test
	public final void testPushFilledCollection() {
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
		boolean changed = stack.push(list);
		assertTrue("Pushing an empty list indicates that something changed", changed);
		assertEquals("Incorrect size when pushing collection", list.size(), stack.size());
	}

	@Test
	public final void testPushNullCollection() {
		List<Integer> list = null;
		boolean changed = stack.push(list);
		assertFalse("Pushing an null list indicates that something changed", changed);
		assertTrue("Size changed when trying to push a null collection", stack.isEmpty());
	}

	@Test
	public final void testAddEmptyCollection() {
		List<Integer> list = new ArrayList<>();
		boolean changed = stack.addAll(list);
		assertFalse("Adding an empty collection indicates that something changed", changed);
		assertTrue("Size changed when trying to add an empty collection", stack.isEmpty());
	}

	@Test
	public final void testAddFilledCollection() {
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
		boolean changed = stack.addAll(list);
		assertTrue("Adding an empty list indicates that something changed", changed);
		assertEquals("Incorrect size when adding collection", list.size(), stack.size());
	}

	@Test
	public final void testAddNullCollection() {
		List<Integer> list = null;
		boolean changed = stack.addAll(list);
		assertFalse("Adding an null list indicates that something changed", changed);
		assertTrue("Size changed when trying to add a null collection", stack.isEmpty());
	}

	@Test
	public final void testPop() {
		stack.push(1);
		int i = stack.pop();
		assertEquals("Wrong element retrieved", 1, i);
		assertTrue("Wrong size after pop", stack.size() == 0);
	}

	@Test
	public final void testStringStack() {
		Stack<String> stack = new ArrayStack<>();
		stack.push("First");
		stack.push("Second");
		stack.push("Third");
		assertTrue("Wrong size of stack", stack.size() == 3);
		assertEquals("peek on stack of strings", "Third", stack.peek());
		assertEquals("String Third expected", "Third", stack.pop());
		assertEquals("String Second expected", "Second", stack.pop());
		assertEquals("String First expected", "First", stack.pop());
		assertTrue("Stack of strings should be empty", stack.isEmpty());
	}

	@Test
	public final void testOrder() {
		int max = 50;
		int min = 0;
		fillStack(min, max);
		for (int i = max - 1; i >= min; i--) {
			int k = stack.pop();
			assertEquals("Pop returns incorrect element", i, k);
		}
		assertTrue("Stack not empty", stack.isEmpty());
	}

	/**
	 * Tests that popping all elements gives an empty stack.
	 */
	@Test
	public final void testMakeStackEmpty() {
		stack.push(1);
		stack.push(2);
		stack.pop();
		stack.pop();
		assertTrue("Wrong size after pop", stack.size() == 0);
		assertTrue("Stack not empty after pop", stack.isEmpty());
		fillStack(10);
		assertTrue("Wrong size after push", stack.size() == 10);
		for (int i = 9; i >= 0; i--) {
			int k = stack.pop();
			assertEquals("Pop returns incorrect element", i, k);
		}
		assertTrue("Wrong size after pop", stack.size() == 0);
		assertTrue("Stack not empty after pop", stack.isEmpty());
	}

	@Test
	public final void testPopUntilEmpty() {
		fillStack(10);
		int counter = 9;
		while (!stack.isEmpty()) {
			int i = stack.pop();
			assertEquals("Popping until empty returns wrong value", counter, i);
			counter--;
		}
		assertTrue("Size is not updated properly when popping", stack.size() == 0);
	}

	@Test
	public final void testIteratorEmptyStackHasNext() {
		boolean hasNext = stack.iterator()
				.hasNext();
		assertTrue("iterator().hasNext() returning wrong value for empty stack", false == hasNext);
	}

	@Test
	public final void testIteratorFilledStackHasNext() {
		stack.push(3);
		stack.push(5);
		boolean hasNext = stack.iterator()
				.hasNext();
		assertTrue("iterator().hasNext() returning wrong value for filled stack", true == hasNext);
	}

	@Test
	public final void testIteratorEmptyStackNext() {
		boolean thrown = false;
		try {
			stack.iterator()
					.next();
		} catch (NoSuchElementException e) {
			thrown = true;
		}
		assertTrue("iterator().next() not throwing exception with empty stack", thrown);
	}

	@Test
	public final void testIteratorFilledStackNext() {
		stack.push(3);
		stack.push(5);
		int i = stack.iterator()
				.next();
		assertTrue("iterator().next() returning wrong value", i == 5);
	}

	@Test
	public final void testIterationOrder() {
		fillStack(10);
		int counter = 9;
		for (int i : stack) {
			assertEquals("Iterator returning wrong element", i, counter);
			counter--;
		}
	}

	@Test
	public final void testSearchOnEmpty() {
		Optional<Integer> value = stack.search((e) -> e.equals(3));
		assertTrue("Searching in an empty stack returns the wrong value", !value.isPresent());
	}

	@Test
	public final void testSearchOnFilledWhereValuePresent() {
		fillStack(10);
		Optional<Integer> value = stack.search((e) -> e.equals(3));
		assertTrue("Searching in a filled stack returns the wrong value", value.isPresent());
		assertEquals("Searching in a filled stack where the value is present returns the wrong value", 6,
				(int) value.get());
	}

	@Test
	public final void testSearchOnFilledWhereValueAbsent() {
		fillStack(10);
		Optional<Integer> value = stack.search((e) -> e.equals(30));
		assertTrue("Searching in a filled stack where the value is absent returns the wrong value", !value.isPresent());
	}

	@Test
	public final void testSearchOnFilledWhereValueNull() {
		fillStack(10);
		Optional<Integer> value = stack.search(null);
		assertTrue("Searching in a filled stack where the value is absent returns the wrong value", !value.isPresent());
	}

	@Test
	public final void testClear() {
		fillStack(100);
		stack.clear();
		assertTrue("Clearing the stack does not update the size properly", stack.size() == 0);
		assertTrue("Clearing the stack does not update isEmpty() properly", stack.isEmpty());
	}

	@Test
	public final void testPeekWhileFilled() {
		fillStack(10);
		Predicate<Integer> pred = e -> e > 5;
		List<Integer> values = stack.peekWhile(pred);
		assertEquals("peekWhile returns wrong list", 4, values.size());
		assertEquals("peekWhile changed the size of the stack", 10, stack.size());

		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6));
		assertEquals("The returned list does not match the expected one", expected, values);
	}

	@Test
	public final void testPeekWhileEmpty() {
		Predicate<Integer> pred = e -> e > 5;
		List<Integer> values = stack.peekWhile(pred);
		assertTrue("peekWhile returns wrong list", values.isEmpty());
		assertTrue("peekWhile changed the size of the stack", stack.isEmpty());
	}

	@Test
	public final void testPeekWhileNullPredicate() {
		fillStack(10);
		List<Integer> values = stack.peekWhile(null);
		assertTrue("peekWhile with a null predicate returns a filled list", values.isEmpty());
		assertEquals("peekWhile with a null predicate changed the size of the stack", 10, stack.size());
	}

	@Test
	public final void testPopWhileFilled() {
		fillStack(10);
		Predicate<Integer> pred = e -> e > 5;
		List<Integer> values = stack.popWhile(pred);
		assertEquals("popWhile returns wrong list", 4, values.size());
		assertEquals("popWhile did not update size correctly", 6, stack.size());

		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6));
		assertEquals("The returned list does not match the expected one", expected, values);
	}

	@Test
	public final void testPopWhileEmpty() {
		Predicate<Integer> pred = e -> e > 5;
		List<Integer> values = stack.peekWhile(pred);
		assertTrue("popWhile returns wrong list", values.isEmpty());
		assertTrue("popWhile with an empty stack changed the size of the stack", stack.isEmpty());
	}

	@Test
	public final void testPopWhileNullPredicate() {
		fillStack(10);
		List<Integer> values = stack.popWhile(null);
		assertTrue("popWhile with a null predicate returns a filled list", values.isEmpty());
		assertEquals("popWhile with a null predicate changed the size of the stack", 10, stack.size());
	}

	@Test
	public final void testContainsNull() {
		fillStack(10);
		boolean contains = stack.contains(null);
		assertFalse("contains(null) returns true", contains);
		assertEquals("Size is no longer correct", 10, stack.size());
	}

	@Test
	public final void testContainsWithFilled() {
		fillStack(10);
		boolean contains = stack.contains(5);
		assertTrue("contains returns false", contains);
		assertEquals("Size is no longer correct", 10, stack.size());
	}

	@Test
	public final void testContainsWithEmpty() {
		boolean contains = stack.contains(5);
		assertFalse("contains on an empty stack returns true", contains);
		assertEquals("Size is no longer correct", 0, stack.size());
	}

	@Test
	public final void testContainsAllWithNull() {
		fillStack(10);
		boolean contains = stack.containsAll(null);
		assertFalse("containsAll(null) returns true", contains);
		assertEquals("Size is no longer correct", 10, stack.size());
	}

	@Test
	public final void testContainsAllWithPartialNull() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, null, null, 5, 6, 7));
		boolean contains = stack.containsAll(values);
		assertFalse("containsAll returns true on a collection with null values", contains);
		assertEquals("Size is no longer correct", 10, stack.size());
	}

	@Test
	public final void testContainsAllWithFilledInvalidCollection() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 100, 6, 7));
		boolean contains = stack.containsAll(values);
		assertFalse("containsAll returns true", contains);
		assertEquals("Size is no longer correct", 10, stack.size());
	}

	@Test
	public final void testContainsAllWithFilledValidCollection() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 6, 7));
		boolean contains = stack.containsAll(values);
		assertTrue("containsAll returns false", contains);
		assertEquals("Size is no longer correct", 10, stack.size());
	}

	@Test
	public final void testContainsAllWithEmpty() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1));
		boolean contains = stack.containsAll(values);
		assertFalse("containsAll returns true on an empty stack", contains);
		assertEquals("Size is no longer correct", 0, stack.size());
	}

	@Test
	public final void testRemoveSingleNull() {
		fillStack(5);
		boolean removed = stack.remove(null);
		assertFalse("Trying to remove null returns true", removed);
		assertEquals("The size of the stack is not correct", 5, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(4, 3, 2, 1, 0));
		assertEquals("Removing null modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRemoveSingleValidValue() {
		fillStack(10);
		boolean removed = stack.remove(5);
		assertTrue("Trying to remove a valid value returns false", removed);
		assertEquals("The size of the stack is not correct", 9, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 4, 3, 2, 1, 0));
		assertEquals("Removing a valid value modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRemoveSingleInvalidValue() {
		fillStack(10);
		boolean removed = stack.remove(15);
		assertFalse("Trying to remove an invalid value returns true", removed);
		assertEquals("The size of the stack is not correct", 10, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		assertEquals("Removing an invalid value modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRemoveAllNullCollection() {
		fillStack(10);
		boolean modified = stack.removeAll(null);
		assertFalse("Passing a null collection to removeAll returns true", modified);
		assertEquals("The size of the stack is not correct", 10, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		assertEquals("Passing a null collection to removeAll modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRemoveAllPartialNullCollection() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, null, null, null, 8));
		boolean modified = stack.removeAll(values);
		assertTrue("Incorrect return value", modified);
		assertEquals("The size of the stack is not correct", 4, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 7, 6, 0));
		assertEquals("Passing a collection to removeAll modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRemoveAllEmptyCollection() {
		fillStack(10);
		List<Integer> values = new ArrayList<>();
		boolean modified = stack.removeAll(values);
		assertFalse("Incorrect return value", modified);
		assertEquals("The size of the stack is not correct", 10, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		assertEquals("Passing an empty collection to removeAll modifies the stack in an incorrect way", expected,
				actual);
	}

	@Test
	public final void testRemoveAllValidCollection() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3));
		boolean modified = stack.removeAll(values);
		assertTrue("Incorrect return value", modified);
		assertEquals("The size of the stack is not correct", 7, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 0));
		assertEquals("Passing a collection to removeAll modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRemoveAllInvalidCollection() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(15, 16, 17));
		boolean modified = stack.removeAll(values);
		assertFalse("Incorrect return value", modified);
		assertEquals("The size of the stack is not correct", 10, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		assertEquals("Passing a collection to removeAll modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRetainAllNullCollection() {
		fillStack(10);
		boolean modified = stack.retainAll(null);
		assertFalse("Incorrect return value", modified);
		assertEquals("The size of the stack is not correct", 10, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		assertEquals("Passing null to retainAll modifies the stack in an incorrect way", expected, actual);
	}

	@Test
	public final void testRetainAllCollectionWithNulls() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(15, null, null, null, 16, 17));
		boolean modified = stack.retainAll(values);
		assertTrue("Incorrect return value", modified);
		assertTrue("The size of the stack is not correct", stack.isEmpty());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>();
		assertEquals("Passing a collection with nulls to retainAll modifies the stack in an incorrect way", expected,
				actual);
	}

	@Test
	public final void testRetainAllNoneMatching() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(20, 19, 18, 17, 16, 15, 14, 13, 12, 11));
		boolean modified = stack.retainAll(values);
		assertTrue("Incorrect return value", modified);
		assertTrue("The size of the stack is not correct", stack.isEmpty());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>();
		assertEquals(
				"Passing a collection with only values that are not present on the stack, to retainAll modifies the stack in an incorrect way",
				expected, actual);
	}

	@Test
	public final void testRetainAllSomeMatching() {
		fillStack(10);
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 3, 3, 4, 5, 15, 16, 176));
		boolean modified = stack.retainAll(values);
		assertTrue("Incorrect return value", modified);
		assertEquals("The size of the stack is not correct", 6, stack.size());

		List<Integer> actual = stack.popWhile(e -> true);
		List<Integer> expected = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1, 0));
		assertEquals("retainAll does not behave as expected, because it modifies the stack in an incorrect way",
				expected, actual);
	}

	@Test
	public final void testPrettyStringEmptyStack() {
		String string = stack.getPrettyString();
		assertEquals("The pretty string returned from an empty stack is malformed", "[]", string);
	}

	@Test
	public final void testPrettyStringFilledStack() {
		fillStack(10);
		String string = stack.getPrettyString();
		assertEquals("The pretty string returned from an empty stack is malformed", "[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]",
				string);
	}

	@Test
	public final void testBasicEquals() {
		Stack<Integer> s1 = createInstance();
		Stack<Integer> s2 = createInstance();
		testShouldBeEqual(s1, s2);
	}
	
	@Test
	public final void testEqualsFilledStacksReturnsTrue() {
		Stack<Integer> s1 = createInstance();
		Stack<Integer> s2 = createInstance();
		fillStack(s1, 10);
		fillStack(s2, 10);
		testShouldBeEqual(s1, s2);
	}
	
	@Test
	public final void testEqualsFilledStacksReturnsFalse() {
		Stack<Integer> s1 = createInstance();
		Stack<Integer> s2 = createInstance();
		fillStack(s1, 10);
		fillStack(s2, 15);
		testShouldNotBeEqual(s1, s2);
	}
	
	@Test
	public final void testEqualsFilledStacksReturnsFalse2() {
		Stack<Integer> s1 = createInstance();
		Stack<Integer> s2 = createInstance();
		fillStack(s1, 10);
		fillStack(s2, 7);
		s2.add(9);
		s2.add(8);
		s2.add(10);
		testShouldNotBeEqual(s1, s2);
	}

	private void testShouldBeEqual(Object o1, Object o2) {
		assertTrue("According to equals(), the object is not equal to itself", o1.equals(o1));
		assertTrue("According to equals(), the object is not equal to itself", o2.equals(o2));
		assertTrue("According to equals(), the objects are not equal", o1.equals(o2));
		assertTrue("According to equals(), the objects are not equal", o2.equals(o1));
		assertFalse("According to equals(), the object is equal to null", o1.equals(null));
		assertFalse("According to equals(), the object is equal to null", o2.equals(null));
		assertEquals("The two hash codes are not equal", o1.hashCode(), o2.hashCode());
	}
	
	private void testShouldNotBeEqual(Object o1, Object o2) {
		assertTrue("According to equals(), the object is not equal to itself", o1.equals(o1));
		assertTrue("According to equals(), the object is not equal to itself", o2.equals(o2));
		assertFalse("According to equals(), the objects are equal", o1.equals(o2));
		assertFalse("According to equals(), the objects are equal", o2.equals(o1));
		assertFalse("According to equals(), the object is equal to null", o1.equals(null));
		assertFalse("According to equals(), the object is equal to null", o2.equals(null));
	}

	private void fillStack(Stack<Integer> stack, int min, int max) {
		for (int i = min; i < max; i++) {
			stack.push(i);
		}
	}

	private void fillStack(Stack<Integer> stack, int limit) {
		fillStack(stack, 0, limit);
	}

	private void fillStack(int min, int max) {
		for (int i = min; i < max; i++) {
			stack.push(i);
		}
	}

	private void fillStack(int limit) {
		fillStack(0, limit);
	}
}
