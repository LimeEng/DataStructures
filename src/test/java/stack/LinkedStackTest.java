package stack;

import java.util.Collection;

public class LinkedStackTest extends StackTest<LinkedStack<Integer>> {
	
	@Override
	protected LinkedStack<Integer> createInstance() {
		return new LinkedStack<>();
	}

	@Override
	protected LinkedStack<Integer> createInstance(Collection<Integer> c) {
		return new LinkedStack<>(c);
	}
}
