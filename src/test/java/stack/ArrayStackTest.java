package stack;

import java.util.Collection;

public class ArrayStackTest extends StackTest<ArrayStack<Integer>> {

	@Override
	protected ArrayStack<Integer> createInstance() {
		return new ArrayStack<>();
	}

	@Override
	protected ArrayStack<Integer> createInstance(Collection<Integer> c) {
		return new ArrayStack<>(c);
	}
}
