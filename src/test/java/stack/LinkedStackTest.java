package stack;

public class LinkedStackTest extends StackTest<LinkedStack<Integer>> {
	
	@Override
	protected LinkedStack<Integer> createInstance() {
		return new LinkedStack<>();
	}
}
