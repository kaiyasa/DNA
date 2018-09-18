package dna.tool.interpreter;

public abstract class TypeInfo {

	enum Kind {
		INT, STRING;
	}

	public abstract Kind kind();

}
