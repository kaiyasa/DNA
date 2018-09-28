package dna.tool.interpreter.play;

import java.util.Optional;

import dna.tool.interpreter.LogicException;

class SimpleStorage<T> implements Storage<T> {
	T data;

	public SimpleStorage(T item) {
		accept(item);
	}

	@Override
	public T get() {
		return data;
	}

	@Override
	public void accept(T item) {
		if (item == null)
			throw new IllegalArgumentException("null is disallowed");

		data = item;
	}

	@Override
	public Optional<Storage<?>> select(Identifier path) {
		if (path.size() > 1)
			throw new LogicException(path.head(), "request for structure component from simple storage");
		return Optional.ofNullable(this);
	}
}
