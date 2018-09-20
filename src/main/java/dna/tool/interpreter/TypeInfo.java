package dna.tool.interpreter;

public interface TypeInfo {

	enum Kind {
		INT, STRING;
	}

	public Kind kind();

	public boolean match(Class<?> type);

}
