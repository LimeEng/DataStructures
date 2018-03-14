package heap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
