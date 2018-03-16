package heap;

public interface Heap<T> {

	boolean offer(T t);

	T poll();
	
	T peek();
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
}
