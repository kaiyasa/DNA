package dna.tool.interpreter.play;

import org.antlr.v4.runtime.Token;

class StructuredType implements Types<Structure> {

	private Token declaration;
	private StructuredType parent;

	public StructuredType(Token declaration) {
		this.declaration = declaration;
	}

	public StructuredType(StructuredType parent, Token id) {
		this.parent = parent; this.declaration = id;
	}

	public Token symbol() {
		return declaration;
	}

	public void add(Field field) {
		// TODO Auto-generated method stub
		
	}
}