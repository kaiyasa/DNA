package dna.tool.interpreter;

public class IntType implements TypeInfo {
	@Override
	public Kind kind() {
		return Kind.INT;
	}

	@Override
	public boolean match(Class<?> type) {
		return Integer.class.equals(type);
	}

}
