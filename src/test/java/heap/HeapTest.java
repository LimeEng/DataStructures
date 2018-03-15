package heap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class HeapTest<T extends Heap<Integer>> {

	protected T heap;

	protected abstract T createInstance(int order);

	@Before
	public void setUp() {
		heap = createInstance(3);
	}

	@After
	public void tearDown() {
		heap = null;
	}

	@Test
	public void testEmptyWhenCreated() {
		assertTrue(heap.isEmpty());
	}

	@Test
	public void testNotEmptyAfterAdd() {
		heap.offer(50);
		assertFalse(heap.isEmpty());
	}

	@Test
	public void testBasicPoll() {
		IntStream.range(0, 10).forEach(heap::offer);
		for (int i = 0; i < 10; i++) {
			assertEquals("Polling does not work correctly", i, (int) heap.poll());
			assertEquals("Size is not updated correctly", 10 - (i + 1), heap.size());
		}
	}

	@Test
	public void testBasicOffer() {
		for (int i = 0; i < 10; i++) {
			boolean success = heap.offer(i);
			assertTrue("Wrong success indication", success);
			assertEquals("Size is not updated correctly", i + 1, heap.size());
		}
		boolean success = heap.offer(null);
		assertFalse("Wrong success indication", success);
		assertEquals("Size update incorrectly", 10, heap.size());
	}

	@Test
	public void testBasicPeek() {
		IntStream.range(0, 10).forEach(heap::offer);
		List<Integer> peeked = Stream.generate(() -> heap.peek()).limit(100).collect(Collectors.toList());
		assertEquals("Peek returns wrong value", 0, (int) peeked.get(0));
		assertEquals("Peek multiple times returns different values", 1, peeked.stream().distinct().count());
	}
}
