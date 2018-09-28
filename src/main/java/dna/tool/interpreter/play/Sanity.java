package dna.tool.interpreter.play;

import org.antlr.v4.runtime.Token;

public class Sanity {
	void main(String... args) {
		
		TypeSystem typeSystem = new TypeSystem();
		Token id;
		
		StructuredType type = typeSystem.addType(id);
		
		type.add(typeSystem.field(Types.Kind.INT, id));
				

	}
}