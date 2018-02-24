package stack;

import org.junit.Before;

public class ArrayStackTest extends StackTest {

	@Override
	@Before
	public void setUp() {
		stack = new ArrayStack<>();
	}
}
