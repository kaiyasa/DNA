package dna.tool.interpreter;

public class StringType implements TypeInfo {

	@Override
	public Kind kind() {
		return Kind.STRING;
	}

	@Override
	public boolean match(Class<?> type) {
		return String.class.equals(type);
	}

}
