package heap;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayHeapTest extends HeapTest<ArrayHeap<Integer>> {

	@Override
	protected ArrayHeap<Integer> createInstance(int order) {
		return new ArrayHeap<>(order);
	}

	@Test
	public void testConstructor() {
		Heap<Integer> heap;
		for (int i = 1; i < 100; i++) {
			heap = createInstance(i);
		}
		boolean exceptionThrown = false;
		try {
			heap = createInstance(0);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		}
		assertTrue("No exception thrown for trying to create a heap with order 0", exceptionThrown);
	}
}
