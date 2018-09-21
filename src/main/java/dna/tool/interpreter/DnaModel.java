package dna.tool.interpreter;

import java.util.Optional;

public interface DnaModel {

	Optional<Variable> variable(String name);
	

}
