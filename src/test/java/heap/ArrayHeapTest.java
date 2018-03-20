package heap;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

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

	@Test
	public void testComparatorBasic() {
		Heap<Integer> heap1 = new ArrayHeap<>(3, (a, b) -> -Integer.compare(a, b));
		Heap<Integer> heap2 = new ArrayHeap<>(3, (a, b) -> Integer.compare(a, b));

		IntStream.range(0, 100)
				.forEach(heap1::offer);
		IntStream.range(0, 100)
				.forEach(heap2::offer);

		for (int i = 0; i < 100; i++) {
			assertEquals("Custom comparator is not working (reverse order)", 99 - i, (int) heap1.poll());
			assertEquals("Custom comparator is not working (natural order)", i, (int) heap2.poll());
		}
	}
}
