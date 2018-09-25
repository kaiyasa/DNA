package dna.tool.interpreter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DnaModel {

	Optional<Variable> variable(String name);

	Optional<Function<List<Storage<?>>, Storage<?>>> routine(String name);

}
