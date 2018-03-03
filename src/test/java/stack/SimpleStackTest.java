package stack;

public class SimpleStackTest extends StackTest<SimpleStack<Integer>> {

	@Override
	protected SimpleStack<Integer> createInstance() {
		return new SimpleStack<>();
	}
}
