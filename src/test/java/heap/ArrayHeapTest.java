package heap;

public class ArrayHeapTest extends HeapTest<ArrayHeap<Integer>> {
	
	@Override
	protected ArrayHeap<Integer> createInstance(int order) {
		return new ArrayHeap<>(order);
	}
}
