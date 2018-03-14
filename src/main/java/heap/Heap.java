package heap;

public interface Heap<T> {

	boolean offer(T t);

	T peek();
	
	T poll();
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
}
