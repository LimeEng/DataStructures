package heap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
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
		IntStream.range(0, 10)
				.forEach(heap::offer);
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
			if (i + 1 == 0) {
				assertTrue("isEmpty returns wrong result", heap.isEmpty());
			} else {
				assertFalse("isEmpty returns wrong result", heap.isEmpty());
			}
		}
		boolean success = heap.offer(null);
		assertFalse("Wrong success indication", success);
		assertEquals("Size update incorrectly", 10, heap.size());
	}

	@Test
	public void testBasicPeek() {
		IntStream.range(0, 10)
				.forEach(heap::offer);
		List<Integer> peeked = Stream.generate(heap::peek)
				.limit(100)
				.collect(Collectors.toList());
		assertEquals("Peek returns wrong value", 0, (int) peeked.get(0));
		assertEquals("Peek multiple times returns different values", 1, peeked.stream()
				.distinct()
				.count());
	}

	@Test
	public void testEmptyPeek() {
		boolean exceptionThrown = false;
		try {
			heap.peek();
		} catch (NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue("No exception thrown for peeking on an empty heap", exceptionThrown);
	}

	@Test
	public void testEmptyPoll() {
		boolean exceptionThrown = false;
		try {
			heap.poll();
		} catch (NoSuchElementException e) {
			exceptionThrown = true;
		}
		assertTrue("No exception thrown for polling on an empty heap", exceptionThrown);
	}

	@Test
	public void testOfferAndPollWithRandomNumbers() {
		boolean success = positiveNumbers(0, 1000).allMatch(heap::offer);
		assertTrue("Offering a bunch of random numbers fails on some", success);
		assertEquals("Polling the heap does not work properly", 0, (int) heap.poll());
	}

	@Test
	public void testDefaultIsEmpty() {
		Heap<Integer> heap = new Heap<Integer>() {

			private final List<Integer> list = new ArrayList<>();

			@Override
			public boolean offer(Integer t) {
				return list.add(t);
			}

			@Override
			public Integer poll() {
				return null;
			}

			@Override
			public Integer peek() {
				return null;
			}

			@Override
			public int size() {
				return list.size();
			}
		};
		assertTrue("Default implementation of isEmpty does not work", heap.isEmpty());
		heap.offer(5);
		assertFalse("Default implementation of isEmpty does not work", heap.isEmpty());
	}

	private IntStream positiveNumbers(int min, int max) {
		return IntStream.range(min, max)
				.boxed()
				.sorted(HeapTest.shuffle())
				.mapToInt(e -> e);
	}

	public static <T> Comparator<T> shuffle() {
		final Map<Object, UUID> uniqueIds = new IdentityHashMap<>();
		return (e1, e2) -> {
			final UUID id1 = uniqueIds.computeIfAbsent(e1, k -> UUID.randomUUID());
			final UUID id2 = uniqueIds.computeIfAbsent(e2, k -> UUID.randomUUID());
			return id1.compareTo(id2);
		};
	}
}
