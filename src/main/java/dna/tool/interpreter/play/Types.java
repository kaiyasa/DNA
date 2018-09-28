package dna.tool.interpreter.play;

public interface Types<R> {

	public enum Kind {
		INT, STRING, STRUCT;
	}

	default Kind kind() {
		throw new UnsupportedOperationException();
	}

	default Storage<R> allocate() {
		throw new UnsupportedOperationException();
	}
}
