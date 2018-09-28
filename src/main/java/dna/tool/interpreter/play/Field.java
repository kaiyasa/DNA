package dna.tool.interpreter.play;

import org.antlr.v4.runtime.Token;

class Field {
	Types<?> type;
	Token typeDeclaration;

	public Field(Types<?> type, Token typeDeclaration) {
		super();
		this.type = type;
		this.typeDeclaration = typeDeclaration;
	}

}