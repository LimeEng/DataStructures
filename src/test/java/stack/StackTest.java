package stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StackTest {

	private Stack<Integer> stack;

	@Before
	public void setUp() throws Exception {
		stack = new ArrayStack<>();
	}

	@After
	public void tearDown() throws Exception {
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
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
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
		for (int i = min; i < max; i++) {
			stack.push(i);
		}
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
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
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
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
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
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
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
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		Optional<Integer> value = stack.search((e) -> e.equals(3));
		assertTrue("Searching in a filled stack returns the wrong value", value.isPresent());
		assertEquals("Searching in a filled stack where the value is present returns the wrong value", 6,
				(int) value.get());
	}

	@Test
	public final void testSearchOnFilledWhereValueAbsent() {
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		Optional<Integer> value = stack.search((e) -> e.equals(30));
		assertTrue("Searching in a filled stack where the value is absent returns the wrong value", !value.isPresent());
	}

	@Test
	public final void testSearchOnFilledWhereValueNull() {
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		Optional<Integer> value = stack.search(null);
		assertTrue("Searching in a filled stack where the value is absent returns the wrong value", !value.isPresent());
	}

	@Test
	public final void testClear() {
		for (int i = 0; i < 100; i++) {
			stack.push(i);
		}
		stack.clear();
		assertTrue("Clearing the stack does not update the size properly", stack.size() == 0);
		assertTrue("Clearing the stack does not update isEmpty() properly", stack.isEmpty());
	}

}

