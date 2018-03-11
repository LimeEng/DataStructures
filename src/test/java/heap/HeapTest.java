package heap;

import org.junit.After;
import org.junit.Before;

public abstract class HeapTest<T extends Heap<Integer>> {

	protected T stack;

	protected abstract T createInstance(int order);

	@Before
	public void setUp() {
		stack = createInstance(3);
	}

	@After
	public void tearDown() {
		stack = null;
	}
}
