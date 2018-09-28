package dna.tool.interpreter.play;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

interface Storage<T> extends Supplier<T>, Consumer<T> {

	default Optional<Storage<?>> select(Identifier path) {
		throw new UnsupportedOperationException();
	}

	@Override
	default T get() {
		throw new UnsupportedOperationException();
	}

	@Override
	default void accept(T t) {
		throw new UnsupportedOperationException();

	}
}