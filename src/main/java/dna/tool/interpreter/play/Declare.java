package dna.tool.interpreter.play;

import org.antlr.v4.runtime.Token;

class Declare extends Field {
	Storage<?> item;

	public Declare(Types<?> type, Token typeDeclaration, Storage<?> item) {
		super(type, typeDeclaration);
		this.item = item;
	}

	public Declare(Types<?> type, Token typeDeclaration) {
		this(type, typeDeclaration, type.allocate());
	}
}