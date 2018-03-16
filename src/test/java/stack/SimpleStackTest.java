package stack;

import java.util.Collection;

public class SimpleStackTest extends StackTest<SimpleStack<Integer>> {

	@Override
	protected SimpleStack<Integer> createInstance() {
		return new SimpleStack<>();
	}

	@Override
	protected SimpleStack<Integer> createInstance(Collection<Integer> c) {
		return new SimpleStack<>(c);
	}
}
