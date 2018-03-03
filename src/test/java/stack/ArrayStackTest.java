package stack;

public class ArrayStackTest extends StackTest<ArrayStack<Integer>> {

	@Override
	protected ArrayStack<Integer> createInstance() {
		return new ArrayStack<>();
	}
}
