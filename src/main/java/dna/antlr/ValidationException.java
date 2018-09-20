package dna.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ValidationException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	private static String message(Token symbol, String fmt, Object... args) {
		return new MessageFormatter().render(symbol.getLine(), symbol.getCharPositionInLine(), fmt, args);
	}

	public ValidationException(ParserRuleContext ctx, Exception e) {
		this(ctx.getStart(), e);

	}

	public ValidationException(ParserRuleContext ctx, String fmt, Object... args) {
		this(ctx.getStart(), fmt, args);
	}

	public ValidationException(Token symbol, Exception e) {
		super(message(symbol, ""), e);
	}

	public ValidationException(Token symbol, String fmt, Object... args) {
		super(message(symbol, fmt, args));
	}

}
