package stack;

import org.junit.Before;

public class LinkedStackTest extends StackTest {
	
	@Override
	@Before
	public void setUp() {
		stack = new LinkedStack<>();
	}
}
