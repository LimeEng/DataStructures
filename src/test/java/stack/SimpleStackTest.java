package stack;

import org.junit.Before;

public class SimpleStackTest extends StackTest {

	@Override
	@Before
	public void setUp() {
		stack = new SimpleStack<>();
	}
}
