package dna.tool.interpreter;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Storage<R> extends Supplier<R>, Consumer<R> {
	default R set(R item) {
		accept(item);
		return item;
	}

	<T> Storage<T> typeOf(Class<T> typeClass);

}
