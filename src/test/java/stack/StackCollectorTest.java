package stack;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class StackCollectorTest {

	@Test
	public void testCollectToStack() {
		Stack<Integer> stack = IntStream.range(0, 100)
				.boxed()
				.collect(StackCollector.toStack());
		assertEquals("The collected stack is the wrong size", 100, stack.size());
		List<Integer> values = stack.popWhile(e -> true);
		Collections.sort(values);
		List<Integer> expected = IntStream.range(0, 100)
				.boxed()
				.collect(Collectors.toList());
		assertEquals("The collector returned a malformed stack", expected, values);
	}
	
	@Test
	public void testToStackNotNull() {
		assertNotNull("toStack() returns null", StackCollector.toStack());
	}
	
	@Test
	public void testCharacteristicsNotNull() {
		assertNotNull("toStack() returns null", StackCollector.toStack().characteristics());
	}
	
	@Test
	public void testFinisherNotNull() {
		assertNotNull("toStack() returns null", StackCollector.toStack().finisher());
	}
	
	@Test
	public void testCombinerNotNull() {
		assertNotNull("toStack() returns null", StackCollector.toStack().combiner());
	}
	
	@Test
	public void testAccumulatorNotNull() {
		assertNotNull("toStack() returns null", StackCollector.toStack().accumulator());
	}
}
