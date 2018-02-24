package stack;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

// TODO: Document, test and optimize!
// Good resources:
// http://www.nurkiewicz.com/2014/07/introduction-to-writing-custom.html
// https://hedleyproctor.com/2016/02/yet-another-java-8-custom-collector-example/
// http://www.deadcoderising.com/2017-03-07-java-8-creating-a-custom-collector-for-your-stream/
public class StackCollector<T> implements Collector<T, Stack<T>, Stack<T>> {

	public static <T> Collector<T, ?, Stack<T>> toStack() {
		return new StackCollector<>();
	}

	@Override
	public Supplier<Stack<T>> supplier() {
		return () -> new ArrayStack<>();
	}

	@Override
	public BiConsumer<Stack<T>, T> accumulator() {
		return (stack, element) -> stack.push(element);
	}

	@Override
	public BinaryOperator<Stack<T>> combiner() {
		return (stack1, stack2) -> {
			stack1.push(stack2);
			return stack1;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.singleton(Characteristics.IDENTITY_FINISH);
	}

	@Override
	public Function<Stack<T>, Stack<T>> finisher() {
		return Function.identity();
	}
}
