package dna.tool.interpreter;

public class Variable {

	public String name;
	public TypeInfo typeInfo;
	int line, charAt;

	public Variable(String name, TypeInfo typeInfo, int line, int charAt) {
		super();
		this.name = name;
		this.typeInfo = typeInfo;
		this.line = line;
		this.charAt = charAt;
	}

}
