package dna.antlr;

public class MessageFormatter {
	protected static final String message = "line %d:%d: %s";

	public String render(int line, int charAt, String fmt, Object... args) {
		return String.format(message, line, charAt, String.format(fmt, args));
	}
}
