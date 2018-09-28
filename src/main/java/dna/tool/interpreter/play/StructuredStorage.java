package dna.tool.interpreter.play;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dna.antlr.ValidationException;

public class StructuredStorage implements Storage<Structure> {
	Map<String, Storage<?>> fields = new HashMap<>();

	Map<String, StructuredStorage> nest = new HashMap<>();

	@Override
	public Optional<Storage<?>> select(Identifier path) {
		if (path.size() < 1)
			throw new IllegalArgumentException();

		String name = path.head().getText();
		if (path.size() == 1)
			return Optional.ofNullable(fields.get(name));

		return Optional.ofNullable(nest.get(name)).orElseThrow(() -> {
			return new ValidationException(path.head(), "undefined structure component '%s'", name);
		}).select(path.tail());
	}
}
